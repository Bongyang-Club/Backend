package com.project.bongyang_club_backend.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubListDto {

    private Long clubId;

    private String clubName;

    private String leaderName;

    private String type;

}

