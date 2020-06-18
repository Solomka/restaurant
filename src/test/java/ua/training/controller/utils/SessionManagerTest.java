package ua.training.controller.utils;

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
import ua.training.constants.Attribute;
import ua.training.entity.User;
import ua.training.testData.UserTestDataGenerator;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static ua.training.controller.utils.SessionManager.USER_HAS_LOGGED_IN;
import static ua.training.controller.utils.SessionManager.USER_HAS_LOGGED_OUT;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LogManager.class, SessionManager.class})
public class SessionManagerTest {

    private static Logger LOGGER;

    @Mock
    private HttpSession httpSession;

    private SessionManager sessionManager;

    @BeforeClass
    public static void setUp() {
        LOGGER = PowerMockito.mock(Logger.class);
        PowerMockito.mockStatic(LogManager.class);
        PowerMockito.when(LogManager.getLogger(SessionManager.class)).thenReturn(LOGGER);
    }

    @Before
    public void setUpBeforeMethod() {
        sessionManager = new SessionManager();
    }

    @Test
    public void shouldReturnSessionManagerInstanceOnGetInstance() throws Exception {
        PowerMockito.whenNew(SessionManager.class).withNoArguments().thenReturn(sessionManager);

        SessionManager.getInstance();

        PowerMockito.verifyNew(SessionManager.class).withNoArguments();
    }

    @Test
    public void shouldReturnTrueWhenUserLoggedInOnIsUserLoggedIn() {
        User user = UserTestDataGenerator.generateUser();
        when(httpSession.getAttribute(Attribute.USER)).thenReturn(user);

        boolean actualResult = sessionManager.isUserLoggedIn(httpSession);

        verify(httpSession).getAttribute(Attribute.USER);
        assertTrue(actualResult);
    }

    @Test
    public void shouldAddUserToSessionOnAddUserToSession() {
        User user = UserTestDataGenerator.generateUser();
        doNothing().when(httpSession).setAttribute(Attribute.USER, user);

        sessionManager.addUserToSession(httpSession, user);

        verify(httpSession).setAttribute(Attribute.USER, user);
        verify(LOGGER, times(1)).info(USER_HAS_LOGGED_IN);
    }

    @Test
    public void shouldReturnUserOnGetUserFromSession() {
        User expectedResult = UserTestDataGenerator.generateUser();
        when(httpSession.getAttribute(Attribute.USER)).thenReturn(expectedResult);

        User actualResult = sessionManager.getUserFromSession(httpSession);

        verify(httpSession).getAttribute(Attribute.USER);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldInvalidateSessionOnInvalidateSession() {
        User user = UserTestDataGenerator.generateUser();
        when(httpSession.getAttribute(Attribute.USER)).thenReturn(user);
        doNothing().when(httpSession).invalidate();

        sessionManager.invalidateSession(httpSession);

        verify(httpSession, times(2)).getAttribute(Attribute.USER);
        verify(httpSession).invalidate();
        verify(LOGGER, times(1)).info(USER_HAS_LOGGED_OUT);
    }
}
