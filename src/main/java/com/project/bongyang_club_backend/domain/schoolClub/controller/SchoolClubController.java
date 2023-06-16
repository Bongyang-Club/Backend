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

    @GetMapping("/my/club")
    public ResponseEntity<BasicResponse> getMySchoolClub() {
        return schoolClubService.getMySchoolClub();
    }

    // 동아리원 리스트
    // clubName에는 공백이 포함되지 않으므로 공백은 "_"로 대체한다.
    @PostMapping("/members")
    public ResponseEntity<BasicResponse> getSchoolClubMembers(@RequestBody SchoolClubId clubId) {
        return schoolClubService.getSchoolClubMembers(clubId.getId());
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

}
