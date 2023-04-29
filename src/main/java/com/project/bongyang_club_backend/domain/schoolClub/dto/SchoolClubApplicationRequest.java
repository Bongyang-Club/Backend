package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SchoolClubApplicationRequest {

    // clubId
    @Positive(message = "동아리 아이디를 확인해주세요.")
    private Long schoolClubId;

}
