package bestdeals.backend.dto;

import bestdeals.backend.entities.Review;
import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private int rating;
    private String reviewText;
    private String customerName;

    public static ReviewDto convertToDTO(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        dto.setCustomerName(review.getCustomerName());
        return dto;
    }
}
