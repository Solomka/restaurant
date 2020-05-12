package ua.training.dao;

import java.time.LocalDate;
import java.util.List;

import ua.training.entity.Role;
import ua.training.entity.User;

public interface UserDao extends GenericDao<User, Long>, AutoCloseable {

	List<User> searchUsersBySurname(String surname);

	List<User> searchUsersByRole(Role role);
	
	List<User> searchBestWaitersPerPeriod(LocalDate from, LocalDate to);

	void close();
}
