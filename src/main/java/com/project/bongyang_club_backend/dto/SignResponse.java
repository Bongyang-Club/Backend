package com.project.bongyang_club_backend.dto;

import com.project.bongyang_club_backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignResponse {

    private String si_number;

    private String name;

    private String studentId;

    private String role;

    private String token;

    public SignResponse(Member member) {
        this.si_number = member.getSi_number();
        this.name = member.getName();
        this.studentId = member.getS_grade() + member.getS_class();
        if (member.getS_number().length() == 1) {
            this.studentId += "0";
        }
        this.studentId += member.getS_number();
        this.role = member.getRole();
    }
}
