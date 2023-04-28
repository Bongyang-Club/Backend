package com.project.bongyang_club_backend.controller;

import com.project.bongyang_club_backend.dto.SignRequest;
import com.project.bongyang_club_backend.response.BasicResponse;
import com.project.bongyang_club_backend.security.JWTProvider;
import com.project.bongyang_club_backend.service.member.MemberService;
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

    private final JWTProvider jwtProvider;

    @GetMapping("/member")
    public ResponseEntity<BasicResponse> getMemberByToken() {
        return memberService.getMemberByToken();
    }

    @PostMapping("/login" )
    public ResponseEntity<BasicResponse> login(@RequestBody SignRequest request) {
        return memberService.login(request);
    }

}