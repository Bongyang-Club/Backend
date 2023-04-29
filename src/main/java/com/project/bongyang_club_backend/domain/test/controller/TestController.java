package com.project.bongyang_club_backend.domain.test.controller;

import com.project.bongyang_club_backend.domain.test.dto.TestDto;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import com.project.bongyang_club_backend.domain.test.service.TestService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @PostMapping("/add")
    public ResponseEntity<BasicResponse> testAdd() {
        return testService.test();
    }

    @PostMapping("/valid")
    public ResponseEntity<?> testValid(@RequestBody @Valid TestDto testDto) {
        System.out.println("성공");

        return null;
    }

}
