package com.gustavosdaniel.restaurantReview.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {

    Restaurant createRestaurant(RestaurantCreateUpdateRequest restaurantCreateUpdateRequest);

    Page<Restaurant> searchRestaurant(
            String query,
            Float minRating,
            Float latitude,
            Float longitude,
            Float radius,
            Pageable pageable);
}
