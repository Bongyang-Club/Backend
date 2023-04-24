package com.project.bongyang_club_backend.controller;

import com.project.bongyang_club_backend.dto.*;
import com.project.bongyang_club_backend.response.BasicResponse;
import com.project.bongyang_club_backend.service.schoolclub.SchoolClubService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schoolclub")
public class SchoolClubController {

    private final SchoolClubService schoolClubService;

    // 동아리 신청
    @PostMapping("/enroll")
    public ResponseEntity<BasicResponse> schoolClubEnroll(@RequestBody SchoolClubEnrollRequest schoolClubEnrollRequest) {
        return schoolClubService.schoolClubEnroll(schoolClubEnrollRequest);
    }

    // 신청: 학생 -> 동아리
    @PostMapping("/application")
    public ResponseEntity<BasicResponse> schoolClubApplication(@RequestBody SchoolClubApplicationRequest schoolClubApplicationRequest) {
        return schoolClubService.schoolClubApplication(schoolClubApplicationRequest);
    }

    // CLUB_LEADER // 동아리 신청 내역
    @PostMapping("/application/list")
    public ResponseEntity<BasicResponse> schoolClubApplicationList(@RequestBody SchoolClubApplicationListRequest schoolClubApplicationListRequest, HttpServletRequest request) {
        return schoolClubService.schoolClubApplicationList(schoolClubApplicationListRequest, request);
    }

    @PostMapping("/application/promotion")
    public ResponseEntity<BasicResponse> schoolClubPromotionApplication(@RequestBody SchoolClubPromotionApplicationRequest schoolClubPromotionApplicationDto) {
        return schoolClubService.schoolClubPromotionApplication(schoolClubPromotionApplicationDto);
    }

    // CLUB_LEADER // 학생 -> 동아리 신청 승인
    @PutMapping("/application/approve")
    public ResponseEntity<BasicResponse> schoolClubApplicationApprove(@RequestBody SchoolClubApplicationCheckRequest schoolClubApplicationCheckRequest) {
        return schoolClubService.schoolClubApplicationCheck(schoolClubApplicationCheckRequest, true);
    }

    // CLUB_LEADER // 학생 -> 동아리 신청 거절
    @PutMapping("/application/deny")
    public ResponseEntity<BasicResponse> schoolClubApplicationDeny(@RequestBody SchoolClubApplicationCheckRequest schoolClubApplicationCheckRequest) {
        return schoolClubService.schoolClubApplicationCheck(schoolClubApplicationCheckRequest, false);
    }

    // @PostMapping("/promotion")

}
