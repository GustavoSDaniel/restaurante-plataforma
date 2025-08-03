package com.gustavosdaniel.restaurantReview.review;

import com.gustavosdaniel.restaurantReview.photo.Photo;
import com.gustavosdaniel.restaurantReview.photo.PhotoDTO;
import com.gustavosdaniel.restaurantReview.user.User;
import com.gustavosdaniel.restaurantReview.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDTO {

    private String id;

    private String comment;

    private Integer rating;

    private LocalDateTime datePosted;

    private LocalDateTime lastEdited;

    private List<PhotoDTO> photosDTO = new ArrayList<>();

    private UserDTO writtenBy;
}
