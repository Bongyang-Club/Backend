package com.project.bongyang_club_backend.domain.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    STUDENT("ROLE_STUDENT", "학생"),

    CLUB_LEADER("ROLE_CLUB_LEADER", "동아리장"),

    TEACHER("ROLE_TEACHER", "선생님");

    private final String key;

    private final String name;

}
