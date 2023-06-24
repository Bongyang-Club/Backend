package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostNoticeRequest {

    private Long clubId;

    @NotBlank(message = "공지글을 작성해주세요.")
    private String notice;

}
