package com.gustavosdaniel.restaurantReview.restaurant;

import com.gustavosdaniel.restaurantReview.address.Address;
import com.gustavosdaniel.restaurantReview.geoLocation.GeoLocation;
import com.gustavosdaniel.restaurantReview.geoLocation.GeoLocationService;
import com.gustavosdaniel.restaurantReview.photo.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<Restaurant> searchRestaurant(
            String query, Float minRating, Float latitude,
            Float longitude, Float radius, Pageable pageable) {


        if (minRating != null && (query == null || query.isEmpty())) { // busca será realizada exclusivamente pela avaliação mínima.
            return restaurantRepository.findByAverageRatingGreaterThanEqual(minRating, pageable);
        }

        Float searchMinRating = null == minRating ? 0f : minRating;

        if (query != null && !query.trim().isEmpty()) { // ele busca restaurantes que correspondam ao termo da query e  que também tenham uma avaliação igual ou superior a searchMinRating
            return restaurantRepository.findByQueryAndMinRating(query, searchMinRating, pageable);
        }

        if (latitude != null && longitude != null) { // rocura restaurantes próximos a um ponto geográfico, com base em um radius (raio)
            return restaurantRepository.findByLocationNear(latitude, longitude, radius, pageable);
        }

        return restaurantRepository.findAll(pageable);
    }

    @Override
    public Optional<Restaurant> getRestaurant(String id) {

       return restaurantRepository.findById(id);
    }
}
