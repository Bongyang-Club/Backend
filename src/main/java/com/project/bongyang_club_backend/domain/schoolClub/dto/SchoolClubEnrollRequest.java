package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SchoolClubEnrollRequest {

    // 전공, 자율
    @Positive(message = "자율, 전공을 선택해주세요.")
    private Integer m_type;

    @NotBlank(message = "동아리명을 확인해주세요.")
    private String clubName;

    // si_number
    @NotBlank(message = "동아리장을 확인해주세요.")
    private String officer;

    // 이름
    @NotBlank(message = "담당 선생님을 확인해주세요.")
    private String teacher;

    @NotBlank(message = "동아리 소개를 확인해주세요.")
    private String clubIntroduce;

}
