package ua.training.controller.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedirectionManager.class, HttpWrapper.class})
public class RedirectionManagerTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;

    private HttpWrapper httpWrapper;
    private RedirectionManager redirectionManager;

    @Test
    public void shouldReturnRedirectionManagerInstanceOnGetInstance() throws Exception {
        redirectionManager = new RedirectionManager();
        PowerMockito.whenNew(RedirectionManager.class).withNoArguments().thenReturn(redirectionManager);

        RedirectionManager.getInstance();

        PowerMockito.verifyNew(RedirectionManager.class).withNoArguments();
    }

    @Test
    public void shouldGenerateUrlParamsOnGenerateUrlParams() throws UnsupportedEncodingException {
        Map<String, String> urlParameters = new HashMap<String, String>() {
            {
                put("error", "restaurant.error.invalidName");
            }
        };
        String expectedResult = "?error=restaurant.error.invalidName";
        redirectionManager = new RedirectionManager();

        String actualResult = redirectionManager.generateUrlParams(urlParameters);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldRedirectWithParamsOnRedirectWithParams() throws IOException {
        httpWrapper = PowerMockito.mock(HttpWrapper.class);
        PowerMockito.when(httpWrapper.getRequest()).thenReturn(httpServletRequest);
        PowerMockito.when(httpWrapper.getResponse()).thenReturn(httpServletResponse);
        when(httpServletRequest.getContextPath()).thenReturn("/restaurant");
        when(httpServletRequest.getServletPath()).thenReturn("/controller");
        String urlPathWithParams = "/restaurant/controller/manager/categories?error=restaurant.error.invalidName";
        doNothing().when(httpServletResponse).sendRedirect(urlPathWithParams);
        String redirectionPath = "/manager/categories";
        Map<String, String> urlParameters = new HashMap<String, String>() {
            {
                put("error", "restaurant.error.invalidName");
            }
        };

        redirectionManager = new RedirectionManager();
        redirectionManager.redirectWithParams(httpWrapper, redirectionPath, urlParameters);

        verify(httpServletResponse).sendRedirect(urlPathWithParams);
    }

    @Test
    public void shouldRedirectOnRedirect() throws IOException {
        httpWrapper = PowerMockito.mock(HttpWrapper.class);
        PowerMockito.when(httpWrapper.getRequest()).thenReturn(httpServletRequest);
        PowerMockito.when(httpWrapper.getResponse()).thenReturn(httpServletResponse);
        when(httpServletRequest.getContextPath()).thenReturn("/restaurant");
        when(httpServletRequest.getServletPath()).thenReturn("/controller");
        String path = "/";
        String redirectionUrl = "/restaurant/controller/";
        doNothing().when(httpServletResponse).sendRedirect(redirectionUrl);
        redirectionManager = new RedirectionManager();

        redirectionManager.redirect(httpWrapper, path);

        verify(httpServletResponse).sendRedirect(redirectionUrl);
    }
}
