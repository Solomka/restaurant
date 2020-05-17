package ua.training.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ua.training.dao.CategoryDao;
import ua.training.dao.DaoConnection;
import ua.training.dao.DaoFactory;
import ua.training.dao.UserDao;
import ua.training.exception.ServerException;

/**
 * Class that represent dao factory that produces DAOs for JDBC persistent
 * storage access implementation and use DB connection pool for getting
 * connections to db
 * 
 * @author Solomka
 *
 */
public class JdbcDaoFactory extends DaoFactory {

	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoFactory.class);

	private DataSource dataSource;

	/**
	 * Get DataSource implementation from Initial Context by means of JNDI mechanism
	 */
	public JdbcDaoFactory() {
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/restaurant");

		} catch (Exception e) {
			LOGGER.error("Can't load pool connection from Initial Context", e);
			throw new ServerException(e);
		}
	}

	/**
	 * Get custom Connection wrapper for providing transaction execution
	 * 
	 * @return a connection to the data source
	 * @exception ServerException if a database access error occurs
	 */
	@Override
	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection to the data source", e);
			throw new ServerException(e);
		}
	}

	@Override
	public UserDao createUserDao() {
		try {
			return new JdbcUserDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcUserDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public UserDao createUserDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcUserDao(sqlConnection);
	}

	@Override
	public CategoryDao createCategoryDao() {
		try {
			return new JdbcCategoryDao(dataSource.getConnection(), true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB Connection for JdbcCategoryDao creation", e);
			throw new ServerException(e);
		}
	}

	@Override
	public CategoryDao createCategoryDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcCategoryDao(sqlConnection);
	}
}
