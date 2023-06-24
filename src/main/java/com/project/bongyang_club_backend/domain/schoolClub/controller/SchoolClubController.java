package com.project.bongyang_club_backend.domain.schoolClub.controller;

import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import com.project.bongyang_club_backend.domain.schoolClub.service.SchoolClubService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schoolclub")
@Tag(name = "SchoolClub", description = "SchoolClub Management API's")
public class SchoolClubController {

    private final SchoolClubService schoolClubService;

    // 자신이 가입한 동아리 리스트
    @GetMapping("/my/club")
    public ResponseEntity<BasicResponse> getMySchoolClub() {
        return schoolClubService.getMySchoolClub();
    }

    // 동아리 공지 가져오기
    @GetMapping("/notices/{clubId}")
    public ResponseEntity<BasicResponse> getSchoolClubNotices(@PathVariable Long clubId) {
        return schoolClubService.getSchoolClubNotices(clubId);
    }

    // 동아리원 리스트
    // clubName에는 공백이 포함되지 않으므로 공백은 "_"로 대체한다.
    @PostMapping("/members")
    public ResponseEntity<BasicResponse> getSchoolClubMembers(@RequestBody SchoolClubId clubId) {
        return schoolClubService.getSchoolClubMembers(clubId.getId());
    }

    // 공지 등록
    @PostMapping("/notice")
    public ResponseEntity<BasicResponse> postNotice(@RequestBody @Valid PostNoticeRequest request) {
        return schoolClubService.postNotice(request);
    }

    // 동아리 신청
    @PostMapping("/enroll")
    public ResponseEntity<BasicResponse> schoolClubEnroll(@RequestBody @Valid SchoolClubEnrollRequest request) {
        return schoolClubService.schoolClubEnroll(request);
    }

    // 신청: 학생 -> 동아리
    @PostMapping("/application")
    public ResponseEntity<BasicResponse> schoolClubApplication(@RequestBody @Valid SchoolClubApplicationRequest request) {
        return schoolClubService.schoolClubApplication(request);
    }

    // 동아리 신청(학생 -> 동아리) 리스트
    @PostMapping("/application/list")
    public ResponseEntity<BasicResponse> schoolClubApplicationList(@RequestBody @Valid SchoolClubApplicationListRequest request) {
        return schoolClubService.schoolClubApplicationList(request);
    }

    // 개발 중
    @PostMapping("/application/promotion")
    public ResponseEntity<BasicResponse> schoolClubPromotionApplication(@RequestBody @Valid SchoolClubPromotionApplicationRequest request) {
        return schoolClubService.schoolClubPromotionApplication(request);
    }

    // CLUB_LEADER // 학생 -> 동아리 신청 승인
    @PutMapping("/application/approve")
    public ResponseEntity<BasicResponse> schoolClubApplicationApprove(@RequestBody @Valid SchoolClubApplicationCheckRequest request) {
        return schoolClubService.schoolClubApplicationCheck(request, true);
    }

    // CLUB_LEADER // 학생 -> 동아리 신청 거절
    @PutMapping("/application/deny")
    public ResponseEntity<BasicResponse> schoolClubApplicationDeny(@RequestBody @Valid SchoolClubApplicationCheckRequest request) {
        return schoolClubService.schoolClubApplicationCheck(request, false);
    }

    // 동아리장 변경
    @PutMapping("/leader/change")
    public ResponseEntity<BasicResponse> changeClubLeader(@RequestBody @Valid ChangeLeaderRequest request) {
        return schoolClubService.changeClubLeader(request);
    }

    // 공지 삭제
    @DeleteMapping("/notice")
    public ResponseEntity<BasicResponse> deleteNotice(@RequestBody @Valid DeleteNoticeRequest request) {
        return schoolClubService.deleteNotice(request);
    }

}
