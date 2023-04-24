package com.project.bongyang_club_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClubApplicationDto {

    private String name;

    private String studentId;

    private String applicationAt;

}
