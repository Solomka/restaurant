package ua.training.testData;

import ua.training.dto.CredentialsDto;
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class UserTestDataGenerator {

    private UserTestDataGenerator() {

    }

    public static List<User> generateUsersList() {
        return new ArrayList<User>() {
            {
                add(new User.Builder().setId(1L).setName("testName").setSurname("testSurname")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                        .setEmail("test1@gmail.com").build());
                add(new User.Builder().setId(2L).setName("testBla").setSurname("testBla")
                        .setAddress("test address 2").setRole(Role.MANAGER).setPhone("2222222222").setPassword("testpass2")
                        .setEmail("test2@gmail.com").build());
            }
        };
    }

    public static Optional<User> generateOptionalUser() {
        return Optional.of(new User.Builder().setId(1L).setName("testName").setSurname("testSurname")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                .setEmail("test1@gmail.com").build());
    }

    public static UserDto generateUserForCreation() {
        return new UserDto.Builder().setName("testName").setSurname("testSurname")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                .setConfirmPassword("testpass1").setEmail("test1@gmail.com").build();
    }

    public static UserDto generateUserForUpdate() {
        return new UserDto.Builder().setId(1L).setName("testName").setSurname("testSurname")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                .setConfirmPassword("testpass1").setEmail("test1@gmail.com").build();
    }

    public static List<User> generateUserForSearch() {
        return new ArrayList<User>() {
            {
                add(new User.Builder().setId(1L).setName("testName").setSurname("testSurname")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("testpass1")
                        .setEmail("test1@gmail.com").build());
            }
        };
    }

    public static CredentialsDto generateCredentialsDtoWithValidCreds(){
        return new CredentialsDto("pytlyk@gmail.com", "pytlyk777");
    }

    public static User generateUser(){
        return new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("pytlyk777")
                .setEmail("pytlyk@gmail.com").build();
    }

    public static User generateManagerUser(){
        return new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                .setAddress("test address 1").setRole(Role.MANAGER).setPhone("1111111111").setPassword("pytlyk777")
                .setEmail("pytlyk@gmail.com").build();
    }

    public static User generateChiefUser(){
        return new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                .setAddress("test address 1").setRole(Role.CHIEF).setPhone("1111111111").setPassword("pytlyk777")
                .setEmail("pytlyk@gmail.com").build();
    }
}
