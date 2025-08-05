package com.gustavosdaniel.restaurantReview.restaurant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Page<RestaurantSummaryDTO> searchRestaurant(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        // implementações de paginação no Spring Data, incluindo PageRequest, usa um índice baseado em 0
        // Se o usuário da API pedir a página 1, o código envia o índice 0 (1 - 1).
        Page<Restaurant> searchResult = restaurantService.searchRestaurant(
                query, minRating, latitude, longitude, radius, PageRequest.of(page -1, size));

        return searchResult.map(restaurantMapper::toRestaurantSummaryDTO);
    }

    @GetMapping(path = "/{restaurant_id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(@PathVariable("restaurant_id") String restaurantId) {

        return restaurantService.getRestaurant(restaurantId).map(restaurant ->
                ResponseEntity.ok(restaurantMapper.toRestaurantResponseDTO(restaurant)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{restaurant_id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable("restaurant_id") String restaurantId,
            @Valid @RequestBody RestaurantCreateUpdateRequestDTO restaurantCreateUpdateRequestDTO
    ){
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantMapper
                .toRestaurantCreateUpdateRequest(restaurantCreateUpdateRequestDTO);

        Restaurant updateRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantCreateUpdateRequest);

        return ResponseEntity.ok(restaurantMapper.toRestaurantResponseDTO(updateRestaurant));
    }

}
