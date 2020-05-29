package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.converter.UserDtoUserConverter;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.dto.CredentialsDto;
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.testData.UserTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.training.service.UserService.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogManager.class)
public class UserServiceTest {

    private static Logger LOGGER;

    @Mock
    private DaoFactory daoFactory;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserDao userDao;

    @BeforeClass
    public static void setUp() {
        LOGGER = PowerMockito.mock(Logger.class);
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getLogger(UserService.class)).thenReturn(LOGGER);
    }

    @Before
    public void setUpBeforeMethod() {
        userService = new UserService(daoFactory);
        when(daoFactory.createUserDao()).thenReturn(userDao);
    }

    @Test
    public void shouldGetAllUsersOnGetAllUsers() {
        List<User> users = UserTestData.generateUsersList();
        doReturn(users).when(userDao).getAll();

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(users, actualUsers);
        verify(LOGGER, times(1)).info(GET_ALL_USERS);
        verify(daoFactory).createUserDao();
        verify(userDao).getAll();
    }

    @Test
    public void shouldReturnUserWhenValidIdOnGetUserById() {
        Optional<User> user = UserTestData.generateOptionalUser();
        Long userId = 1L;
        doReturn(user).when(userDao).getById(userId);

        Optional<User> actualUser = userService.getUserById(userId);

        assertEquals(user.get(), actualUser.get());
        verify(LOGGER, times(1)).info(String.format(GET_USER_BY_ID, userId));
        verify(daoFactory).createUserDao();
        verify(userDao).getById(userId);
    }

    @Test
    public void shouldReturnUserByCredsOnGetUserByCredentials() {
        Optional<User> user = UserTestData.generateOptionalUser();
        String email = "test1@gmail.com";
        String pass = "testpass1";
        doReturn(user).when(userDao).getUserByCredentials(email, pass);

        Optional<User> actualUser = userService.getUserByCredentials(new CredentialsDto(email, pass));

        assertEquals(user.get(), actualUser.get());
        verify(LOGGER, times(1)).info(String.format(GET_USER_BY_CREDENTIALS, email));
        verify(daoFactory).createUserDao();
        verify(userDao).getUserByCredentials(email, pass);
    }

    @Test
    public void shouldCreateUserOnCreateUser() {
        UserDto userDto = UserTestData.generateUserForCreation();

        userService.createUser(userDto);

        verify(LOGGER, times(1)).info(String.format(CREATE_USER, userDto.getEmail()));
        verify(daoFactory).createUserDao();
        verify(userDao).create(UserDtoUserConverter.toUser(userDto));
    }

    @Test
    public void shouldUpdateUserOnUpdateUser() {
        UserDto userDto = UserTestData.generateUserForUpdate();

        userService.updateUser(userDto);

        verify(LOGGER, times(1)).info(String.format(UPDATE_USER, userDto.getEmail()));
        verify(daoFactory).createUserDao();
        verify(userDao).update(UserDtoUserConverter.toUser(userDto));
    }

    @Test
    public void shouldDeleteUserOnDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(LOGGER, times(1)).info(String.format(DELETE_USER, userId));
        verify(daoFactory).createUserDao();
        verify(userDao).delete(userId);
    }

    @Test
    public void shouldSearchUserOnSearchUsersBySurname() {
        List<User> expectedResult = UserTestData.generateUserForSearch();
        String surname = "testSurname";
        doReturn(expectedResult).when(userDao).searchUsersBySurname(surname);

        List<User> actualResult = userService.searchUsersBySurname(surname);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_USERS_BY_SURNAME, surname));
        verify(daoFactory).createUserDao();
        verify(userDao).searchUsersBySurname(surname);
    }

    @Test
    public void shouldSearchUserOnSearchUsersByRole() {
        List<User> expectedResult = UserTestData.generateUserForSearch();
        Role role = Role.WAITER;
        doReturn(expectedResult).when(userDao).searchUsersByRole(role);

        List<User> actualResult = userService.searchUsersByRole(role);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_USERS_BY_ROLE, role.getValue()));
        verify(daoFactory).createUserDao();
        verify(userDao).searchUsersByRole(role);
    }

    @Test
    public void shouldSearchUserOnSearchBestWaitersPerPeriod() {
        List<User> expectedResult = UserTestData.generateUserForSearch();
        LocalDate fromDate = LocalDate.of(2020, Month.MAY, 1);
        LocalDate toDate = LocalDate.of(2020, Month.MAY, 29);
        doReturn(expectedResult).when(userDao).searchBestWaitersPerPeriod(fromDate, toDate);

        List<User> actualResult = userService.searchBestWaitersPerPeriod(fromDate, toDate);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_BEST_WAITER_PER_PERIOD, fromDate.toString(), toDate.toString()));
        verify(daoFactory).createUserDao();
        verify(userDao).searchBestWaitersPerPeriod(fromDate, toDate);
    }
}
