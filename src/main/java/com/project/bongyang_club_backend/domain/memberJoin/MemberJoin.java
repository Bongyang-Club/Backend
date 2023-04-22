package com.project.bongyang_club_backend.domain.memberJoin;

import com.project.bongyang_club_backend.domain.schoolClub.SchoolClub;
import com.project.bongyang_club_backend.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "school_club_id")
    private SchoolClub schoolClub;

    // 동아리원: member // 동아리장 leader // 선생님 teacher
    private String role;

    // 1: 보류 // 2: 승인 // 3: 거절
    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private LocalDateTime applicationAt;

}
