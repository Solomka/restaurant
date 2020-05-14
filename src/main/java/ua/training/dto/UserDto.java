package ua.training.dto;

import ua.training.entity.IBuilder;
import ua.training.entity.Role;

public class UserDto {

	private String name;
	private String surname;
	private String address;
	private String phone;
	private Role role;
	private String email;
	private String password;
	private String confirmPassword;

	public UserDto() {

	}

	public static class Builder implements IBuilder<UserDto> {

		private UserDto user = new UserDto();

		public Builder setName(String name) {
			user.name = name;
			return this;
		}

		public Builder setSurname(String surname) {
			user.surname = surname;
			return this;
		}

		public Builder setAddress(String address) {
			user.address = address;
			return this;
		}

		public Builder setPhone(String phone) {
			user.phone = phone;
			return this;
		}

		public Builder setRole(Role role) {
			user.role = role;
			return this;
		}

		public Builder setEmail(String email) {
			user.email = email;
			return this;
		}

		public Builder setPassword(String password) {
			user.password = password;
			return this;
		}

		public Builder setConfirmPassword(String password) {
			user.confirmPassword = password;
			return this;
		}

		@Override
		public UserDto build() {
			return user;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}

		UserDto other = (UserDto) obj;

		if ((email != null) ? !email.equals(other.email) : other.email != null) {
			return false;
		}
		if ((password != null) ? !password.equals(other.password) : other.password != null) {
			return false;
		}
		if ((confirmPassword != null) ? !confirmPassword.equals(other.confirmPassword)
				: other.confirmPassword != null) {
			return false;
		}

		return ((phone != null) ? phone.equals(other.phone) : other.phone == null);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("UserDto [name=").append(name).append(", surname=").append(surname).append(", address=")
				.append(address).append(", phone=").append(phone).append(", role=").append(role).append(", email=")
				.append(email).append(", password=").append(password).append(", confirmPassword=")
				.append(confirmPassword).append("]");
		return builder2.toString();
	}

}
