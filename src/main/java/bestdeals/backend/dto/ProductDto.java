package bestdeals.backend.dto;

import bestdeals.backend.entities.Category;
import bestdeals.backend.entities.Product;
import bestdeals.backend.entities.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private float price;
    private String imageUrl;
    private List<ReviewDto> reviews;
    private Category category;
    private Long sellerId;

    public static ProductDto convertToDTO(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategory(product.getCategory());
        dto.setSellerId(product.getSellerId());

        // Map reviews to ReviewDto objects
        dto.setReviews(product.getReviews().stream()
                .map(ReviewDto::convertToDTO)
                .collect(Collectors.toList()));

        return dto;
    }
}
