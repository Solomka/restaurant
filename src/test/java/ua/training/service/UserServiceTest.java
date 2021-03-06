package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import ua.training.testData.UserTestDataGenerator;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.training.service.UserService.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogManager.class, DaoFactory.class, UserService.class})
public class UserServiceTest {

    private static Logger LOGGER;

    @Mock
    private DaoFactory daoFactory;
    @Mock
    private UserDao userDao;

    private UserService userService;

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
    public void shouldReturnUserServiceInstanceOnGetInstance() throws Exception {
        PowerMockito.mockStatic(DaoFactory.class);
        PowerMockito.when(DaoFactory.getDaoFactory()).thenReturn(daoFactory);
        PowerMockito.whenNew(UserService.class).withArguments(daoFactory).thenReturn(userService);

        UserService.getInstance();

        PowerMockito.verifyNew(UserService.class).withArguments(daoFactory);
    }

    @Test
    public void shouldGetAllUsersOnGetAllUsers() {
        List<User> expectedResult = UserTestDataGenerator.generateUsersList();
        when(userDao.getAll()).thenReturn(expectedResult);

        List<User> actualResult = userService.getAllUsers();

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(GET_ALL_USERS);
        verify(daoFactory).createUserDao();
        verify(userDao).getAll();
    }

    @Test
    public void shouldReturnUserWhenValidIdOnGetUserById() {
        Optional<User> expectedResult = UserTestDataGenerator.generateOptionalUser();
        Long userId = 1L;
        when(userDao.getById(userId)).thenReturn(expectedResult);

        Optional<User> actualResult = userService.getUserById(userId);

        assertEquals(expectedResult.get(), actualResult.get());
        verify(LOGGER, times(1)).info(String.format(GET_USER_BY_ID, userId));
        verify(daoFactory).createUserDao();
        verify(userDao).getById(userId);
    }

    @Test
    public void shouldReturnUserByCredsOnGetUserByCredentials() {
        Optional<User> expectedResult = UserTestDataGenerator.generateOptionalUser();
        String email = "test1@gmail.com";
        String pass = "testpass1";
        when(userDao.getUserByCredentials(email, pass)).thenReturn(expectedResult);

        Optional<User> actualResult = userService.getUserByCredentials(new CredentialsDto(email, pass));

        assertEquals(expectedResult.get(), actualResult.get());
        verify(LOGGER, times(1)).info(String.format(GET_USER_BY_CREDENTIALS, email));
        verify(daoFactory).createUserDao();
        verify(userDao).getUserByCredentials(email, pass);
    }

    @Test
    public void shouldCreateUserOnCreateUser() {
        UserDto userDto = UserTestDataGenerator.generateUserForCreation();

        userService.createUser(userDto);

        verify(LOGGER, times(1)).info(String.format(CREATE_USER, userDto.getEmail()));
        verify(daoFactory).createUserDao();
        verify(userDao).create(UserDtoUserConverter.toUser(userDto));
    }

    @Test
    public void shouldUpdateUserOnUpdateUser() {
        UserDto userDto = UserTestDataGenerator.generateUserForUpdate();

        userService.updateUser(userDto);

        verify(LOGGER, times(1)).info(String.format(UPDATE_USER, userDto.getId()));
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
        List<User> expectedResult = UserTestDataGenerator.generateUserForSearch();
        String surname = "testSurname";
        when(userDao.searchUsersBySurname(surname)).thenReturn(expectedResult);

        List<User> actualResult = userService.searchUsersBySurname(surname);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_USERS_BY_SURNAME, surname));
        verify(daoFactory).createUserDao();
        verify(userDao).searchUsersBySurname(surname);
    }

    @Test
    public void shouldSearchUserOnSearchUsersByRole() {
        List<User> expectedResult = UserTestDataGenerator.generateUserForSearch();
        Role role = Role.WAITER;
        when(userDao.searchUsersByRole(role)).thenReturn(expectedResult);

        List<User> actualResult = userService.searchUsersByRole(role);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_USERS_BY_ROLE, role.getValue()));
        verify(daoFactory).createUserDao();
        verify(userDao).searchUsersByRole(role);
    }

    @Test
    public void shouldSearchUserOnSearchBestWaitersPerPeriod() {
        List<User> expectedResult = UserTestDataGenerator.generateUserForSearch();
        LocalDate fromDate = LocalDate.of(2020, Month.MAY, 1);
        LocalDate toDate = LocalDate.of(2020, Month.MAY, 29);
        when(userDao.searchBestWaitersPerPeriod(fromDate, toDate)).thenReturn(expectedResult);

        List<User> actualResult = userService.searchBestWaitersPerPeriod(fromDate, toDate);

        assertEquals(expectedResult, actualResult);
        verify(LOGGER, times(1)).info(String.format(SEARCH_BEST_WAITER_PER_PERIOD, fromDate.toString(), toDate.toString()));
        verify(daoFactory).createUserDao();
        verify(userDao).searchBestWaitersPerPeriod(fromDate, toDate);
    }
}
