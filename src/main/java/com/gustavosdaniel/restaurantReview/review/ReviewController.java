package com.gustavosdaniel.restaurantReview.review;

import com.gustavosdaniel.restaurantReview.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

        reviewService.createReview(jwt)
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
