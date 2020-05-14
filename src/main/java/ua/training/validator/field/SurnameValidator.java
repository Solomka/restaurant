package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class SurnameValidator extends AbstractFieldValidatorHandler {

	private static final String SURNAME_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$";
	
	private SurnameValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final SurnameValidator INSTANCE = new SurnameValidator(FieldValidatorKey.SURNAME);
	}

	public static SurnameValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (fieldValue.isEmpty() || !fieldValue.matches(SURNAME_REGEX)) {
			errors.add(Message.INVALID_SURNAME_INPUT);
		}
	}
}