package bestdeals.backend.services;

import bestdeals.backend.entities.Product;
import bestdeals.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final String deleteDir = "src/main/resources/static/images/products";

    @Autowired
    private ProductRepository productRepository;

    public String getProductNameById(Long id)
    {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id))
                .getName();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product,String image) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        deleteImage(existingProduct.getImageUrl());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        System.out.println("updated new image" + image);
        existingProduct.setImageUrl(image);
        existingProduct.setSellerId(product.getSellerId());

        return productRepository.save(existingProduct);
    }

    public Product updateProductWithoutImage(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setSellerId(product.getSellerId());

        return productRepository.save(existingProduct);
    }


    public boolean deleteProduct(Long id, Long seller_id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getSellerId().equals(seller_id)) {
                deleteImage(product.getImageUrl());
                productRepository.deleteById(id);
                return true; // Product successfully deleted
            }
        }
        return false;
    }

    private void deleteImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                // Assuming images are stored in a directory on the server
//                System.out.println(imageUrl);
                Path deletePath = Paths.get(deleteDir);
//                System.out.println(deletePath);
                if (!Files.exists(deletePath)) {
                    Files.createDirectories(deletePath);
                }
                Path filePath = deletePath.resolve(imageUrl);

//                System.out.println("Attempting to delete image at: " + filePath);

                if (Files.exists(filePath)) {
                    Files.deleteIfExists(filePath);
//                    System.out.println("Deleted image: " + imageUrl);
                } else {
                    System.out.println("Image not found: " + filePath);
                }
            } catch (IOException e) {
                // Handle specific IOException (e.g., file not found, permission denied)
                System.err.println("Error deleting image: " + imageUrl);
                e.printStackTrace();
            } catch (Exception e) {
                // Handle any other unexpected exceptions
                System.err.println("Unexpected error deleting image: " + imageUrl);
                e.printStackTrace();
            }
        } else {
            System.out.println("Image URL is null or empty.");
        }
    }

    public boolean isValidSeller(Long productId, Long sellerId)
    {
        if(productRepository.findById(productId).isPresent() && productRepository.findById(productId).get().getSellerId().equals(sellerId)) return true;
        return false;
    }

    public Long retrieveSellerId(Long productId)
    {
        return productRepository.findById(productId).get().getSellerId();
    }
}
