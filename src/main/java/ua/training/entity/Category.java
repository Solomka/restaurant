package ua.training.entity;

public class Category {

	private Long id;
	private String name;

	public Category() {

	}

	public static class Builder implements IBuilder<Category> {

		Category category = new Category();

		public Builder setId(Long id) {
			category.id = id;
			return this;
		}

		public Builder setName(String name) {
			category.name = name;
			return this;
		}

		@Override
		public Category build() {
			return category;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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

		Category other = (Category) obj;

		return ((name != null) ? name.equals(other.name) : other.name == null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [id=").append(id).append(", name=").append(name).append("]");
		return builder.toString();
	}

}
