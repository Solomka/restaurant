package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.converter.UserDtoUserConverter;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.dto.CredentialsDto;
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    static final String GET_ALL_USERS = "Get all users";
    static final String GET_USER_BY_ID = "Get user by id: %d";
    static final String GET_USER_BY_CREDENTIALS = "Get user by credentials: %s";
    static final String CREATE_USER = "Create user: %s";
    static final String UPDATE_USER = "Update user: %d";
    static final String DELETE_USER = "Delete user: %d";
    static final String SEARCH_USERS_BY_SURNAME = "Search users by surname: %s";
    static final String SEARCH_USERS_BY_ROLE = "Search users by role: %s";
    static final String SEARCH_BEST_WAITER_PER_PERIOD = "Search best waiter per period from  %s to %s";

    private final DaoFactory daoFactory;

    UserService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final UserService INSTANCE = new UserService(DaoFactory.getDaoFactory());
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    public List<User> getAllUsers() {
        LOGGER.info(GET_ALL_USERS);
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getAll();
        }
    }

    public Optional<User> getUserById(Long userId) {
        LOGGER.info(String.format(GET_USER_BY_ID, userId));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getById(userId);
        }
    }

    public Optional<User> getUserByCredentials(CredentialsDto credentials) {
        LOGGER.info(String.format(GET_USER_BY_CREDENTIALS, credentials.getEmail()));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.getUserByCredentials(credentials.getEmail(), credentials.getPassword());
        }
    }

    public void createUser(UserDto userDto) {
        LOGGER.info(String.format(CREATE_USER, userDto.getEmail()));
        User user = UserDtoUserConverter.toUser(userDto);
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        }
    }

    public void updateUser(UserDto userDto) {
        LOGGER.info(String.format(UPDATE_USER, userDto.getId()));
        User user = UserDtoUserConverter.toUser(userDto);
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.update(user);
        }
    }

    public void deleteUser(Long userId) {
        LOGGER.info(String.format(DELETE_USER, userId));
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.delete(userId);
        }
    }

    public List<User> searchUsersBySurname(String surname) {
        LOGGER.info(String.format(SEARCH_USERS_BY_SURNAME, surname));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.searchUsersBySurname(surname);
        }
    }

    public List<User> searchUsersByRole(Role role) {
        LOGGER.info(String.format(SEARCH_USERS_BY_ROLE, role.getValue()));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.searchUsersByRole(role);
        }
    }

    public List<User> searchBestWaitersPerPeriod(LocalDate fromDate, LocalDate toDate) {
        LOGGER.info(String.format(SEARCH_BEST_WAITER_PER_PERIOD, fromDate.toString(), toDate.toString()));
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.searchBestWaitersPerPeriod(fromDate, toDate);
        }
    }
}
