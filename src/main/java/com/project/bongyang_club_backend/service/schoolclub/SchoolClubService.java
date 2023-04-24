package com.project.bongyang_club_backend.service.schoolclub;

import com.project.bongyang_club_backend.dto.*;
import com.project.bongyang_club_backend.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SchoolClubService {

    ResponseEntity<BasicResponse> schoolClubEnroll(SchoolClubEnrollRequest schoolClubEnrollRequest);

    ResponseEntity<BasicResponse> schoolClubApplication(SchoolClubApplicationRequest schoolClubApplicationRequest);

    ResponseEntity<BasicResponse> schoolClubApplicationList(SchoolClubApplicationListRequest schoolClubApplicationListRequest);

    ResponseEntity<BasicResponse> schoolClubPromotionApplication(SchoolClubPromotionApplicationRequest schoolClubPromotionApplicationDto);

    ResponseEntity<BasicResponse> schoolClubApplicationCheck(SchoolClubApplicationCheckRequest schoolClubApplicationCheckRequest, boolean approve);

}