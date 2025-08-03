package com.gustavosdaniel.restaurantReview.restaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(
            @Valid @RequestBody RestaurantCreateUpdateRequestDTO restaurantCreateUpdateRequestDTO
    ) {

        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest =  restaurantMapper.
                toRestaurantCreateUpdateRequest(restaurantCreateUpdateRequestDTO);

        Restaurant restaurant = restaurantService.createRestaurant(restaurantCreateUpdateRequest);

        RestaurantResponseDTO createRestaurantDTO = restaurantMapper.toRestaurantResponseDTO(restaurant);

        return ResponseEntity.status(HttpStatus.CREATED).body(createRestaurantDTO);



    }
}
