//package bestdeals.backend.services;
//
//import bestdeals.backend.entities.Cart;
//import bestdeals.backend.entities.CurrentOrder;
//import bestdeals.backend.repositories.CartRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CartService {
//
//    private final AuthenticationService authenticationService;
//    private final CartRepository cartRepository;
//    private final ProductService productService;
//    private final CurrentOrderService currentOrderService;
//
//
//
//    public List<Cart> getCartByCustomerId(Long customerId) {
//        return cartRepository.findByCustomerId(customerId);
//    }
//
//    public Optional<Cart> getCartById(Long cartId) {
//        return cartRepository.findById(cartId);
//    }
//
//    public Cart addProductToCart(Long customerId, Long productId, int quantity) {
//        List<Cart> existingCartItems = cartRepository.findByCustomerIdAndProductId(customerId, productId);
//        if (!existingCartItems.isEmpty()) {
//            Cart existingCartItem = existingCartItems.get(0);
//            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
//            return cartRepository.save(existingCartItem);
//        }
//        Cart newCartItem = new Cart();
//        newCartItem.setCustomerId(customerId);
//        newCartItem.setProductId(productId);
//        newCartItem.setQuantity(quantity);
//        return cartRepository.save(newCartItem);
//    }
//
//    public Cart updateCartItemQuantity(Long cartId, int quantity, Long userId) {
//        Optional<Cart> cartItemOpt = cartRepository.findById(cartId);
//        if (cartItemOpt.isPresent() && cartItemOpt.get().getCustomerId().equals((userId))) {
//            Cart cartItem = cartItemOpt.get();
//            cartItem.setQuantity(quantity);
//            return cartRepository.save(cartItem);
//        }
//        throw new IllegalArgumentException("Cart item not found");
//    }
//
//    public void removeCartItem(Long cartId, Long userId) {
//        Optional<Cart> cartItemOpt = cartRepository.findById(cartId);
//        if (cartItemOpt.isPresent()) {
//            Cart cartItem = cartItemOpt.get();
//            if (cartItem.getCustomerId().equals(userId)) {
//                cartRepository.deleteById(cartId);
//            }
//        }
//    }
//
//    public void clearCart(Long customerId) {
//        List<Cart> cartItems = cartRepository.findByCustomerId(customerId);
//        cartRepository.deleteAll(cartItems);
//    }
//
//    public void checkOut(Long customerId)
//    {
//        // Fetch all items in the customer's cart
//        List<Cart> cartItems = cartRepository.findByCustomerId(customerId);
//
//        if (cartItems.isEmpty()) {
//            throw new RuntimeException("Cart is empty");
//        }
//
//        // Iterate over each cart item and create an order for each item
//        for (Cart cartItem : cartItems) {
//            CurrentOrder order = new CurrentOrder();
//            order.setCustomerId(customerId);
//            order.setProductId(cartItem.getProductId());
//            order.setQuantity(cartItem.getQuantity());
//            // Create the order using CurrentOrderService
//            currentOrderService.createOrder(order);
//        }
//
//        // Clear the cart after checkout
//        clearCart(customerId);
//
//    }
//}
