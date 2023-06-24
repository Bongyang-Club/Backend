package com.project.bongyang_club_backend.domain.member.service;

import com.project.bongyang_club_backend.domain.member.enums.Role;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.member.repository.MemberRepository;
import com.project.bongyang_club_backend.domain.memberJoin.domain.MemberJoin;
import com.project.bongyang_club_backend.domain.member.dto.SignRequest;
import com.project.bongyang_club_backend.domain.member.dto.SignResponse;
import com.project.bongyang_club_backend.domain.memberJoin.repository.MemberJoinRepository;
import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import com.project.bongyang_club_backend.domain.schoolClub.repository.SchoolClubRepository;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import com.project.bongyang_club_backend.global.security.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final SchoolClubRepository schoolClubRepository;

    private final MemberJoinRepository memberJoinRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTProvider jwtProvider;

    private final HttpServletRequest request;

    @Override
    public ResponseEntity<BasicResponse> getMemberByToken() {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(request);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("사용자를 정상적으로 찾았습니다.")
                .count(1)
                .result(memberOpt.get())
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> login(SignRequest request) {
        Optional<Member> memberOpt = memberRepository.findById(request.getSi_number());

        log.info(request.toString());

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = BasicResponse.builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .httpStatus(HttpStatus.OK)
                    .message("사용자를 찾을 수 없습니다.")
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            BasicResponse basicResponse = BasicResponse.builder()
                    .code(HttpStatus.FORBIDDEN.value())
                    .httpStatus(HttpStatus.OK)
                    .message("비밀번호가 일치하지 않습니다.")
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SignResponse signResponse = SignResponse.builder()
                .si_number(member.getSinumber())
                .name(member.getName())
                .role(member.getRole())
                .studentId(getStudentId(member))
                .token(jwtProvider.createToken(member.getSinumber(), member.getRole()))
                .build();

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("로그인인 정상적으로 처리되었습니다.")
                .count(1)
                .result(signResponse)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public Boolean schoolClubDuplicateCheck(Member member, int type) {
        List<MemberJoin> memberJoins = member.getSchoolClubs();

        if (memberJoins.size() > 2) {
            return false;
        }

        if (memberJoins.size() == 1) {
            if (member.getRole().equals(Role.TEACHER.getKey())) {
                return false;
            }

            int m_type = memberJoins.get(0).getSchoolClub().getM_type();

            return type != m_type;
        }

        return true;
    }

    @Override
    public ResponseEntity<BasicResponse> checkClubLeader(Long clubId) {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(request);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(clubId);

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClubAndRole(member, schoolClubOpt, Role.CLUB_LEADER.getKey());

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .count(1)
                .build();

        if (memberJoinOpt.isEmpty()) {
            basicResponse.setMessage("동아리장이 아닙니다.");
            basicResponse.setResult(false);
        } else {
            basicResponse.setMessage("동아리장 입니다.");
            basicResponse.setResult(true);
        }

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public String getStudentId(Member member) {
        String studentId;

        if (member.getSnumber().length() > 1) {
             studentId = member.getSgrade() + member.getSclass() + member.getSnumber();
        } else {
            studentId = member.getSgrade() + member.getSclass() + "0" + member.getSnumber();
        }

        log.info("studentId: {}", studentId);

        return studentId;
    }
}