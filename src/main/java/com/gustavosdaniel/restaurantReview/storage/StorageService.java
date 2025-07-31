package com.gustavosdaniel.restaurantReview.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface StorageService {

    String store(MultipartFile file, String filename);  // MultipartFile Para upload de arquivos em aplicações web
    Optional<Resource> loadsResource(String id); // Resource acessar recursos via URL
}
