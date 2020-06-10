package ua.training.validator.field;

import ua.training.locale.Message;

import java.util.List;

public class CostValidator extends AbstractFieldValidatorHandler {

    private static final String COST_REGEX = "^\\d*\\.?\\d*$";

    CostValidator(FieldValidatorKey fieldValidatorKey) {
        super(fieldValidatorKey);
    }

    private static class Holder {
        static final CostValidator INSTANCE = new CostValidator(FieldValidatorKey.COST);
    }

    public static CostValidator getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void validateField(String fieldValue, List<String> errors) {
        if (fieldValue.isEmpty() || !fieldValue.matches(COST_REGEX)) {
            errors.add(Message.INVALID_COST);
        }
    }
}
