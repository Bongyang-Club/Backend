package com.project.bongyang_club_backend.domain.schoolClub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClubApplicationDto {

    private Long memberJoinId;

    private String name;

    private String studentId;

    private String applicationAt;

}
