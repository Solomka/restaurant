package ua.training.validator.entity;

import java.util.List;

/**
 * Interface that has to be implemented by all app entities/dto validators for
 * validation user input
 * 
 * @author Solomka
 *
 * @param <T>
 *            entity/dto type
 */
public interface Validator<T> {
	List<String> validate(T dto);
}