package com.project.bongyang_club_backend.domain.schoolClub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolClubMemberDto {

    private String name;

    private String studentId;

    private String joinAt;

}
