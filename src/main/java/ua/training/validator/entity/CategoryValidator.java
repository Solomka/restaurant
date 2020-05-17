package ua.training.validator.entity;

import java.util.ArrayList;
import java.util.List;

import ua.training.entity.Category;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

public class CategoryValidator implements Validator<Category> {


	private AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();

	private CategoryValidator() {
	}

	private static class Holder {
		static final CategoryValidator INSTANCE = new CategoryValidator();
	}

	public static CategoryValidator getInstance() {
		return Holder.INSTANCE;
	}

	@Override
	public List<String> validate(Category category) {
		List<String> errors = new ArrayList<>();

		fieldValidator.validateField(FieldValidatorKey.NAME, category.getName(), errors);

		return errors;
	}
}
