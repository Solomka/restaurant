package ua.training.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.converter.UserDtoUserConverter;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.dto.CredentialsDto;
import ua.training.dto.UserDto;
import ua.training.entity.Role;
import ua.training.entity.User;

public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	static final String GET_ALL_USERS = "Get all users";

	private DaoFactory daoFactory;

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
		LOGGER.info("Get user by id: " + userId);
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.getById(userId);
		}
	}

	public Optional<User> getUserByCredentials(CredentialsDto credentials) {
		LOGGER.info("Get user by credantials: " + credentials.getEmail());
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.getUserByCredentials(credentials.getEmail(), credentials.getPassword());
		}
	}

	public void createUser(UserDto userDto) {
		LOGGER.info("Create user: " + userDto.getEmail());
		User user = UserDtoUserConverter.toUser(userDto);
		try (UserDao userDao = daoFactory.createUserDao()) {
			userDao.create(user);
		}
	}

	public void updateUser(UserDto userDto) {
		LOGGER.info("Update user: " + userDto.getEmail());
		User user = UserDtoUserConverter.toUser(userDto);
		try (UserDao userDao = daoFactory.createUserDao()) {
			userDao.update(user);
		}
	}

	public void deleteUser(Long userId) {
		LOGGER.info("Delete user: " + userId);
		try (UserDao userDao = daoFactory.createUserDao()) {
			userDao.delete(userId);
		}
	}

	public List<User> searchUsersBySurname(String surname) {
		LOGGER.info("Search users by surname: " + surname);
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.searchUsersBySurname(surname);
		}
	}

	public List<User> searchUsersByRole(Role role) {
		LOGGER.info("Search users by role: " + role.getValue());
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.searchUsersByRole(role);
		}
	}

	public List<User> searchBestWaitersPerPeriod(LocalDate fromDate, LocalDate toDate) {
		LOGGER.info(String.format("Search best waiter per period from  %s to %s ", fromDate.toString(), toDate.toString()));
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.searchBestWaitersPerPeriod(fromDate, toDate);
		}
	}

}
