package ua.training.validator.field;

import java.util.List;

import ua.training.locale.Message;

public class AddressValidator extends AbstractFieldValidatorHandler {

	private static final String ADDRESS_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє\\d](?=.*[a-zA-ZА-ЯІЇЄа-яіїє]{2,99})[a-zA-ZА-ЯІЇЄа-яіїє\\d\\s/\\.’'-,]*$";
	
	private AddressValidator(FieldValidatorKey fieldValidatorKey) {
		super(fieldValidatorKey);
	}	

	private static class Holder {
		static final AddressValidator INSTANCE = new AddressValidator(FieldValidatorKey.ADDRESS);
	}

	public static AddressValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public void validateField(String fieldValue, List<String> errors) {
		if (fieldValue.isEmpty() || !fieldValue.matches(ADDRESS_REGEX)) {
			errors.add(Message.INVALID_ADDRESS);
		}
	}
}