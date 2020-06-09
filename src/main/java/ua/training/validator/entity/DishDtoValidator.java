package ua.training.validator.entity;

import ua.training.dto.DishDto;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import java.util.ArrayList;
import java.util.List;

public class DishDtoValidator implements Validator<DishDto> {

    private AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

    DishDtoValidator() {
    }

    private static class Holder {
        static final DishDtoValidator INSTANCE = new DishDtoValidator();
    }

    public static DishDtoValidator getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public List<String> validate(DishDto dish) {
        List<String> errors = new ArrayList<>();

        fieldValidator.validateField(FieldValidatorKey.NAME, dish.getName(), errors);
        fieldValidator.validateField(FieldValidatorKey.DESCRIPTION, dish.getDescription(), errors);
        fieldValidator.validateField(FieldValidatorKey.WEIGHT, dish.getWeight(), errors);
        fieldValidator.validateField(FieldValidatorKey.COST, dish.getCost(), errors);

        return errors;
    }
}
