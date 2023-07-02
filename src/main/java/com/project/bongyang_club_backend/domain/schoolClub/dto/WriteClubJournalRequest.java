package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class WriteClubJournalRequest {

    @NotNull(message = "동아리 아이디를 확인해주세요.")
    private Long clubId;

    private String activityDate;

    @NotBlank(message = "활동날자를 작성해주세요.")
    private String activityTime;

    @NotBlank(message = "장소를 작성해주세요.")
    private String place;

    @NotNull(message = "참가인원을 작성해주세요.")
    private Integer participantCount;

    private String absents;

    @NotNull(message = "누계를 작성해주세요.")
    private Integer total;

    @NotBlank(message = "활동내용을 작성해주세요.")
    private String content;

    private String leaderRatting;

    private String studentRatting;

    private String dueDate;

    @NotBlank(message = "활동계획을 작성해주세요.")
    private String duePlan;

    @NotBlank(message = "활동목표를 작성해주세요.")
    private String dueGoal;

    private String etc;

}
