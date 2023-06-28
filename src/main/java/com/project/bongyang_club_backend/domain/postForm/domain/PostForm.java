package com.project.bongyang_club_backend.domain.postForm.domain;

import com.project.bongyang_club_backend.domain.schedule.domain.Schedule;
import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import com.project.bongyang_club_backend.domain.target.domain.Target;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구글 폼
    private Boolean gf_check;
    // 링크
    private String gf_link;
    // 기간(시작)
    private String gf_start;
    // 기간(끝)
    private String gf_end;


    // 면접
    private Boolean iv_check;
    // 참고 내용
//    private String iv_refernce;
//    // 스케쥴
//    @OneToMany
//    private List<Schedule> iv_schedules;


    // 테스트
    private Boolean t_check;
    // 시간
    private String t_time;
    // 장소
    private String t_place;
    // 참고 내용
    private String t_refernce;


    // 모집대상
    // 행 개수(최대: 6)
    private int r_c_total;
    // 대상자
    @OneToMany
    private List<Target> r_targets; //  Recruitment target
    // 총 모집 인원
    private int r_s_total;


    // 활동
    // 방법
    private String p_how;
    // 장소
    private String p_place;
    // 시간 ex) 7교시
    private String p_time;


    // 문의
    // 카톡
    private String i_kakao;
    // 인스타
    private String i_insta;
    // 전화번호
    private String i_phone;

}
