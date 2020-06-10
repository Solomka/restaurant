package ua.training.validator.field;

import ua.training.locale.Message;

import java.util.List;

public class DescriptionValidator extends AbstractFieldValidatorHandler {

    private static final String DESCRIPTION_REGEX = "^[A-Za-zА-ЯІЇЄа-яіїє]+([\\s’'-][A-Za-zА-ЯІЇЄа-яіїє]+)*$";

    DescriptionValidator(FieldValidatorKey fieldValidatorKey) {
        super(fieldValidatorKey);
    }

    private static class Holder {
        static final DescriptionValidator INSTANCE = new DescriptionValidator(FieldValidatorKey.DESCRIPTION);
    }

    public static DescriptionValidator getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void validateField(String fieldValue, List<String> errors) {
        if (fieldValue.isEmpty() || !fieldValue.matches(DESCRIPTION_REGEX)) {
            errors.add(Message.INVALID_DESCRIPTION);
        }
    }
}
