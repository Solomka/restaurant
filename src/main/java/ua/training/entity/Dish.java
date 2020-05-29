package ua.training.entity;

import java.math.BigDecimal;

public class Dish {

	private Long id;
	private String name;
	private String description;
	private double weight;
	private BigDecimal cost;
	private Category category;

	public Dish() {

	}

	public static class Builder implements IBuilder<Dish> {

		Dish dish = new Dish();

		public Builder setId(Long id) {
			dish.id = id;
			return this;
		}

		public Builder setName(String name) {
			dish.name = name;
			return this;
		}

		public Builder setDescription(String description) {
			dish.description = description;
			return this;
		}

		public Builder setWeight(double weight) {
			dish.weight = weight;
			return this;
		}

		public Builder setCost(BigDecimal cost) {
			dish.cost = cost;
			return this;
		}

		public Builder setCategory(Category category) {
			dish.category = category;
			return this;
		}

		@Override
		public Dish build() {
			return dish;
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

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
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
		
		Dish other = (Dish) obj;
		
		if ((description != null) ? !description.equals(other.description) : other.description != null) {
			return false;
		}
		
		return ((name != null) ? name.equals(other.name) : other.name == null);		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dish [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", weight=").append(weight).append(", cost=").append(cost)
				.append(", category=").append(category).append("]");
		return builder.toString();
	}

}
