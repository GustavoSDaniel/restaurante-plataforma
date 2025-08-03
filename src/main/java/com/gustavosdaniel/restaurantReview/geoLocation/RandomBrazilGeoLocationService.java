package com.gustavosdaniel.restaurantReview.geoLocation;

import com.gustavosdaniel.restaurantReview.address.Address;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomBrazilGeoLocationService implements GeoLocationService {

    private static final double BRAZIL_MIN_LATITUDE = -33.7;  // Chuí (RS)
    private static final double BRAZIL_MAX_LATITUDE = 5.3;    // Oiapoque (AP)
    private static final double BRAZIL_MIN_LONGITUDE = -74.0; // Acre
    private static final double BRAZIL_MAX_LONGITUDE = -34.0; // Fernando de Noronha (PE)


    @Override
    public GeoLocation getGeoLocation(Address address) {
        Random random = new Random();
        double latitude = BRAZIL_MIN_LATITUDE + random.nextDouble() * (BRAZIL_MAX_LATITUDE - BRAZIL_MIN_LATITUDE);
        double longitude = BRAZIL_MIN_LONGITUDE + random.nextDouble() * (BRAZIL_MAX_LONGITUDE - BRAZIL_MIN_LONGITUDE);

        return GeoLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
