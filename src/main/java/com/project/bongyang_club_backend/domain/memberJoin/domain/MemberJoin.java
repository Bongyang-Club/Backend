package com.project.bongyang_club_backend.domain.memberJoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.schoolClub.dto.SchoolClubApplicationResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @JsonIgnore
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "school_club_id")
    private SchoolClub schoolClub;

    // 동아리원: member // 동아리장 leader // 선생님 teacher
    private String role;

    // 1: 보류 // 2: 승인 // 3: 거절
    @Column(nullable = false)
    private Integer status;

    @Column(nullable = false)
    private LocalDateTime applicationAt;

    public SchoolClubApplicationResponse toResponse() {
        return SchoolClubApplicationResponse.builder()
                .memberJoinId(id)
                .name(member.getName())
                .studentId(member.getS_number().length() == 1 ? member.getS_grade() + member.getS_class() + "0" + member.getS_number() : member.getS_grade() + member.getS_class()  + member.getS_number())
                .applicationAt(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(applicationAt))
                .build();
    }

}
