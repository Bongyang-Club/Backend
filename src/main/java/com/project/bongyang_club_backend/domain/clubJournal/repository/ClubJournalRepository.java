package com.project.bongyang_club_backend.domain.clubJournal.repository;

import com.project.bongyang_club_backend.domain.clubJournal.domain.ClubJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubJournalRepository extends JpaRepository<ClubJournal, Long> {
}
