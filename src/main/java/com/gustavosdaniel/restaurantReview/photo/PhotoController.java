package com.gustavosdaniel.restaurantReview.photo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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

    @GetMapping(path = "/{id:.+}")
    public ResponseEntity<Resource> getPhoto(@PathVariable String id) {

        if (id == null || id.trim().isEmpty() ||
                id.contains("..") || id.contains("/") || id.contains("\\")) {
            return ResponseEntity.badRequest().build();
        }

        return photoService.getPhotoAsResource(id).map(photo ->
                ResponseEntity.ok()
                        .contentType(
                                MediaTypeFactory.getMediaType(photo)
                                        .orElse(MediaType.APPLICATION_OCTET_STREAM)
                        )
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline") // para exibir a foto no navegador
                        .body(photo)

        ).orElse(ResponseEntity.notFound().build());



    }
}


