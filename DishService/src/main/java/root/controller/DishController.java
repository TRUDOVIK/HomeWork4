package root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.InvalidDataException;
import root.database.model.Dish;
import root.service.DishService;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public ResponseEntity<String> createDish(@RequestBody Dish dish) {
        try {
            Dish createdDish = dishService.createDish(dish);
            return ResponseEntity.status(HttpStatus.CREATED).body("Dish created successfully. Dish ID: " + createdDish.getId());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<Dish> getDish(@PathVariable Integer dishId) {
        try {
            Dish dish = dishService.getDishById(dishId);
            return ResponseEntity.ok(dish);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
