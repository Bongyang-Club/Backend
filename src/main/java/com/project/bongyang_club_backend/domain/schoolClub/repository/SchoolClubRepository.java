package com.project.bongyang_club_backend.domain.schoolClub.repository;

import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClubRepository extends JpaRepository<SchoolClub, Long> {

    Optional<SchoolClub> findByName(String clubName);

    List<SchoolClub> findAllByName(String clubName);

}
