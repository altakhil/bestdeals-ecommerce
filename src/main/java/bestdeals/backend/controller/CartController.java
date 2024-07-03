//package bestdeals.backend.controller;
//
//import bestdeals.backend.dto.AddProductToCartDto;
//import bestdeals.backend.dto.UpdateQuantityInCartRequest;
//import bestdeals.backend.entities.Cart;
//import bestdeals.backend.services.AuthenticationService;
//import bestdeals.backend.services.CartService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/user/cart")
//@RequiredArgsConstructor
//public class CartController {
//
//    private final CartService cartService;
//    private final AuthenticationService authenticationService;
//
//
//
//    @GetMapping
//    public ResponseEntity<List<Cart>> getCartByCustomerId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        Long userId = authenticationService.retrieveId(token.substring(7));
////        System.out.println(userId);
//        List<Cart> cartItems = cartService.getCartByCustomerId(userId);
//        return ResponseEntity.ok(cartItems);
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<Cart> addProductToCart(@RequestBody AddProductToCartDto addProductToCartDto,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        Long userId = authenticationService.retrieveId(token.substring(7));
//        Cart cartItem = cartService.addProductToCart(userId,addProductToCartDto.getProductId(),addProductToCartDto.getQuantity());
//        return ResponseEntity.ok(cartItem);
//    }
//
//    @PutMapping("/update/{cartId}")
//    public ResponseEntity<Cart> updateCartItemQuantity(@PathVariable Long cartId, @RequestBody UpdateQuantityInCartRequest updateQuantityInCartRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        try {
//            Long userId = authenticationService.retrieveId(token.substring(7));
//
//            Cart updatedCartItem = cartService.updateCartItemQuantity(cartId, updateQuantityInCartRequest.getQuantity(),userId);
//            return ResponseEntity.ok(updatedCartItem);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/remove/{cartId}")
//    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartId,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        Long userId = authenticationService.retrieveId(token.substring(7));
//        cartService.removeCartItem(cartId,userId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/clear")
//    public ResponseEntity<String> clearCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
//        Long userId = authenticationService.retrieveId(token.substring(7));
//
//        cartService.clearCart(userId);
//        return ResponseEntity.ok("Cart Cleared!");
//    }
//
//    @PostMapping("/checkout")
//    public ResponseEntity<String> cartCheckout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
//    {
//        Long userId = authenticationService.retrieveId(token.substring(7));
//        cartService.checkOut(userId);
//        return ResponseEntity.ok("Products transferred from cart to current orders.");
//
//    }
//}
