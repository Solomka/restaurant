package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.dto.UserDto;
import ua.training.locale.Message;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class UserDtoValidator implements Validator<UserDto> {

	private static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,14}$";

	private AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	UserDtoValidator() {
	}

	private static class Holder {
		static final UserDtoValidator INSTANCE = new UserDtoValidator();
	}

	public static UserDtoValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(UserDto dto) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.NAME, dto.getName(), errors);
		fieldValidator.validateField(FieldValidatorKey.SURNAME, dto.getSurname(), errors);
		fieldValidator.validateField(FieldValidatorKey.ADDRESS, dto.getAddress(), errors);
		fieldValidator.validateField(FieldValidatorKey.PHONE, dto.getPhone(), errors);
		fieldValidator.validateField(FieldValidatorKey.EMAIL, dto.getEmail(), errors);

		checkPassword(dto, errors);
		checkConfirmPassword(dto, errors);
		checkNewConfirmPassword(dto, errors);

		return errors;
	}

	private void checkPassword(UserDto dto, List<String> errors) {
		if (dto.getPassword().isEmpty() || !dto.getPassword().matches(PASSWORD_REGEX)) {
			errors.add(Message.INVALID_NEW_PASSWORD);
		}
	}

	private void checkConfirmPassword(UserDto dto, List<String> errors) {
		if (dto.getConfirmPassword().isEmpty() || !dto.getConfirmPassword().matches(PASSWORD_REGEX)) {
			errors.add(Message.IVALID_CONFIRM_PASSWORD);
		}
	}

	private void checkNewConfirmPassword(UserDto dto, List<String> errors) {
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			errors.add(Message.INVALID_NEW_CONFIRM_PASSWORD);
		}
	}
}
