package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SchoolClubApplicationCheckRequest {

    @NotNull(message = "동아리 아이디를 확인해주세요.")
    private Long schoolClubId;

    @NotEmpty(message = "동아리 신청 내역을 확인해주세요.")
    private List<Long> memberJoinIds;

}
