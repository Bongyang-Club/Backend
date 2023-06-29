package com.project.bongyang_club_backend.domain.poster.service;

import com.project.bongyang_club_backend.domain.poster.domain.Poster;
import com.project.bongyang_club_backend.domain.poster.repository.PosterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PosterServiceImpl implements PosterService {

    private final PosterRepository posterRepository;

    private final String path = "image" + File.separator;

    private final String absolutePath = new File("./poster").getAbsolutePath() + File.separator;

    @Override
    public List<Poster> savePosters(List<MultipartFile> multipartFiles) throws IOException {
        List<Poster> posters = new ArrayList<>();

        if (multipartFiles != null) {
            File file = new File(path);

            if (!file.exists()) {
                if (!file.mkdir()) {
                    log.info("file was not successful");
                }
            }

            for (MultipartFile multipartFile : multipartFiles) {
                log.info("fileName: {}", multipartFile.getOriginalFilename());

                String fileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if(contentType.contains("image/jpeg")) {
                        fileExtension = ".jpg";
                    } else if(contentType.contains("image/png")) {
                        fileExtension = ".png";
                    } else {
                        break;
                    }
                }

                String fileSaveName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename() + fileExtension;
                Poster image = Poster.builder()
                        .name(multipartFile.getOriginalFilename())
                        .saveName(fileSaveName)
                        .path(path + fileSaveName + File.separator)
                        .build();

                posters.add(image);
                posterRepository.save(image);

                file = new File(absolutePath + File.separator + fileSaveName);

                multipartFile.transferTo(file);
            }
        } else {
            log.info("MultipartFiles Not Found");
        }

        return posters;
    }

}
