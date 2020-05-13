package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class NameValidator extends AbstractFieldValidatorHandler {

	private static final String NAME_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$";
	
	private NameValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final NameValidator INSTANCE = new NameValidator(FieldValidatorKey.NAME);
	}

	public static NameValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(NAME_REGEX)) {
			errors.add(Message.INVALID_NAME_INPUT);
		}
	}
}