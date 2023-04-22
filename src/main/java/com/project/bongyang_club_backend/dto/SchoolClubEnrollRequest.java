package com.project.bongyang_club_backend.dto;

import lombok.Data;

@Data
public class SchoolClubEnrollRequest {

    // 전공, 자율
    private Integer type;

    private String clubName;

    // si_number
    private String officer;

    // 이름
    private String teacher;

    private String clubIntroduce;

}
