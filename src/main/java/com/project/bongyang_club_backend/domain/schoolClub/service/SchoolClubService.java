package com.project.bongyang_club_backend.domain.schoolClub.service;

import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface SchoolClubService {

    ResponseEntity<BasicResponse> getMySchoolClub();

    ResponseEntity<BasicResponse> getSchoolClubMembers(SchoolClubId clubId, boolean check);

    ResponseEntity<BasicResponse> schoolClubEnroll(SchoolClubEnrollRequest request);

    ResponseEntity<BasicResponse> schoolClubApplication(SchoolClubApplicationRequest request);

    ResponseEntity<BasicResponse> schoolClubApplicationList(SchoolClubApplicationListRequest request);

    ResponseEntity<BasicResponse> schoolClubPromotionApplication(SchoolClubPromotionApplicationRequest request, MultipartFile poster) throws IOException;

    ResponseEntity<BasicResponse> schoolClubApplicationCheck(SchoolClubApplicationCheckRequest request, boolean approve);

    ResponseEntity<BasicResponse> changeClubLeader(ChangeLeaderRequest request);

    ResponseEntity<BasicResponse> postNotice(PostNoticeRequest request);

    ResponseEntity<BasicResponse> getSchoolClubNotices(Long clubId);

    ResponseEntity<BasicResponse> deleteNotice(DeleteNoticeRequest request);

    ResponseEntity<BasicResponse> clubWithDraw(SchoolClubId clubId);

    ResponseEntity<BasicResponse> writeJournal(WriteClubJournalRequest request) throws Exception;

    ResponseEntity<BasicResponse> getSchoolClubById(SchoolClubId request);

}
