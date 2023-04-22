package com.project.bongyang_club_backend.domain.schedule;

import com.project.bongyang_club_backend.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자들
    @OneToMany
    private List<Member> members;

    // 장소
    @Column(nullable = false)
    private String place;
    
    // 예약시간
    @Column(nullable = false)
    private String time;

}
