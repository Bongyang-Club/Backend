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
    private String sinumber;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String sgrade;

    private String sclass;

    private String snumber;

    @OneToMany(mappedBy = "member")
    private List<MemberJoin> schoolClubs;

    @Column(nullable = false)
    private String role;

}
