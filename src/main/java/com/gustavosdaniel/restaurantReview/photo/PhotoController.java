package com.gustavosdaniel.restaurantReview.photo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoMapper photoMapper;
    private final PhotoService photoService;

    @PostMapping
    public PhotoDTO uploadPhoto(@RequestParam("file") MultipartFile file ) {

        Photo savedPhoto = photoService.uploadPhoto(file);

        return photoMapper.toPhotoDTO(savedPhoto);

    }
}


