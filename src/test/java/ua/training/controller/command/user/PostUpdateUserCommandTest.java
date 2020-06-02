package ua.training.controller.command.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.locale.Message;
import ua.training.service.UserService;
import ua.training.testData.UserTestDataGenerator;
import ua.training.validator.entity.UserDtoValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RedirectionManager.class, UserDtoValidator.class})
public class PostUpdateUserCommandTest {

    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    @Mock
    private RedirectionManager redirectionManager;
    @Mock
    private UserDtoValidator userDtoValidator;
    @Captor
    private ArgumentCaptor<HttpWrapper> httpWrapperArgumentCaptor;
    @Captor
    private ArgumentCaptor<UserDto> userDtoArgumentCaptor;
    @Mock
    private UserService userService;

    private PostUpdateUserCommand postUpdateUserCommand;

    @Test
    public void shouldUpdateUserWhenValidInputOnExecute() throws ServletException, IOException {
        UserDto userDto = UserTestDataGenerator.generateUserForUpdate();
        when(httpServletRequest.getParameter(Attribute.ID_USER)).thenReturn("1");
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("testName");
        when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn("testSurname");
        when(httpServletRequest.getParameter(Attribute.ADDRESS)).thenReturn("test address 1");
        when(httpServletRequest.getParameter(Attribute.PHONE)).thenReturn("1111111111");
        when(httpServletRequest.getParameter(Attribute.ROLE)).thenReturn("waiter");
        when(httpServletRequest.getParameter(Attribute.EMAIL)).thenReturn("test1@gmail.com");
        when(httpServletRequest.getParameter(Attribute.PASSWORD)).thenReturn("testpass1");
        when(httpServletRequest.getParameter(Attribute.CONFIRM_PASSWORD)).thenReturn("testpass1");
        PowerMockito.mockStatic(RedirectionManager.class);
        PowerMockito.when(RedirectionManager.getInstance()).thenReturn(redirectionManager);
        PowerMockito.mockStatic(UserDtoValidator.class);
        PowerMockito.when(UserDtoValidator.getInstance()).thenReturn(userDtoValidator);
        List<String> errors = new ArrayList<>();
        when(userDtoValidator.validate(userDto)).thenReturn(errors);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(Attribute.SUCCESS,  Message.SUCCESS_USER_UPDATE);
        doNothing().when(userService).updateUser(userDto);
        String expectedResult = RedirectionManager.REDIRECTION;
        postUpdateUserCommand = new PostUpdateUserCommand(userService);

        String actualResult = postUpdateUserCommand.execute(httpServletRequest, httpServletResponse);

        verify(redirectionManager).redirectWithParams(httpWrapperArgumentCaptor.capture(), eq(ServletPath.ALL_USERS), eq(urlParams));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldNotUpdateUserWhenInvalidInputOnExecute() throws ServletException, IOException {
        when(httpServletRequest.getParameter(Attribute.ID_USER)).thenReturn("1");
        when(httpServletRequest.getParameter(Attribute.NAME)).thenReturn("");
        when(httpServletRequest.getParameter(Attribute.SURNAME)).thenReturn("");
        when(httpServletRequest.getParameter(Attribute.ADDRESS)).thenReturn("test address 1");
        when(httpServletRequest.getParameter(Attribute.PHONE)).thenReturn("1111111111");
        when(httpServletRequest.getParameter(Attribute.ROLE)).thenReturn("waiter");
        when(httpServletRequest.getParameter(Attribute.EMAIL)).thenReturn("test1@gmail.com");
        when(httpServletRequest.getParameter(Attribute.PASSWORD)).thenReturn("testpass1");
        when(httpServletRequest.getParameter(Attribute.CONFIRM_PASSWORD)).thenReturn("testpass1");
        PowerMockito.mockStatic(UserDtoValidator.class);
        PowerMockito.when(UserDtoValidator.getInstance()).thenReturn(userDtoValidator);
        List<String> errors = new ArrayList<String>(){
            {
                add(Message.INVALID_NAME_INPUT);
                add(Message.INVALID_SURNAME_INPUT);
            }
        };
        when(userDtoValidator.validate(userDtoArgumentCaptor.capture())).thenReturn(errors);
        String expectedResult = Page.ADD_UPDATE_USER_VIEW;
        postUpdateUserCommand = new PostUpdateUserCommand(userService);

        String actualResult = postUpdateUserCommand.execute(httpServletRequest, httpServletResponse);

        verify(httpServletRequest).setAttribute(Attribute.ROLES, Role.values());
        verify(httpServletRequest).setAttribute(eq(Attribute.USER_DTO), userDtoArgumentCaptor.capture());
        verify(httpServletRequest).setAttribute(Attribute.ERRORS, errors);
        assertEquals(expectedResult, actualResult);
    }
}
