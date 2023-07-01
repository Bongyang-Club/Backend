package com.project.bongyang_club_backend.domain.clubJournal.service;

import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import com.project.bongyang_club_backend.domain.schoolClub.dto.WriteClubJournalRequest;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface ClubJournalService {

    ResponseEntity<BasicResponse> writeClubJournal(WriteClubJournalRequest request, SchoolClub schoolClub) throws Exception;

}
