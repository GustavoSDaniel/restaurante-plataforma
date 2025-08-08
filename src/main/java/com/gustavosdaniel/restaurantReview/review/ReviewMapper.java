package com.gustavosdaniel.restaurantReview.review;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDTO reviewCreateUpdateRequestDTO);

    ReviewDTO toReviewDTO(Review review);
}
