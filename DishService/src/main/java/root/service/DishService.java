package root.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.InvalidDataException;
import root.database.model.Dish;
import root.database.repo.DishRepository;



@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    public Dish createDish(Dish dish) throws InvalidDataException {
        validateDish(dish);
        return dishRepository.save(dish);
    }

    public Dish getDishById(Integer dishId) throws InvalidDataException {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new InvalidDataException("Dish not found with ID: " + dishId));
    }

    private void validateDish(Dish dish) throws InvalidDataException {
        if (dish.getName() == null || dish.getDescription() == null || dish.getPrice() == null || dish.getQuantity() == null) {
            throw new InvalidDataException("Invalid dish data. Name, description, price, and quantity are required.");
        }
    }
}
