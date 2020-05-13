package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class EmailValidator extends AbstractFieldValidatorHandler {

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$";

	private EmailValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}

	private static class Holder {
		static final EmailValidator INSTANCE = new EmailValidator(FieldValidatorKey.EMAIL);
	}

	public static EmailValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (!fieldValue.matches(EMAIL_REGEX)) {
			errors.add(Message.INVALID_EMAIL);
		}
	}
}