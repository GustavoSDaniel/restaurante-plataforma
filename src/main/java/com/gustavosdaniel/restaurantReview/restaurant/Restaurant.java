package com.gustavosdaniel.restaurantReview.restaurant;

import co.elastic.clients.elasticsearch._types.GeoBounds;
import com.gustavosdaniel.restaurantReview.address.Address;
import com.gustavosdaniel.restaurantReview.operatingHours.OperatingHours;
import com.gustavosdaniel.restaurantReview.photo.Photo;
import com.gustavosdaniel.restaurantReview.review.Review;
import com.gustavosdaniel.restaurantReview.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.util.ArrayList;
import java.util.List;

@Document(indexName = "restaurants")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Restaurant {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String cuisineType;

    @Field(type = FieldType.Keyword)
    private String contactInformation;

    @Field(type = FieldType.Float)
    private Float averageRating;

    @GeoPointField
    private GeoBounds geoLocation;

    @Field(type = FieldType.Nested)
    private Address address;

    @Field(type = FieldType.Nested)
    private OperatingHours operatingHours;

    @Field(type = FieldType.Nested)
    private List<Photo> photos = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private List<Review> reviews = new ArrayList<>();

    @Field(type = FieldType.Nested)
    private User createdBy;

}
