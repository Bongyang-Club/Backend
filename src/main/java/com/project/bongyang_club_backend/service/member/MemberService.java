package com.project.bongyang_club_backend.service.member;

import com.project.bongyang_club_backend.domain.member.Member;
import com.project.bongyang_club_backend.dto.SignRequest;
import com.project.bongyang_club_backend.dto.SignResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    ResponseEntity<SignResponse> login(SignRequest request);

    Boolean schoolClubDuplicateCheck(Member member, int type);

}
