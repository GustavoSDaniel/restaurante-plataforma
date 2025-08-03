package com.gustavosdaniel.restaurantReview.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {

    private String name;
    private String cuisineType;
    private double latitude;
    private double longitude;
}
