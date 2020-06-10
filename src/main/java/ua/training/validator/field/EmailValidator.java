package ua.training.validator.field;

import ua.training.locale.Message;

import java.util.List;

public class EmailValidator extends AbstractFieldValidatorHandler {

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w-]+)*@[A-Za-z\\d-]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$";

    EmailValidator(FieldValidatorKey fieldValidatorKey) {
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
        if (fieldValue.isEmpty() || !fieldValue.matches(EMAIL_REGEX)) {
            errors.add(Message.INVALID_EMAIL);
        }
    }
}