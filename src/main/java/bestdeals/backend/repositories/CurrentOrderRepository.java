package bestdeals.backend.repositories;

import bestdeals.backend.entities.CurrentOrder;
import bestdeals.backend.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CurrentOrderRepository extends JpaRepository<CurrentOrder, Long> {

    // Custom query methods can be added here

    // Find all orders by a specific customer ID
    List<CurrentOrder> findByCustomerId(Long customerId);

    // Find all orders with a specific status
    List<CurrentOrder> findByOrderStatus(OrderStatus orderStatus);

    // Find all orders placed on a specific date
    List<CurrentOrder> findByOrderDate(Date orderDate);

    List<CurrentOrder> findBySellerId(Long sellerId);
}
