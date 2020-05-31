package ua.training.controller.command.i18n;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.locale.MessageLocale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MessageLocale.class, AppLocale.class})
public class ChangeLocaleCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private Locale locale;
    @Mock
    private HttpSession httpSession;

    private ChangeLocaleCommand changeLocaleCommand;

    @Test
    public void shouldReturnLoginViewOnExecute() throws Exception {
        when(httpServletRequest.getParameter(Attribute.LANG)).thenReturn("en");
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        PowerMockito.mockStatic(AppLocale.class);
        PowerMockito.mockStatic(MessageLocale.class);
        PowerMockito.when(AppLocale.forValue("en")).thenReturn(locale);
        PowerMockito.doNothing().when(MessageLocale.class, "setResourceBundleLocale", locale);

        String expectedResult = Page.HOME_VIEW;
        changeLocaleCommand = new ChangeLocaleCommand();

        String actualResult = changeLocaleCommand.execute(httpServletRequest, httpServletResponse);

        assertEquals(expectedResult, actualResult);
        verify(httpSession).setAttribute(Attribute.LOCALE, locale);
    }
}
