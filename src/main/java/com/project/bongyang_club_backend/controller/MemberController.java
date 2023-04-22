package com.project.bongyang_club_backend.controller;

import com.project.bongyang_club_backend.dto.SignRequest;
import com.project.bongyang_club_backend.dto.SignResponse;
import com.project.bongyang_club_backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<SignResponse> login(@RequestBody SignRequest request) {
        return memberService.login(request);
    }

}