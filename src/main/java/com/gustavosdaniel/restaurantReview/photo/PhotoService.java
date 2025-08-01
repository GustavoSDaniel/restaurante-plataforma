package com.gustavosdaniel.restaurantReview.photo;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface PhotoService {

    Photo uploadPhoto(MultipartFile file);

    Optional<Resource> getPhotoAsResource(String id);


}
