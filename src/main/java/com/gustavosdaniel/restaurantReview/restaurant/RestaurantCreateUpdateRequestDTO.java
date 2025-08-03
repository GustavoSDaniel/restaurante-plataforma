package com.gustavosdaniel.restaurantReview.restaurant;

import com.gustavosdaniel.restaurantReview.address.Address;
import com.gustavosdaniel.restaurantReview.address.AddressDTO;
import com.gustavosdaniel.restaurantReview.operatingHours.OperatingHours;
import com.gustavosdaniel.restaurantReview.operatingHours.OperatingHoursDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequestDTO {

    @NotBlank(message = "Restaurant name is é required")
    private String name;
    @NotBlank(message = "Cuisine type is é required")
    private String cuisineType;
    @NotBlank(message = "Contact information is é required")
    private String contactInformation;
    @Valid
    private AddressDTO addressDTO;
    @Valid
    private OperatingHoursDTO operatingHoursDTO;
    @Size(min = 1, message = "At least one photo ID is required")
    private List<String> photoIds;

}
