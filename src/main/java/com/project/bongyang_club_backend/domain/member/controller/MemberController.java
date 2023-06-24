package com.project.bongyang_club_backend.domain.member.controller;

import com.project.bongyang_club_backend.domain.member.dto.SignRequest;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import com.project.bongyang_club_backend.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Member", description = "Member Management API's")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public ResponseEntity<BasicResponse> getMemberByToken() {
        return memberService.getMemberByToken();
    }

    @GetMapping("/member/{clubId}")
    public ResponseEntity<BasicResponse> checkClubLeader(@PathVariable Long clubId) {
        return memberService.checkClubLeader(clubId);
    }

    @PostMapping("/login" )
    public ResponseEntity<BasicResponse> login(@RequestBody SignRequest request) {
        return memberService.login(request);
    }

}