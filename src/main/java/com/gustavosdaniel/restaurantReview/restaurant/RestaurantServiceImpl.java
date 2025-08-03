package com.gustavosdaniel.restaurantReview.restaurant;

import com.gustavosdaniel.restaurantReview.address.Address;
import com.gustavosdaniel.restaurantReview.geoLocation.GeoLocation;
import com.gustavosdaniel.restaurantReview.geoLocation.GeoLocationService;
import com.gustavosdaniel.restaurantReview.photo.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public Restaurant createRestaurant(RestaurantCreateUpdateRequest restaurantCreateUpdateRequest) {

        Address address = restaurantCreateUpdateRequest.getAddress();

        GeoLocation geoLocation = geoLocationService.getGeoLocation(address);

        GeoPoint geoPoint = new GeoPoint(geoLocation.getLatitude(), geoLocation.getLongitude());

        List<String> photoIds = restaurantCreateUpdateRequest.getPhotoIds();

        List<Photo> photos = photoIds.stream()
                .map( photoUrl -> Photo.builder()
                        .url(photoUrl)
                        .uploadDate(LocalDateTime.now())
                        .build())
                .toList();

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantCreateUpdateRequest.getName())
                .cuisineType(restaurantCreateUpdateRequest.getCuisineType())
                .contactInformation(restaurantCreateUpdateRequest.getContactInformation())
                .address(address)
                .geoLocation(geoPoint)
                .operatingHours(restaurantCreateUpdateRequest.getOperatingHours())
                .averageRating(0f)
                .photos(photos)
                .build();

        return restaurantRepository.save(restaurant);

    }
}
