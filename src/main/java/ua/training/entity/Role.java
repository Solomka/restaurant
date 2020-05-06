package ua.training.entity;

import ua.training.locale.Message;

public enum Role {
	CHIEF("chief", Message.ROLE_CHIEF), MANAGER("manager", Message.ROLE_MANAGER),
	WAITER("waiter", Message.ROLE_MANAGER), CLIENT("client", Message.ROLE_CLIENT);

	private String value;
	private String localizedValue;

	Role(String value) {
		this.value = value;
	}

	Role(String value, String localizedValue) {
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
	 * @param value value describing Role
	 * @return Role or RuntimeException if appropriate role wasn't found
	 */
	public static Role forValue(String value) {
		for (Role role : Role.values()) {
			if (role.getValue().equals(value)) {
				return role;
			}
		}
		throw new RuntimeException("Role with such string value doesn't exist");
	}
}
