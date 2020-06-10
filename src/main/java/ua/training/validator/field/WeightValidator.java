package ua.training.validator.field;

import ua.training.locale.Message;

import java.util.List;

public class WeightValidator extends AbstractFieldValidatorHandler {

    private static final String WEIGHT_REGEX = "^\\d*\\.?\\d*$";

    WeightValidator(FieldValidatorKey fieldValidatorKey) {
        super(fieldValidatorKey);
    }

    private static class Holder {
        static final WeightValidator INSTANCE = new WeightValidator(FieldValidatorKey.WEIGHT);
    }

    public static WeightValidator getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void validateField(String fieldValue, List<String> errors) {
        if (fieldValue.isEmpty() || !fieldValue.matches(WEIGHT_REGEX)) {
            errors.add(Message.INVALID_WEIGHT);
        }
    }
}
