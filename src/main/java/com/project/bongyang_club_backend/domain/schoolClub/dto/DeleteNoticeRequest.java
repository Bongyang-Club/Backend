package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteNoticeRequest {

    @NotBlank(message = "삭제할 공지 아이디를 적어주세요.")
    private Long noticeId;

    @NotBlank(message = "동아리 아이디를 적어주세요.")
    private Long clubId;

}
