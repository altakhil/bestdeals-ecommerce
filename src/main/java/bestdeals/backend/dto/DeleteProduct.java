package bestdeals.backend.dto;

import lombok.Data;

@Data
public class DeleteProduct {
    private Long sellerId;
    private Long productId;
}

