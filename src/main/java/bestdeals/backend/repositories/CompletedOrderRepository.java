package bestdeals.backend.repositories;

import bestdeals.backend.entities.CompletedOrders;
import bestdeals.backend.entities.CurrentOrder;
import bestdeals.backend.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CompletedOrderRepository extends JpaRepository<CompletedOrders, Long> {

    // Custom query methods can be added here

    // Find all orders by a specific customer ID
    List<CompletedOrders> findByCustomerId(Long customerId);

    // Find all orders placed on a specific date
    List<CompletedOrders> findByOrderDate(Date orderDate);

    List<CompletedOrders> findBySellerId(Long sellerId);
}
