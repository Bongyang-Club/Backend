package com.project.bongyang_club_backend.domain.poster.repository;

import com.project.bongyang_club_backend.domain.poster.domain.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
}
