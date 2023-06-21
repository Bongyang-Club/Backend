package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeLeaderRequest {

    private Long clubId;

    @NotBlank(message = "학번을 입력해주세요.")
    private String studentId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

}
