package ua.training.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.entity.Role;
import ua.training.entity.User;

public class UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

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
		LOGGER.info("Get all users");
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

	public Optional<User> getUserByCredentials(String email, String password) {
		LOGGER.info("Get user by credantials: " + email);
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.getUserByCredentials(email, password);
		}
	}

	public void createUser(User user) {
		LOGGER.info("Create user: " + user.getEmail());
		try (UserDao userDao = daoFactory.createUserDao()) {
			userDao.create(user);
		}
	}

	public void updateUser(User user) {
		LOGGER.info("Update user: " + user.getEmail());
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

	public List<User> searchUsersBySurname(Role role) {
		LOGGER.info("Search users by role: " + role.getValue());
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.searchUsersByRole(role);
		}
	}

	public List<User> searchBestWaitersPerPeriod(LocalDate from, LocalDate to) {
		LOGGER.info(String.format("Search best waiter per period from  %s to %s ", from.toString(), to.toString()));
		try (UserDao userDao = daoFactory.createUserDao()) {
			return userDao.searchBestWaitersPerPeriod(from, to);
		}
	}
}
