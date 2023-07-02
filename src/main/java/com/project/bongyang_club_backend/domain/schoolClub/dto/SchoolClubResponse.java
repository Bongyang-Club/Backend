package com.project.bongyang_club_backend.domain.schoolClub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolClubResponse {

    private String clubName;

    private String leaderName;

    private String teacherName;

    private String imageUrl;

}
