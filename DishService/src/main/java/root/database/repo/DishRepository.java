package root.database.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.database.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
}