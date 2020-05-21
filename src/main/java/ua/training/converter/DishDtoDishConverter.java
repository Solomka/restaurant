package ua.training.converter;

import java.math.BigDecimal;

import ua.training.dto.DishDto;
import ua.training.entity.Dish;

public final class DishDtoDishConverter {

	private DishDtoDishConverter() {

	}

	public static Dish toDish(DishDto dishDto) {
		Dish.Builder dishBuilder = new Dish.Builder().setName(dishDto.getName())
				.setDescriprion(dishDto.getDescription()).setWeight(Double.parseDouble(dishDto.getWeight()))
				.setCost(new BigDecimal(dishDto.getCost())).setCategory(dishDto.getCategory());

		if (dishDto.getId() != null) {
			dishBuilder.setId(dishDto.getId());
		}

		return dishBuilder.build();
	}

}
