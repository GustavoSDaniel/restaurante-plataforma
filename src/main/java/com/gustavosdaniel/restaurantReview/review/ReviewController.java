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

    @GetMapping(path = "/{reviewsId}")
    public ResponseEntity<ReviewDTO> getReview(
            @PathVariable String restaurantsId,
            @PathVariable String reviewsId
    ){
        return reviewService.getReview(restaurantsId, reviewsId)
        .map(reviewMapper::toReviewDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // orElseGet so vai se chamado se for nulo
    }

    @PutMapping(path = "/{reviewsId}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable String restaurantsId,
            @PathVariable String reviewsId,
            @Valid @RequestBody ReviewCreateUpdateRequestDTO reviewCreateUpdateRequestDTO,
            @AuthenticationPrincipal Jwt jwt
    ){
        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper
                .toReviewCreateUpdateRequest(reviewCreateUpdateRequestDTO);

        User user = jwtUser(jwt);

        Review updateReview = reviewService.updateReview(restaurantsId, reviewsId, user, reviewCreateUpdateRequest);

        return ResponseEntity.ok(reviewMapper.toReviewDTO(updateReview));
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
