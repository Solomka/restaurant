package ua.training.validator.field;

/**
 * Class that chains all the fields validators
 * 
 * @author Solomka
 *
 */
public final class FieldValidatorsChainGenerator {

	private FieldValidatorsChainGenerator() {
		
	}

	public static AbstractFieldValidatorHandler getFieldValidatorsChain() {
		AbstractFieldValidatorHandler emailFieldValidator = EmailValidator.getInstance();
		AbstractFieldValidatorHandler passwordFieldValidator = PasswordValidator.getInstance();
		AbstractFieldValidatorHandler nameTextValidator = NameValidator.getInstance();
		AbstractFieldValidatorHandler surnameTextValidator = SurnameValidator.getInstance();
		AbstractFieldValidatorHandler phoneFieldValidator = PhoneValidator.getInstance();
		AbstractFieldValidatorHandler addressFieldValidator = AddressValidator.getInstance();
		AbstractFieldValidatorHandler descriptionFieldValidator = DescriptionValidator.getInstance();
		AbstractFieldValidatorHandler weightFieldValidator = WeightValidator.getInstance();
		AbstractFieldValidatorHandler costFieldValidator = CostValidator.getInstance();

		emailFieldValidator.setNextFieldValidator(passwordFieldValidator);
		passwordFieldValidator.setNextFieldValidator(nameTextValidator);
		nameTextValidator.setNextFieldValidator(surnameTextValidator);
		surnameTextValidator.setNextFieldValidator(phoneFieldValidator);
		phoneFieldValidator.setNextFieldValidator(addressFieldValidator);
		addressFieldValidator.setNextFieldValidator(descriptionFieldValidator);
		descriptionFieldValidator.setNextFieldValidator(weightFieldValidator);
		weightFieldValidator.setNextFieldValidator(costFieldValidator);

		return emailFieldValidator;
	}
}
