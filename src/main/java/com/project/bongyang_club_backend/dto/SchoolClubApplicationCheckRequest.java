package com.project.bongyang_club_backend.dto;

import lombok.Data;

@Data
public class SchoolClubApplicationCheckRequest {

    private String memberId;

    private Long schoolClubId;

    private Long memberJoinId;

}
