package com.gustavosdaniel.restaurantReview.geoLocation;

import com.gustavosdaniel.restaurantReview.address.Address;

public interface GeoLocationService {

        GeoLocation getGeoLocation(Address address);
}
