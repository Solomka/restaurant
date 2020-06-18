package ua.training.controller.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandKeyGeneratorTest {

    @Mock
    private HttpServletRequest request;

    @Test
    public void shouldGenerateCommandKeyOnGenerateCommandKeyFromRequest() {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn("/restaurant/controller/login");
        String expectedResult = "GET:login";

        String actualResult = CommandKeyGenerator.generateCommandKeyFromRequest(request);

        verify(request).getMethod();
        verify(request).getRequestURI();
        assertEquals(expectedResult, actualResult);
    }
}
