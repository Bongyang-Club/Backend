package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DeleteClubMemberRequest {

    @NotNull(message = "동아리 아이디를 확인해주세요.")
    private Long clubId;

    private List<String> memberIds;

}
