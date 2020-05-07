package ua.training.entity;

import ua.training.locale.Message;

public enum Status {

	NEW("new", Message.STATUS_NEW), IN_PROGRESS("in progress", Message.STATUS_IN_PROGRESS),
	PREPARED("prepared", Message.STATUS_PREPARED), PAID("paid", Message.STATUS_PAID);

	private String value;
	private String localizedValue;

	Status(String value) {
		this.value = value;
	}

	Status(String value, String localizedValue) {
		this.value = value;
		this.localizedValue = localizedValue;
	}

	public String getValue() {
		return value;
	}

	public String getLocalizedValue() {
		return localizedValue;
	}

	/**
	 * Provides Role for a given String value
	 * 
	 * @param value value describing Status
	 * @return Role or RuntimeException if appropriate role wasn't found
	 */
	public static Status forValue(String value) {
		for (Status role : Status.values()) {
			if (role.getValue().equals(value)) {
				return role;
			}
		}
		throw new RuntimeException("Status with such string value doesn't exist");
	}

}
