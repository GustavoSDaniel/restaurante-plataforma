package com.gustavosdaniel.restaurantReview.review;

import com.gustavosdaniel.restaurantReview.restaurant.RestaurantSummaryDTO;
import com.gustavosdaniel.restaurantReview.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/restaurants/{restaurantsId}/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(
            @PathVariable String restaurantsId,
            @Valid @RequestBody ReviewCreateUpdateRequestDTO reviewCreateUpdateRequestDTO,
            @AuthenticationPrincipal Jwt jwt
            ) {

        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper
                .toReviewCreateUpdateRequest(reviewCreateUpdateRequestDTO);

        User user = jwtUser(jwt);

       Review createdReview =  reviewService.createReview(user, restaurantsId, reviewCreateUpdateRequest);

       return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.toReviewDTO(createdReview));
    }

    @GetMapping
    public Page<ReviewDTO> listReviews(
            @PathVariable String restaurantsId,
            @PageableDefault(size = 20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return reviewService
                .listReviews(restaurantsId, pageable)
                .map(reviewMapper::toReviewDTO);

    }


    private User jwtUser(Jwt jwt) {
        return User.builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .build();
    }



}
