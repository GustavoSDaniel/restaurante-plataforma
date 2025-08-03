package com.gustavosdaniel.restaurantReview.restaurant;

import com.gustavosdaniel.restaurantReview.address.Address;
import com.gustavosdaniel.restaurantReview.address.AddressDTO;
import com.gustavosdaniel.restaurantReview.geoLocation.GeoPointDTO;
import com.gustavosdaniel.restaurantReview.operatingHours.OperatingHours;
import com.gustavosdaniel.restaurantReview.operatingHours.OperatingHoursDTO;
import com.gustavosdaniel.restaurantReview.photo.Photo;
import com.gustavosdaniel.restaurantReview.photo.PhotoDTO;
import com.gustavosdaniel.restaurantReview.review.Review;
import com.gustavosdaniel.restaurantReview.review.ReviewDTO;
import com.gustavosdaniel.restaurantReview.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantResponseDTO {


    private String id;

    private String name;

    private String cuisineType;

    private String contactInformation;

    private Float averageRating;

    private GeoPointDTO geoLocation;

    private AddressDTO addressDTO;

    private OperatingHoursDTO operatingHoursDTO;

    private List<PhotoDTO> photos = new ArrayList<>();

    private List<ReviewDTO> reviews = new ArrayList<>();

    private User createdBy;
}
