package com.project.bongyang_club_backend.domain.schoolClub.domain;

import com.project.bongyang_club_backend.domain.clubJournal.domain.ClubJournal;
import com.project.bongyang_club_backend.domain.image.domain.Image;
import com.project.bongyang_club_backend.domain.notice.domain.Notice;
import com.project.bongyang_club_backend.domain.promotionPost.domain.PromotionPost;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.memberJoin.domain.MemberJoin;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 동아리명
    @Column(nullable = false)
    private String name;

    // 동아리장
    @OneToOne
    private Member leader;

    // 담당 선생님
    @OneToOne
    private Member teacher;

    @OneToMany(mappedBy = "schoolClub")
    private List<MemberJoin> members;

    // 소개
    @Column(nullable = false)
    private String introduce;

    // 홍보 포스터
    @OneToOne
    private PromotionPost promotionPost;

    // 동아리 일지
    @OneToMany
    private List<ClubJournal> clubJournals;

    @OneToMany
    private List<Notice> notices;

    @OneToOne
    private Image image;

    // 1: 자율, 2: 전공
    @Column(nullable = false)
    private Integer m_type;

    // 1: 예비 동아리, 2: 정식 동아리 등등
    @Column(nullable = false)
    private Integer status;

}
