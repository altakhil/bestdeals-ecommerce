package bestdeals.backend.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddNewProduct {
    private String name;
    private String description;
    private float price;
    private String category;
    private Long sellerId;  // Assuming sellerId is sent from the front-end
    private String image;
}

