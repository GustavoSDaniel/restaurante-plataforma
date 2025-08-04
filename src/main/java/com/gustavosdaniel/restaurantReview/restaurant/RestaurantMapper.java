package com.gustavosdaniel.restaurantReview.restaurant;

import com.gustavosdaniel.restaurantReview.geoLocation.GeoPointDTO;
import com.gustavosdaniel.restaurantReview.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDTO restaurantCreateUpdateRequestDTO);

    RestaurantResponseDTO toRestaurantResponseDTO(Restaurant restaurant);

    @Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
    @Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
    GeoPointDTO toGeoPointDTO(GeoPoint geoPoint);


    // source = "reviews": Indica que o valor será retirado da lista de reviews que existe dentro da entidade Restaurant
    // target = "totalReviews": Indica que o valor será inserido no campo totalReviews do DTO RestaurantSummaryDTO.
    // qualifiedByName = "populateTotalReviews": Diz ao MapStruct para usar ométodo populateTotalReviews para realizar a conversão. Isso é necessário porque o MapStruct não sabe como converter uma List<Review> para um Integer por conta própria.
    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    RestaurantSummaryDTO toRestaurantSummaryDTO(Restaurant restaurant);

    @Named("populateTotalReviews")
    default Integer populateTotalReviews(List<Review> reviews) {
        return reviews.size();
    }
}
