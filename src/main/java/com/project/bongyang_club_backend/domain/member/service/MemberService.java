package com.project.bongyang_club_backend.domain.member.service;

import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.member.dto.SignRequest;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    ResponseEntity<BasicResponse> getMemberByToken();

    ResponseEntity<BasicResponse> login(SignRequest request);

    Boolean schoolClubDuplicateCheck(Member member, int type);

    String getStudentId(Member member);

    ResponseEntity<BasicResponse> checkClubLeader(Long clubId);

    ResponseEntity<BasicResponse> getSchoolClubs(String name);

}
