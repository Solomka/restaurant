package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class PhoneValidator extends AbstractFieldValidatorHandler {

	private static final String PHONE_REGEX = "^(\\+)?\\d{7,12}$";
	
	private PhoneValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final PhoneValidator INSTANCE = new PhoneValidator(FieldValidatorKey.PHONE);
	}

	public static PhoneValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(PHONE_REGEX)) {
			errors.add(Message.INVALID_PHONE);
		}
	}
}