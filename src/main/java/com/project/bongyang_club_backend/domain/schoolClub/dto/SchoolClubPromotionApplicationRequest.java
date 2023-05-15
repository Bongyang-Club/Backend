package com.project.bongyang_club_backend.domain.schoolClub.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class SchoolClubPromotionApplicationRequest {

    @NotBlank(message = "동아리 이름을 확인해주세요.")
    private String schoolClubName;

    // 구글 폼
    private Boolean googleForm;
    
    // 면접
    private Boolean interview;
    
    // 테스트
    private Boolean test;

    // 구글 폼
    private String gf_link;
    private LocalDate startDate;
    private LocalDate endDate;

    // 면접 or 테스트
    // false: 개인 // true: 전체
    private Boolean check;

    // 시간(면접 or 테스트)
    private LocalDateTime checkTime;

    // 장소(면접 or 테스트)
    private String checkPlace;

    // 모집 대상
    private Map<String, Integer> target;
    
    // 활동 방법
    @NotBlank(message = "활동 방법을 확인해주세요.")
    private String a_method;

    // 활동 장소
    @NotBlank(message = "활동 장소를 확인해주세요.")
    private String a_place;

    // 활동 시간
    @NotBlank(message = "활동 시간을 확인해주세요.")
    private String a_time;

    // 가입 문의
    private Map<String, String> a_inquiry;

}
