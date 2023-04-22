package com.project.bongyang_club_backend.controller;

import com.project.bongyang_club_backend.response.BasicResponse;
import com.project.bongyang_club_backend.service.test.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @PostMapping("/add")
    public ResponseEntity<BasicResponse> testAdd() {
        return testService.test();
    }

}
