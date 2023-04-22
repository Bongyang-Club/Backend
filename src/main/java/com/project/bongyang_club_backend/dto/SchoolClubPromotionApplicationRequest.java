package com.project.bongyang_club_backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class SchoolClubPromotionApplicationRequest {

    // to valid
    private Long memberId;

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

    private Map<String, Integer> eoguddl;

}
