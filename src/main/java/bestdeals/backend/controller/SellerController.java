package bestdeals.backend.controller;

import bestdeals.backend.dto.AddNewProduct;
import bestdeals.backend.dto.UpdateProductDto;
import bestdeals.backend.entities.Category;
import bestdeals.backend.entities.CurrentOrder;
import bestdeals.backend.entities.Product;
import bestdeals.backend.entities.User;
import bestdeals.backend.repositories.CurrentOrderRepository;
import bestdeals.backend.services.AuthenticationService;
import bestdeals.backend.services.CurrentOrderService;
import bestdeals.backend.services.ProductService;
import bestdeals.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final CurrentOrderRepository currentOrderRepository;
    private final CurrentOrderService currentOrderService;

    @GetMapping
    public ResponseEntity<String> helo() {
        return ResponseEntity.ok("HIIIII SELLERRR");
    }

    @PostMapping(value = "/products/addproduct")
    public ResponseEntity<Product> createProduct(@RequestBody AddNewProduct addNewProduct) {
        try {
            System.out.println(addNewProduct);
            Product product = new Product();
            product.setName(addNewProduct.getName());
            product.setDescription(addNewProduct.getDescription());
            product.setPrice(addNewProduct.getPrice());
            product.setCategory(Category.valueOf(String.valueOf(addNewProduct.getCategory())));
            product.setSellerId(addNewProduct.getSellerId());
//            if(addNewProduct.getImage().equals()) check if image already exists
            product.setImageUrl(addNewProduct.getImage());
            Product savedProduct = productService.createProduct(product);
            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody UpdateProductDto updateProductDto, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if(!productService.isValidSeller(id,authenticationService.retrieveId(token.substring(7))))
            {
                return ResponseEntity.notFound().build();
            }
            Product updatedProduct;
            if(updateProductDto.getNewImage().equals("NA"))
            {
                updatedProduct = productService.updateProductWithoutImage(id, updateProductDto.getProduct());
            }
            else
            {
                updatedProduct = productService.updateProduct(id, updateProductDto.getProduct(),updateProductDto.getNewImage());
            }
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        if(productService.deleteProduct(id,authenticationService.retrieveId(token.substring(7))))
        {
            return ResponseEntity.ok("product Deleted");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to delete this product.");
    }

    //ORDERS ------
    @GetMapping("/orders/all")
    public List<CurrentOrder> getOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        Long sellerId = authenticationService.retrieveId(token.substring(7));
        return currentOrderRepository.findBySellerId(sellerId);
    }

    @PostMapping("orders/setprocessing/{id}")
    public CurrentOrder setOrderStatusProcessing(@PathVariable("id") Long orderId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        Long sellerId = authenticationService.retrieveId(token.substring(7));
        return currentOrderService.setProcessingOrderStatus(orderId,sellerId);
    }

    @PostMapping("orders/setshipped/{id}")
    public CurrentOrder setOrderStatusShipped(@PathVariable("id") Long orderId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token)
    {
        Long sellerId = authenticationService.retrieveId(token.substring(7));
        return currentOrderService.setShippedOrderStatus(orderId,sellerId);
    }
}
