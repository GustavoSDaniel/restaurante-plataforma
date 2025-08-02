package com.gustavosdaniel.restaurantReview.photo;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    PhotoDTO toPhotoDTO(Photo photo);

}
