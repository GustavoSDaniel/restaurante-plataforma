package com.gustavosdaniel.restaurantReview.review;

import com.gustavosdaniel.restaurantReview.user.User;

public interface ReviewService {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest reviewCreateUpdateRequest);
}
