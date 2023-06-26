package com.project.bongyang_club_backend.domain.member.controller;

import com.project.bongyang_club_backend.domain.member.service.MemberService;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/clubs")
    public ResponseEntity<BasicResponse> getSchoolClubs(@RequestParam(required = false) String name) {
        return memberService.getSchoolClubs(name);
    }

}
