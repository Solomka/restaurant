package ua.training.dto;

import ua.training.entity.Category;
import ua.training.entity.IBuilder;

public class DishDto {
	
	private Long id;
	private String name;
	private String description;
	private String weight;
	private String cost;
	private Category category;

	public DishDto() {

	}

	public static class Builder implements IBuilder<DishDto> {
		
		DishDto dishDto = new DishDto();

		public Builder setId(Long id) {
			dishDto.id = id;
			return this;
		}
		
		public Builder setName(String name) {
			dishDto.name = name;
			return this;
		}
		
		public Builder setDescription(String descritpion) {
			dishDto.description = descritpion;
			return this;
		}
		
		public Builder setWeight(String weight) {
			dishDto.weight = weight;
			return this;
		}
		public Builder setCost(String cost) {
			dishDto.cost = cost;
			return this;
		}
		public Builder setCategory(Category category) {
			dishDto.category = category;
			return this;
		}
		
		@Override
		public DishDto build() {
			return dishDto;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if ((obj == null) || (getClass() != obj.getClass())){
			return false;
		}
		
		DishDto other = (DishDto) obj;
		
		if ((description != null) ? !description.equals(other.description) : other.description != null) {
			return false;
		}
		
		return ((name != null) ? name.equals(other.name) : other.name == null);
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("DishDto [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", weight=").append(weight).append(", cost=").append(cost)
				.append(", category=").append(category).append("]");
		return builder2.toString();
	}
	
	}


