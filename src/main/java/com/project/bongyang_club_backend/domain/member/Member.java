package com.project.bongyang_club_backend.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bongyang_club_backend.domain.memberJoin.MemberJoin;
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
    private String sinumber;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String s_grade;

    @Column(nullable = false)
    private String s_class;

    @Column(nullable = false)
    private String s_number;

    @OneToMany(mappedBy = "member")
    private List<MemberJoin> schoolClubs;

    @Column(nullable = false)
    private String role;

}
