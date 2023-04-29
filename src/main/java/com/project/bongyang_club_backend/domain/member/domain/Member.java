package com.project.bongyang_club_backend.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Member  {

    // 아이디
    @Id
    private String si_number;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String s_grade;

    private String s_class;

    private String s_number;

    @OneToMany(mappedBy = "member")
    private List<MemberJoin> schoolClubs;

    @Column(nullable = false)
    private String role;

}
