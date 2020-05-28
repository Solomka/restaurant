package ua.training.testData;

import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserTestData {

    private UserTestData() {

    }

    public static List<User> generateUsersList() {
        return Arrays.asList(
                new User[]{new User.Builder().setId(new Long(1)).setName("test name 1").setSurname("test surname 1")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                        .setEmail("test1@gmail.com").build(),
                        new User.Builder().setId(new Long(2)).setName("test name 2").setSurname("test surname 2")
                                .setAddress("test address 2").setRole(Role.MANAGER).setPhone("2222222222").setPassword("testpass2")
                                .setEmail("test2@gmail.com").build()

                }
        );
    }

    public static Optional<User> generateOptionalUser() {
        return Optional.of(new User.Builder().setId(new Long(1)).setName("test name 1").setSurname("test surname 1")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                .setEmail("test1@gmail.com").build());
    }

    public static UserDto generateUserForCreation() {
        return new UserDto.Builder().setName("test name 1").setSurname("test surname 1")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                .setConfirmPassword("testpass1").setEmail("test1@gmail.com").build();
    }
    public static UserDto generateUserForUpdate() {
        return new UserDto.Builder().setId(new Long(1)).setName("test name 1").setSurname("test surname 1")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                .setConfirmPassword("testpass1").setEmail("test1@gmail.com").build();
    }

    public static List<User> generateUserForSearch(){
        return new ArrayList<User>(){
            {
                add(new User.Builder().setId(new Long(1)).setName("test name 1").setSurname("testSurname")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                        .setEmail("test1@gmail.com").build());
            }
        };
    }

}
