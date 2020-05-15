package ua.training.converter;

import ua.training.dto.UserDto;
import ua.training.entity.User;

public final class UserDtoUserConverter {

	private UserDtoUserConverter() {

	}

	public static User toUser(UserDto userDto) {
		User.Builder userBuilder = new User.Builder().setName(userDto.getName()).setSurname(userDto.getSurname())
				.setAddress(userDto.getAddress()).setPhone(userDto.getPassword()).setPhone(userDto.getPhone())
				.setRole(userDto.getRole()).setEmail(userDto.getEmail()).setPassword(userDto.getPassword());

		if (userDto.getId() != null) {
			userBuilder.setId(userDto.getId());
		}

		return userBuilder.build();
	}
}
