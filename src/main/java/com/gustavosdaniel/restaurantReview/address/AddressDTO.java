package com.gustavosdaniel.restaurantReview.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    @NotBlank(message = "Street number is required")
    @Pattern(regexp = "^[\\p{L}0-9\\s.,'-]+(\\s\\d+[a-zA-Z]?)?$", message = "Invalid street number format")
    private String streetNumber;

    @NotBlank(message = "Street name is required")
    private String streetName;

    private String unit;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "CEP inválido. Use o formato 00000-000 ou 00000000")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;
}
