package com.project.bongyang_club_backend.domain.schoolClub.service;

import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SchoolClubService {

    ResponseEntity<BasicResponse> getSchoolClubMembers(Long clubId);

    ResponseEntity<BasicResponse> schoolClubEnroll(SchoolClubEnrollRequest request);

    ResponseEntity<BasicResponse> schoolClubApplication(SchoolClubApplicationRequest request);

    ResponseEntity<BasicResponse> schoolClubApplicationList(SchoolClubApplicationListRequest request);

    ResponseEntity<BasicResponse> schoolClubPromotionApplication(SchoolClubPromotionApplicationRequest request);

    ResponseEntity<BasicResponse> schoolClubApplicationCheck(SchoolClubApplicationCheckRequest request, boolean approve);

}
