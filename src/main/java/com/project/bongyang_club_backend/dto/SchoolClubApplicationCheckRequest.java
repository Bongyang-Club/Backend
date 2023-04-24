package com.project.bongyang_club_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class SchoolClubApplicationCheckRequest {

    private Long schoolClubId;

    private List<Long> memberJoinIds;

}
