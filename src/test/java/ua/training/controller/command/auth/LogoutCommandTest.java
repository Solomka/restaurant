package ua.training.controller.command.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SessionManager.class, RedirectionManager.class})
public class LogoutCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private HttpSession httpSession;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private RedirectionManager redirectionManager;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;

    private LogoutCommand logoutCommand;

    @Test
    public void shouldLogOutOnExecute() throws Exception {
        PowerMockito.mockStatic(SessionManager.class);
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(SessionManager.getInstance()).thenReturn(sessionManager);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        when(httpServletRequest.getContextPath()).thenReturn("\\restaurant");
        when(httpServletRequest.getServletPath()).thenReturn("\\controller");
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        String expectedResult = RedirectionManager.REDIRECTION;
        logoutCommand = new LogoutCommand();

        String actualResult = logoutCommand.execute(httpServletRequest, httpServletResponse);

        verify(sessionManager).invalidateSession(httpSession);
        verify(redirectionManager).redirect(httpWrapperArgumentCaptor.capture(), eq(ServletPath.HOME));
        assertEquals(expectedResult, actualResult);
    }
}
