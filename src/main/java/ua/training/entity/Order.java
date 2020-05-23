package ua.training.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

	private Long id;
	private LocalDateTime date;
	private Status status;
	private BigDecimal total;
	private User user;

	private List<Dish> dishes = new ArrayList<>();

	public Order() {

	}

	public Dish addDish(Dish dish) {
		dishes.add(dish);
		return dish;
	}

	public static class Builder implements IBuilder<Order> {

		Order order = new Order();

		public Builder setId(Long id) {
			order.id = id;
			return this;
		}

		public Builder setDate(LocalDateTime date) {
			order.date = date;
			return this;
		}

		public Builder setStatus(Status status) {
			order.status = status;
			return this;
		}

		public Builder setTotal(BigDecimal total) {
			order.total = total;
			return this;
		}

		public Builder setUser(User waiter) {
			order.user = waiter;
			return this;
		}

		public Builder setDishes(List<Dish> dishes) {
			order.dishes = new ArrayList<>();
			dishes.stream().forEach(dish -> order.dishes.add(dish));
			return this;
		}

		@Override
		public Order build() {
			return order;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = new ArrayList<>();
		dishes.stream().forEach(dish -> this.dishes.add(dish));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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

		Order other = (Order) obj;

		if ((date != null) ? !date.equals(other.date) : other.date != null) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		if ((total != null) ? !total.equals(other.total) : other.total != null) {
			return false;
		}

		return ((user != null) ? user.equals(other.user) : other.user == null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=").append(id).append(", date=").append(date).append(", status=").append(status)
				.append(", total=").append(total).append(", user=").append(user).append(", dishes=").append(dishes)
				.append("]");
		return builder.toString();
	}
}
