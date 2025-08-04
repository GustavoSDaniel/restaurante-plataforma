package com.gustavosdaniel.restaurantReview.restaurant;

import com.gustavosdaniel.restaurantReview.address.AddressDTO;
import com.gustavosdaniel.restaurantReview.photo.PhotoDTO;
import com.gustavosdaniel.restaurantReview.review.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantSummaryDTO {

    private String id;
    private String name;
    private String cuisineType;
    private Float averageRating;
    private Integer totalReviews;
    private AddressDTO address;
    private List<PhotoDTO> photoDTOS;
}
