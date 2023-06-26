package com.project.bongyang_club_backend.domain.schoolClub.service;

import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SchoolClubService {

    ResponseEntity<BasicResponse> getMySchoolClub();

    ResponseEntity<BasicResponse> getSchoolClubMembers(SchoolClubId clubId, boolean check);

    ResponseEntity<BasicResponse> schoolClubEnroll(SchoolClubEnrollRequest request);

    ResponseEntity<BasicResponse> schoolClubApplication(SchoolClubApplicationRequest request);

    ResponseEntity<BasicResponse> schoolClubApplicationList(SchoolClubApplicationListRequest request);

    ResponseEntity<BasicResponse> schoolClubPromotionApplication(SchoolClubPromotionApplicationRequest request);

    ResponseEntity<BasicResponse> schoolClubApplicationCheck(SchoolClubApplicationCheckRequest request, boolean approve);

    ResponseEntity<BasicResponse> changeClubLeader(ChangeLeaderRequest request);

    ResponseEntity<BasicResponse> postNotice(PostNoticeRequest request);

    ResponseEntity<BasicResponse> getSchoolClubNotices(Long clubId);

    ResponseEntity<BasicResponse> deleteNotice(DeleteNoticeRequest request);

}
