package com.project.bongyang_club_backend.domain.schoolClub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClubRepository extends JpaRepository<SchoolClub, Long> {
}
