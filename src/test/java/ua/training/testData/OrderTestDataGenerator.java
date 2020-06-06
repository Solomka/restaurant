package ua.training.testData;

import ua.training.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public final class OrderTestDataGenerator {

    private OrderTestDataGenerator() {

    }

    public static List<Order> generateOrdersList() {
        return new ArrayList<Order>() {
            {
                add(new Order.Builder().setId(1L)
                        .setDate(LocalDateTime.of(2020, Month.MAY, 1, 1, 3, 3))
                        .setStatus(Status.NEW).setTotal(new BigDecimal("371"))
                        .setUser(new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("pytlyk777")
                                .setEmail("pytlyk@gmail.com").build())
                        .setDishes(new ArrayList<Dish>() {
                            {
                                add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                                        .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                                        .build());
                                add(new Dish.Builder().setId(1L).setName("chicken").setDescription("with vegetables")
                                        .setWeight(180).setCost(new BigDecimal("190.5")).setCategory(new Category.Builder().setId(2L).setName("meat").build())
                                        .build());
                            }
                        }).build());
                add(new Order.Builder().setId(1L)
                        .setDate(LocalDateTime.of(2020, Month.MAY, 2, 3, 30, 3))
                        .setStatus(Status.NEW).setTotal(new BigDecimal("180.5"))
                        .setUser(new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                                .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("pytlyk777")
                                .setEmail("pytlyk@gmail.com").build())
                        .setDishes(new ArrayList<Dish>() {
                            {
                                add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                                        .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                                        .build());
                            }
                        }).build());
            }
        };
    }

    public static Optional<Order> generateOptionalOrder() {
        return Optional.of(new Order.Builder().setId(1L)
                .setDate(LocalDateTime.of(2020, Month.MAY, 1, 1, 3, 3))
                .setStatus(Status.NEW).setTotal(new BigDecimal("371"))
                .setUser(new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("pytlyk777")
                        .setEmail("pytlyk@gmail.com").build())
                .setDishes(new ArrayList<Dish>() {
                    {
                        add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                                .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                                .build());
                        add(new Dish.Builder().setId(1L).setName("chicken").setDescription("with vegetables")
                                .setWeight(180).setCost(new BigDecimal("190.5")).setCategory(new Category.Builder().setId(2L).setName("meat").build())
                                .build());
                    }
                }).build());
    }

    public static Order generateOrderForCreation() {
        return new Order.Builder()
                .setDate(LocalDateTime.of(2020, Month.MAY, 2, 3, 30, 3))
                .setStatus(Status.NEW).setTotal(new BigDecimal("180.5"))
                .setUser(new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("pytlyk777")
                        .setEmail("pytlyk@gmail.com").build())
                .setDishes(new ArrayList<Dish>() {
                    {
                        add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                                .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                                .build());
                    }
                }).build();
    }

    public static Order generateOrderForUpdate() {
        return new Order.Builder()
                .setId(1L)
                .setStatus(Status.PREPARED)
                .build();

    }

    public static List<Order> generateOrderForSearch() {
        return Collections.singletonList(new Order.Builder().setId(1L)
                .setDate(LocalDateTime.of(2020, Month.MAY, 2, 3, 30, 3))
                .setStatus(Status.NEW).setTotal(new BigDecimal("180.5"))
                .setUser(new User.Builder().setId(1L).setName("vika").setSurname("pytlyk")
                        .setAddress("test address 1").setRole(Role.WAITER).setPhone("1111111111").setPassword("pytlyk777")
                        .setEmail("pytlyk@gmail.com").build())
                .setDishes(new ArrayList<Dish>() {
                    {
                        add(new Dish.Builder().setId(1L).setName("cheesecake").setDescription("delicious")
                                .setWeight(150).setCost(new BigDecimal("180.5")).setCategory(new Category.Builder().setId(1L).setName("dessert").build())
                                .build());
                    }
                }).build());
    }
}
