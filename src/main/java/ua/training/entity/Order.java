package ua.training.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

	private Long id;
	private LocalDate date;
	private Status status;
	private BigDecimal total;
	private User waiter;

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

		public Builder setDate(LocalDate date) {
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
			order.waiter = waiter;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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

	public User getWaiter() {
		return waiter;
	}

	public void setWaiter(User waiter) {
		this.waiter = waiter;
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
		result = prime * result + ((waiter == null) ? 0 : waiter.hashCode());
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

		return ((waiter != null) ? waiter.equals(other.waiter) : other.waiter == null);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=").append(id).append(", date=").append(date).append(", status=").append(status)
				.append(", total=").append(total).append(", waiter=").append(waiter).append(", dishes=").append(dishes)
				.append("]");
		return builder.toString();
	}
}
