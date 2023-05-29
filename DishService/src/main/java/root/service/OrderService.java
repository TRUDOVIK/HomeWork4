package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.InvalidDataException;
import root.database.model.Dish;
import root.database.model.Order;
import root.database.repo.DishRepository;
import root.database.repo.OrderRepository;
import root.util.OrderStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

    public Order createOrder(Order order) throws InvalidDataException {
        validateOrder(order);

        checkDishAvailability(order.getDishes());


        order.setStatus(String.valueOf(OrderStatus.PENDING));

        return orderRepository.save(order);
    }

    public Order getOrderById(Integer orderId) throws InvalidDataException {

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidDataException("Order not found with ID: " + orderId));
    }

    private void validateOrder(Order order) throws InvalidDataException {
        if (order.getUserId() == null || order.getDishes() == null || order.getDishes().isEmpty()) {
            throw new InvalidDataException("Invalid order data. User and dishes information is required.");
        }
    }

    private void checkDishAvailability(Set<Dish> dishes) throws InvalidDataException {
        for (Dish dish : dishes) {
            Dish storedDish = dishRepository.findById(dish.getId())
                    .orElseThrow(() -> new InvalidDataException("Dish not found with ID: " + dish.getId()));

            if (storedDish.getQuantity() < dish.getQuantity()) {
                throw new InvalidDataException("Dish not available in the requested quantity: " + dish.getName());
            }
        }
    }

}
