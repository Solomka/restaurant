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
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.entity.User;
import ua.training.testData.UserTestData;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static ua.training.service.UserService.GET_ALL_USERS;

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
    // @Ignore
    public void shouldGetAllUsersOnGetAllUsers() {
        List<User> users = UserTestData.generateUsersList();
        doReturn(users).when(userDao).getAll();

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(users, actualUsers);
        verify(LOGGER, times(1)).info(GET_ALL_USERS);
        verify(daoFactory).createUserDao();
        verify(userDao).getAll();
    }
}
