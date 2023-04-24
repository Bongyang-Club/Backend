package com.project.bongyang_club_backend.service.member;

import com.project.bongyang_club_backend.domain.enums.Role;
import com.project.bongyang_club_backend.domain.member.Member;
import com.project.bongyang_club_backend.domain.member.MemberRepository;
import com.project.bongyang_club_backend.domain.memberJoin.MemberJoin;
import com.project.bongyang_club_backend.dto.SignRequest;
import com.project.bongyang_club_backend.dto.SignResponse;
import com.project.bongyang_club_backend.response.BasicResponse;
import com.project.bongyang_club_backend.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTProvider jwtProvider;

    @Override
    public ResponseEntity<BasicResponse> login(SignRequest request) {
        Optional<Member> memberOpt = memberRepository.findById(request.getSi_number());

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("아이디가 일치하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("비밀번호가 일치하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SignResponse signResponse = SignResponse.builder()
                .si_number(member.getSinumber())
                .name(member.getName())
                .role(member.getRole())
                .token(jwtProvider.createToken(member.getSinumber(), member.getRole()))
                .build();

        if (member.getS_number().length() == 1) {
            signResponse.setStudentId(member.getS_grade() + member.getS_class() + "0");
        }

        signResponse.setStudentId(signResponse.getStudentId() + member.getS_number());

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

}