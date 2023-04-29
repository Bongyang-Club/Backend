package com.project.bongyang_club_backend.domain.schoolClub.service;

import com.project.bongyang_club_backend.domain.member.enums.Role;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.member.repository.MemberRepository;
import com.project.bongyang_club_backend.domain.schoolClub.domain.*;
import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.domain.schoolClub.repository.SchoolClubRepository;
import com.project.bongyang_club_backend.domain.memberJoin.domain.MemberJoin;
import com.project.bongyang_club_backend.domain.memberJoin.repository.MemberJoinRepository;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import com.project.bongyang_club_backend.global.security.JWTProvider;
import com.project.bongyang_club_backend.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SchoolClubServiceImpl implements SchoolClubService {

    private final MemberRepository memberRepository;

    private final SchoolClubRepository schoolClubRepository;

    private final MemberJoinRepository memberJoinRepository;

    private final MemberService memberService;

    private final JWTProvider jwtProvider;

    private final HttpServletRequest request;

    @Override
    public ResponseEntity<BasicResponse> schoolClubEnroll(SchoolClubEnrollRequest schoolClubEnrollRequest) {
        Integer type = schoolClubEnrollRequest.getType();
        String clubName = schoolClubEnrollRequest.getClubName();
        String clubIntroduce = schoolClubEnrollRequest.getClubIntroduce();

        if (type == null || type != 1 && type != 2) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 분류를 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (clubName.equals("")) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 명을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> officerOpt = memberRepository.findById(schoolClubEnrollRequest.getOfficer());

        if (officerOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 장을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> teacherOpt = memberRepository.findById(schoolClubEnrollRequest.getTeacher());

        if (teacherOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("담당 선생님을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (clubIntroduce.equals("")) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 설명을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member officer = officerOpt.get();
        Member teacher = teacherOpt.get();

        if (!officer.getRole().equals(Role.CLUB_LEADER.getKey())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리의 권한을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (!teacher.getRole().equals(Role.TEACHER.getKey())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("담당 선생님의 권한을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (memberService.schoolClubDuplicateCheck(officer, type)) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("이미 " + type + " 동아리에 가입되어 있습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (memberService.schoolClubDuplicateCheck(teacher, type)) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("이미 존재하는 동아리 장은 중복 될 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SchoolClub schoolClub = SchoolClub.builder()
                .name(clubName)
                .leader(officer)
                .teacher(teacher)
                .introduce(clubIntroduce)
                .m_type(type)
                .status(1)
                .build();

        schoolClubRepository.save(schoolClub);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("동아리가 신청이 정상적으로 되었습니다.")
                .count(1)
                .result(schoolClub)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubApplication(SchoolClubApplicationRequest schoolClubApplicationRequest) {
        Optional<Member> memberOpt = memberRepository.findById(schoolClubApplicationRequest.getMemberId());

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(schoolClubApplicationRequest.getSchoolClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        SchoolClub schoolClub = schoolClubOpt.get();
        MemberJoin memberJoin = MemberJoin.builder()
                .member(member)
                .schoolClub(schoolClub)
                .role("member")
                .status(1)
                .applicationAt(LocalDateTime.now())
                .build();

        memberJoinRepository.save(memberJoin);
        member.getSchoolClubs().add(memberJoin);

        BasicResponse basicResponse = BasicResponse.builder()
                .count(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("동아리 신청이 정상적으로 완료 되었습니다.")
                .count(1)
                .result(member)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubApplicationList(SchoolClubApplicationListRequest schoolClubApplicationListRequest) {
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(schoolClubApplicationListRequest.getSchoolClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = jwtProvider.getMemberByToken(request);
        SchoolClub schoolClub = schoolClubOpt.get();

        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClub(member, schoolClub);

        if (memberJoinOpt.isEmpty() || !memberJoinOpt.get().getRole().equals("ROLE_CLUB_LEADER")) {
            BasicResponse basicResponse = new BasicResponse()
                    .error(schoolClub.getName() + "의 동아리장이 아닙니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        List<MemberJoin> memberJoins = memberJoinRepository.findAllBySchoolClub(schoolClub);

        if (memberJoins.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("신청내역이 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        List<SchoolClubApplicationDto> schoolClubApplicationDtos = new ArrayList<>();

        for (MemberJoin memberJoin : memberJoins) {
            if (memberJoin.getRole().equals("ROLE_CLUB_LEADER")) {
                continue;
            }

            schoolClubApplicationDtos.add(memberJoin.toResponse());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(schoolClub.getName() + "의 동아리 신청내역을 정상적으로 찾았습니다.")
                .count(memberJoins.size())
                .result(schoolClubApplicationDtos)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubPromotionApplication(SchoolClubPromotionApplicationRequest schoolClubPromotionApplicationDto) {
        return null;
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubApplicationCheck(SchoolClubApplicationCheckRequest schoolClubApplicationCheckRequest, boolean approve) {
        Member member = jwtProvider.getMemberByToken(request);
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(schoolClubApplicationCheckRequest.getSchoolClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SchoolClub schoolClub = schoolClubOpt.get();
        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClub(member, schoolClub);

        if (memberJoinOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error(member.getName() + "님이 가입한 " + schoolClub.getName() + " 동아리를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        MemberJoin memberJoin = memberJoinOpt.get();

        if (!memberJoin.getRole().equals("leader")) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 장의 권한을 가지고 있지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }
        
        List<Long> memberJoinIds = schoolClubApplicationCheckRequest.getMemberJoinIds();
        
        if (memberJoinIds.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error(approve ? "승인" : "거절" + "할 동아리 신청 내역이 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        for (Long memberJoinId : memberJoinIds) {
            memberJoinOpt = memberJoinRepository.findById(memberJoinId);

            if (memberJoinOpt.isEmpty()) {
                BasicResponse basicResponse = new BasicResponse()
                        .error("동아리 신청 내역을 찾을 수 없습니다.");

                return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }

            memberJoin = memberJoinOpt.get();
            memberJoin.setStatus(approve ? 2 : 3);

            memberJoinRepository.save(memberJoin);
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(approve ? "동아리 신청이 정상적으로 승인되었습니다." : "동아리 신청이 정상적으로 거절되었습니다.")
                .count(1)
                .result(memberJoin)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

}