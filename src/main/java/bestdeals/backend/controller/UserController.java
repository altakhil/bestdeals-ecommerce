package bestdeals.backend.controller;

import bestdeals.backend.entities.CompletedOrders;
import bestdeals.backend.entities.CurrentOrder;
import bestdeals.backend.entities.OrderStatus;
import bestdeals.backend.repositories.UserRepository;
import bestdeals.backend.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final CurrentOrderService orderService;
    private final AuthenticationService authenticationService;
    private final ProductService productService;
    private final CompletedOrderService completedOrderService;
    private final UserRepository userRepository;
//    private final CartService cartService;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello()
    {
        return ResponseEntity.ok("Hi user");
    }

    @PostMapping("/placeorder")
    public ResponseEntity<CurrentOrder> placeOrder(@RequestBody CurrentOrder order, @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        if(productService.getProductById(order.getProductId()).isEmpty()) return ResponseEntity.notFound().build();
        Long userId = authenticationService.retrieveId(token.substring(7));
        order.setCustomerId(userId);
        order.setDeliveryAddress(userRepository.findById(userId).get().getAddress());
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @PostMapping("/cancelorder/{id}")
    public ResponseEntity<CurrentOrder> cancelOrder(@PathVariable("id") Long orderId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        Long userId = authenticationService.retrieveId(token.substring(7));
        return ResponseEntity.ok(orderService.cancelOrder(orderId,userId));
    }

    @GetMapping("/checkstatus/{id}")
    public ResponseEntity<OrderStatus> checkStatus(@PathVariable("id") Long orderId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        Long userId = authenticationService.retrieveId(token.substring(7));
        return ResponseEntity.ok(orderService.checkStatus(orderId,userId));
    }
///llllllllllll
    @PostMapping("/complete/{orderId}")
    public CompletedOrders createCompletedOrder(@PathVariable Long orderId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = authenticationService.retrieveId(token.substring(7));
        return completedOrderService.createCompletedOrder(orderId,userId);
    }

    @GetMapping("/orders/current")
    public List<CurrentOrder> retrieveCurrentOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        Long userId = authenticationService.retrieveId(token.substring(7));
        return orderService.getCurrentOrders(userId);
    }
}
