package com.project.bongyang_club_backend.domain.schoolClub.controller;

import com.project.bongyang_club_backend.domain.promotionPost.service.PromotionPostService;
import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import com.project.bongyang_club_backend.domain.schoolClub.service.SchoolClubService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Basic;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schoolclub")
@Tag(name = "SchoolClub", description = "SchoolClub Management API's")
public class SchoolClubController {

    private final PromotionPostService promotionPostService;

    private final SchoolClubService schoolClubService;

    // 자신이 가입한 동아리 리스트
    @GetMapping("/my/club") //
    public ResponseEntity<BasicResponse> getMySchoolClub() {
        return schoolClubService.getMySchoolClub();
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<BasicResponse> getSchoolClubById(@PathVariable Long clubId) {
        return schoolClubService.getSchoolClubById(clubId);
    }

    // 동아리 공지 가져오기
    @GetMapping("/notices/{clubId}") //
    public ResponseEntity<BasicResponse> getSchoolClubNotices(@PathVariable Long clubId) {
        return schoolClubService.getSchoolClubNotices(clubId);
    }

    @GetMapping("/journal/{clubId}")
    public ResponseEntity<BasicResponse> getJournalById(@PathVariable Long clubId) {
        return schoolClubService.getJournalById(clubId);
    }

    @GetMapping("/promotions") //
    public ResponseEntity<BasicResponse> getPromotions() {
        return promotionPostService.getPromotionPosts();
    }

    @GetMapping("/promotion/{id}") //
    public ResponseEntity<BasicResponse> getPromotionById(@PathVariable Long id) {
        return promotionPostService.getPromotionPostById(id);
    }

    // 동아리원 리스트
    // clubName에는 공백이 포함되지 않으므로 공백은 "_"로 대체한다.
    @PostMapping("/members") //
    public ResponseEntity<BasicResponse> getSchoolClubMembers(@RequestBody SchoolClubId clubId) {
        return schoolClubService.getSchoolClubMembers(clubId, true);
    }

    @PostMapping("/application/members") //
    public ResponseEntity<BasicResponse> getSchoolClubApplicationMembers(@RequestBody SchoolClubId clubId) {
        return schoolClubService.getSchoolClubMembers(clubId, false);
    }

    @PostMapping("/club/image") //
    public ResponseEntity<BasicResponse> SchoolClubImage(@RequestPart SchoolClubId request,
                                                         @RequestPart MultipartFile multipartFile) throws IOException {
        return schoolClubService.schoolClubImage(request, multipartFile);
    }

    // 공지 등록
    @PostMapping("/notice") //
    public ResponseEntity<BasicResponse> postNotice(@RequestBody @Valid PostNoticeRequest request) {
        return schoolClubService.postNotice(request);
    }

    // 동아리 신청
    @PostMapping("/enroll") //
    public ResponseEntity<BasicResponse> schoolClubEnroll(@RequestBody @Valid SchoolClubEnrollRequest request) {
        return schoolClubService.schoolClubEnroll(request);
    }

    // 신청: 학생 -> 동아리
    @PostMapping("/application") //
    public ResponseEntity<BasicResponse> schoolClubApplication(@RequestBody @Valid SchoolClubApplicationRequest request) {
        return schoolClubService.schoolClubApplication(request);
    }

    // 동아리 신청(학생 -> 동아리) 리스트
    @PostMapping("/application/list") //
    public ResponseEntity<BasicResponse> schoolClubApplicationList(@RequestBody @Valid SchoolClubApplicationListRequest request) {
        return schoolClubService.schoolClubApplicationList(request);
    }

    // 개발 중
    @PostMapping("/application/promotion") //
    public ResponseEntity<BasicResponse> schoolClubPromotionApplication(@RequestPart @Valid SchoolClubPromotionApplicationRequest request,
                                                                        @RequestPart MultipartFile poster) throws IOException {
        return schoolClubService.schoolClubPromotionApplication(request, poster);
    }

    @PostMapping("/journal")
    public ResponseEntity<BasicResponse> writeJournal(@RequestBody @Valid WriteClubJournalRequest request) throws Exception {
        return schoolClubService.writeJournal(request);
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

    @PutMapping("/withdraw")
    public ResponseEntity<BasicResponse> clubWithDraw(@RequestBody SchoolClubId clubId) {
        return schoolClubService.clubWithDraw(clubId);
    }

    // 공지 삭제
    @PutMapping("/notice")
    public ResponseEntity<BasicResponse> deleteNotice(@RequestBody @Valid DeleteNoticeRequest request) {
        return schoolClubService.deleteNotice(request);
    }

    @PutMapping("/club/delete/member")
    public ResponseEntity<BasicResponse> deleteClubMember(@RequestBody @Valid DeleteClubMemberRequest request) {
        return schoolClubService.deleteClubMember(request);
    }

}
