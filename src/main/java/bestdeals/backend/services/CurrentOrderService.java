package bestdeals.backend.services;

import bestdeals.backend.entities.CurrentOrder;
import bestdeals.backend.entities.OrderStatus;
import bestdeals.backend.entities.Product;
import bestdeals.backend.repositories.CurrentOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentOrderService {

    private final CurrentOrderRepository orderRepository;
    private final ProductService productService;

    public List<CurrentOrder> getCurrentOrders(Long customerId) {
        List<CurrentOrder> orders = orderRepository.findByCustomerId(customerId);
        return filterByStatusNotCancelled(orders);
    }

    private List<CurrentOrder> filterByStatusNotCancelled(List<CurrentOrder> orders) {
        // Filter orders where orderStatus is not 'cancelled'
        return orders.stream()
                .filter(order -> !order.getOrderStatus().equals(OrderStatus.CANCELED))
                .toList(); // Requires Java 16+ for .toList() method
    }
    public CurrentOrder createOrder(CurrentOrder order) {
        order.setOrderDate(new Date()); // Set the current date as the order date
        order.setOrderStatus(OrderStatus.PENDING); // Default status as PENDING
        Optional<Product> product = productService.getProductById(order.getProductId());
        if(product.isPresent())
        {
            order.setPrice((float)order.getQuantity()*product.get().getPrice());
            order.setSellerId(product.get().getSellerId());
        }
        return orderRepository.save(order);
    }

    public CurrentOrder cancelOrder(Long orderId, Long userId) {
        Optional<CurrentOrder> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            CurrentOrder order = optionalOrder.get();
            if(order.getCustomerId() != userId) return null;
            if (order.getOrderStatus() != OrderStatus.CANCELED) {
                order.setOrderStatus(OrderStatus.CANCELED);
                return orderRepository.save(order);
            } else {
                throw new RuntimeException("CurrentOrder is already canceled");
            }
        } else {
            throw new RuntimeException("CurrentOrder not found with id " + orderId);
        }
    }

    public OrderStatus checkStatus(Long orderId, Long userId) {
        return orderRepository.findById(orderId)
                .filter(order -> order.getCustomerId().equals(userId))
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId + " for user " + userId))
                .getOrderStatus();
    }

    public CurrentOrder setProcessingOrderStatus(Long orderId, Long sellerId) {
        Optional<CurrentOrder> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            CurrentOrder order = optionalOrder.get();
            if(order.getSellerId() != sellerId) return null;
            if (order.getOrderStatus() != OrderStatus.CANCELED) {
                order.setOrderStatus(OrderStatus.PROCESSING);
                return orderRepository.save(order);
            } else {
                throw new RuntimeException("CurrentOrder is already canceled");
            }
        } else {
            throw new RuntimeException("CurrentOrder not found with id " + orderId);
        }
    }

    public CurrentOrder setShippedOrderStatus(Long orderId, Long sellerId) {
        Optional<CurrentOrder> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            CurrentOrder order = optionalOrder.get();
            if(order.getSellerId() != sellerId) return null;
            if (order.getOrderStatus() != OrderStatus.CANCELED) {
                order.setOrderStatus(OrderStatus.SHIPPED);
                return orderRepository.save(order);
            } else {
                throw new RuntimeException("CurrentOrder is already canceled");
            }
        } else {
            throw new RuntimeException("CurrentOrder not found with id " + orderId);
        }
    }

}
