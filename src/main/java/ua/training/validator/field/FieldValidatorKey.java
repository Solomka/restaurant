package ua.training.validator.field;

import ua.training.entity.Status;

public enum FieldValidatorKey {

	NAME("name"), SURNAME("surname"), EMAIL("email"), PASSWORD("password"), PHONE("phone"), ADDRESS("address");

	private String value;

	FieldValidatorKey(String value) {
	}

	public String getValue() {
		return value;
	}

	public static Status forValur(String value) {
		for (final Status status : Status.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		throw new RuntimeException("FieldValidatorKey with such string value doesn't exist");
	}
}