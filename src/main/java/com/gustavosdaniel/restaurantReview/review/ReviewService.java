package com.gustavosdaniel.restaurantReview.review;

import com.gustavosdaniel.restaurantReview.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest reviewCreateUpdateRequest);

    Page<Review> listReviews(String restaurantId, Pageable pageable);
}
