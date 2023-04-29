package com.project.bongyang_club_backend.domain.schoolClub.dto;

import lombok.Data;

@Data
public class SchoolClubApplicationRequest {

    // si_number
    private String memberId;

    // clubId
    private Long schoolClubId;

}
