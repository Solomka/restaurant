package ua.training.entity;

/**
 * Basic interface for GoF Builder pattern realization
 * <p>
 * Class that wants to use this pattern for its instances creation should
 * implement this interface
 * 
 * @author Solomka
 *
 * @param <T>
 *            the generic type of the creating object
 */
public interface IBuilder<T> {
	T build();
}