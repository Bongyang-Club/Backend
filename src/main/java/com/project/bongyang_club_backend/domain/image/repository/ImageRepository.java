package com.project.bongyang_club_backend.domain.image.repository;

import com.project.bongyang_club_backend.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
