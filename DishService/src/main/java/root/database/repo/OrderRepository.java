package root.database.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import root.database.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
