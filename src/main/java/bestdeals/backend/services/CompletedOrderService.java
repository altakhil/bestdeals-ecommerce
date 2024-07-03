package bestdeals.backend.services;

import bestdeals.backend.entities.CompletedOrders;
import bestdeals.backend.entities.CurrentOrder;
import bestdeals.backend.entities.OrderStatus;
import bestdeals.backend.repositories.CompletedOrderRepository;
import bestdeals.backend.repositories.CurrentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CompletedOrderService {

    private final CompletedOrderRepository orderRepository;
    private final CurrentOrderRepository currentOrderRepository;
    private final ProductService productService;

    public CompletedOrders createCompletedOrder(Long orderId, Long userId) {
        // Find the current order by ID
        CurrentOrder order = currentOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("CurrentOrder not found with id " + orderId));

        if (!order.getCustomerId().equals(userId)) {
            throw new RuntimeException("User ID does not match the customer ID for this order.");
        }
        if(order.getOrderStatus().equals(OrderStatus.CANCELED)) return null;
        // Create a new CompletedOrders object
        CompletedOrders completedOrder = new CompletedOrders();

        completedOrder.setOrderDate(new Date());
        completedOrder.setPrice(order.getPrice());
        completedOrder.setCustomerId(order.getCustomerId());
        completedOrder.setQuantity(order.getQuantity());
        completedOrder.setSellerId(order.getSellerId());
        completedOrder.setProductName(productService.getProductNameById(order.getProductId()));

        // Save and return the completed order
        currentOrderRepository.deleteById(orderId);
        return orderRepository.save(completedOrder);
    }
}
