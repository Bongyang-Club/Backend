package com.project.bongyang_club_backend.domain.clubJournal.service;

import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ClubJournalService {

    ResponseEntity<BasicResponse> writeClubJournal();

}
