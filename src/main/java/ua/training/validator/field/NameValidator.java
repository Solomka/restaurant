package ua.training.validator.field;

import ua.training.locale.Message;

import java.util.List;

public class NameValidator extends AbstractFieldValidatorHandler {

    private static final String NAME_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$";

    NameValidator(FieldValidatorKey fieldValidatorKey) {
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
        if (fieldValue.isEmpty() || !fieldValue.matches(NAME_REGEX)) {
            errors.add(Message.INVALID_NAME);
        }
    }
}