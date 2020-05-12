package ua.training.dao;

/**
 * Interface that have to be implemented by custom Connection wrapper
 * 
 * @author Solomka
 *
 */
public interface DaoConnection extends AutoCloseable {

	void begin();

	void commit();

	void rollback();

	void close();
}
