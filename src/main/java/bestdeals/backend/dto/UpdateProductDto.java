package bestdeals.backend.dto;

import bestdeals.backend.entities.Product;
import lombok.Data;

@Data
public class UpdateProductDto {
    private Product product;
    private String newImage;
}
