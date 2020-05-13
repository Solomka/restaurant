package ua.training.validator.field;

import java.util.List;

/**
 * Abstract class that is basic class for all entity/dto fields validators
 * 
 * @author Solomka
 *
 */
public abstract class AbstractFieldValidatorHandler {

	private FieldValidatorKey fieldValidatorKey;
	private AbstractFieldValidatorHandler nextFieldValidator;

	public AbstractFieldValidatorHandler(FieldValidatorKey fieldValidatorKey) {
		this.fieldValidatorKey = fieldValidatorKey;
	}

	public void setNextFieldValidator(AbstractFieldValidatorHandler nextFieldValidator) {
		this.nextFieldValidator = nextFieldValidator;
	}

	public void validateField(FieldValidatorKey fieldValidatorKey, String fieldValue, List<String> errors) {
		if (fieldValidatorKey.equals(this.fieldValidatorKey)) {
			validateField(fieldValue, errors);
		}
		if (nextFieldValidator != null) {
			nextFieldValidator.validateField(fieldValidatorKey, fieldValue, errors);
		}
	}

	abstract void validateField(String fieldValue, List<String> errors);
}