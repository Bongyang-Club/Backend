package com.project.bongyang_club_backend.domain.image.service;

import com.project.bongyang_club_backend.domain.image.domain.Image;
import com.project.bongyang_club_backend.domain.poster.domain.Poster;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ImageService {

    List<Image> saveImage(List<MultipartFile> multipartFiles) throws IOException;

}
