package com.project.bongyang_club_backend.domain.test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestDto {

    @NotBlank(message = "입력해랑")
    private String title;

    @NotBlank(message = "콘텐드")
    private String content;

    private Boolean check;

}
