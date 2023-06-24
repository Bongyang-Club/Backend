package com.project.bongyang_club_backend.domain.member.dto;

import com.project.bongyang_club_backend.domain.member.domain.Member;
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
        this.si_number = member.getSinumber();
        this.name = member.getName();
        this.studentId = member.getSgrade() + member.getSclass();
        if (member.getSnumber().length() == 1) {
            this.studentId += "0";
        }
        this.studentId += member.getSnumber();
        this.role = member.getRole();
    }

}
