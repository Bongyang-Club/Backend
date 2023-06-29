package com.project.bongyang_club_backend.domain.schoolClub.service;

import com.project.bongyang_club_backend.domain.member.enums.Role;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.member.repository.MemberRepository;
import com.project.bongyang_club_backend.domain.notice.domain.Notice;
import com.project.bongyang_club_backend.domain.notice.repository.NoticeRepository;
import com.project.bongyang_club_backend.domain.postForm.domain.PostForm;
import com.project.bongyang_club_backend.domain.postForm.repository.PostFormRepository;
import com.project.bongyang_club_backend.domain.poster.service.PosterService;
import com.project.bongyang_club_backend.domain.promotionPost.domain.PromotionPost;
import com.project.bongyang_club_backend.domain.promotionPost.repository.PromotionPostRepository;
import com.project.bongyang_club_backend.domain.schoolClub.domain.*;
import com.project.bongyang_club_backend.domain.schoolClub.dto.*;
import com.project.bongyang_club_backend.domain.schoolClub.repository.SchoolClubRepository;
import com.project.bongyang_club_backend.domain.memberJoin.domain.MemberJoin;
import com.project.bongyang_club_backend.domain.memberJoin.repository.MemberJoinRepository;
import com.project.bongyang_club_backend.domain.target.domain.Target;
import com.project.bongyang_club_backend.domain.target.service.TargetSerivce;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private final PostFormRepository postFormRepository;

    private final NoticeRepository noticeRepository;

    private final PromotionPostRepository promotionPostRepository;

    private final MemberService memberService;

    private final TargetSerivce targetSerivce;

    private final PosterService posterService;

    private final JWTProvider jwtProvider;

    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<BasicResponse> deleteNotice(DeleteNoticeRequest request) {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("로그인 후 이용 가능합니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(request.getClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        SchoolClub schoolClub = schoolClubOpt.get();

        if (!schoolClub.getLeader().equals(member)) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("해당 동아리장이 아닙니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Notice> noticeOpt = noticeRepository.findById(request.getNoticeId());

        if (noticeOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("공지를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Notice notice = noticeOpt.get();
        schoolClub.getNotices().remove(notice);

        noticeRepository.deleteById(request.getNoticeId());

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("공지를 정상적으로 삭제했습니다.")
                .count(1)
                .result(null)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> getSchoolClubNotices(Long clubId) {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("로그인 후 이용 가능합니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(clubId);

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClub(memberOpt.get(), schoolClubOpt.get());

        if (memberJoinOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("해당 동아리에 가입 되어있지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        List<Notice> notices = schoolClubOpt.get().getNotices();
        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("공지를 정상적으로 찾았습니다.")
                .count(notices.size())
                .result(notices)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> getMySchoolClub() {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        List<MySchoolClubDto> mySchoolClub = new ArrayList<>();


        for (MemberJoin memberJoin : memberOpt.get().getSchoolClubs()) {
            if (memberJoin.getStatus() == 2) {
                mySchoolClub.add(MySchoolClubDto.builder()
                        .clubId(memberJoin.getSchoolClub().getId())
                        .image(memberJoin.getSchoolClub().getImage())
                        .build());
            }
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("가입한 동아리를 정상적으로 찾았습니다.")
                .count(memberOpt.get().getSchoolClubs().size())
                .result(mySchoolClub)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }


    @Override
    public ResponseEntity<BasicResponse> getSchoolClubMembers(SchoolClubId clubId, boolean check) {
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(clubId.getId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        SchoolClub schoolClub = schoolClubOpt.get();
        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClub(member, schoolClub);

        if (memberJoinOpt.isEmpty() || !memberJoinOpt.get().getRole().equals(Role.CLUB_LEADER.getKey())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("잘못된 요청입니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        List<MemberJoin> memberJoins = schoolClub.getMembers();
        List<SchoolClubMemberDto> memberDtos = new ArrayList<>();

        if (check) {
            for (MemberJoin memberJoin : memberJoins) {
                if (memberJoin.getStatus() == 2) {
                    Member clubMember = memberJoin.getMember();

                    memberDtos.add(
                            SchoolClubMemberDto.builder()
                                    .name(clubMember.getName())
                                    .studentId(memberService.getStudentId(member))
                                    .joinAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(memberJoin.getJoinAt()))
                                    .build()
                    );
                }
            }
        } else {
            for (MemberJoin memberJoin : memberJoins) {
                if (memberJoin.getStatus() == 1) {
                    Member clubMember = memberJoin.getMember();

                    memberDtos.add(
                            SchoolClubMemberDto.builder()
                                    .name(clubMember.getName())
                                    .studentId(memberService.getStudentId(member))
                                    .joinAt(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(memberJoin.getJoinAt()))
                                    .build()
                    );
                }
            }
        }

        String findValue = clubId.getFindValue();

        if (findValue != null) {
            List<SchoolClubMemberDto> memberDtofs = new ArrayList<>();

            for (SchoolClubMemberDto memberDto : memberDtos) {
                if (memberDto.getName().contains(findValue) || memberDto.getStudentId().contains(findValue)) {
                    memberDtofs.add(memberDto);
                }
            }

            BasicResponse basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message(findValue + "가 포함된 동아리원을 정상적으로 찾았습니다.")
                    .count(memberDtofs.size())
                    .result(memberDtofs)
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("동아리원을 정상적으로 찾았습니다.")
                .count(memberJoins.size())
                .result(memberDtos)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubEnroll(SchoolClubEnrollRequest request) {
        Integer type = request.getM_type();

        if (type != 1 && type != 2) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 분류를 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> officerOpt = memberRepository.findById(request.getOfficer());

        if (officerOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 장을 확인해주세요.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> teacherOpt = memberRepository.findById(request.getTeacher());

        if (teacherOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("담당 선생님을 확인해주세요.");

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
                .name(request.getClubName())
                .leader(officer)
                .teacher(teacher)
                .introduce(request.getClubIntroduce())
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
    public ResponseEntity<BasicResponse> schoolClubApplication(SchoolClubApplicationRequest request) {
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(request.getSchoolClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾지 못했습니다.");

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
    public ResponseEntity<BasicResponse> schoolClubApplicationList(SchoolClubApplicationListRequest request) {
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(request.getSchoolClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
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

        List<SchoolClubApplicationResponse> schoolClubApplicationResponses = new ArrayList<>();

        for (MemberJoin memberJoin : memberJoins) {
            if (memberJoin.getRole().equals("ROLE_CLUB_LEADER") || memberJoin.getStatus() != 1) {
                continue;
            }

            schoolClubApplicationResponses.add(memberJoin.toResponse());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(schoolClub.getName() + "의 동아리 신청내역을 정상적으로 찾았습니다.")
                .count(memberJoins.size())
                .result(schoolClubApplicationResponses)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubPromotionApplication(SchoolClubPromotionApplicationRequest request, MultipartFile poster) throws IOException {
        PostForm postForm = new PostForm();

        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findByName(request.getSchoolClubName());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SchoolClub schoolClub = schoolClubOpt.get();

        if (request.getInterview() && request.getTest()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("면접과 테스트 중 한 개만 선택이 가능합니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        if (request.getGoogleForm()) {
            if (request.getGf_link().isBlank()) {
                BasicResponse basicResponse = new BasicResponse()
                        .error("구글폼 링크를 확인해주세요.");

                return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }

            if (request.getStartDate().toString().isBlank() || request.getEndDate().toString().isBlank()) {
                BasicResponse basicResponse = new BasicResponse()
                        .error("구글폼 기한을 확인해주세요.");

                return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
            }

            postForm.setGf_check(true);
            postForm.setGf_link(request.getGf_link());
            postForm.setGf_start(DateTimeFormatter.ofPattern("yyyy.MM.dd").format(request.getStartDate()));
            postForm.setGf_end(DateTimeFormatter.ofPattern("yyyy.MM.dd").format(request.getEndDate()));
        }

        if (request.getInterview()) {
            postForm.setIv_check(true);
        }

        if (request.getTest()) {
            postForm.setT_check(true);
            postForm.setT_time(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm").format(request.getCheckTime()));
            postForm.setT_place(request.getCheckPlace());

            List<Target> targets = targetSerivce.createTarget(request.getTarget());

            postForm.setR_targets(targets);
            postForm.setR_c_total(targets.size());
            postForm.setR_s_total(targetSerivce.countPeople(request.getTarget()));
        }

        postForm.setP_how(request.getA_method());
        postForm.setP_place(request.getA_place());
        postForm.setP_time(request.getA_time());

        postFormRepository.save(postForm);

        PromotionPost promotionPost = PromotionPost.builder()
                .schoolClub(schoolClub)
                .postForm(postForm)
                .poster(posterService.savePosters(List.of(poster)).get(0))
                .build();

        promotionPostRepository.save(promotionPost);

        schoolClub.setPromotionPost(promotionPost);
        schoolClubRepository.save(schoolClub);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("홍보 신청이 정상적으로 완료되었습니다.")
                .count(1)
                .result(postForm)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> schoolClubApplicationCheck(SchoolClubApplicationCheckRequest request, boolean approve) {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾지 못했습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(request.getSchoolClubId());

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

        if (!memberJoin.getRole().equals(Role.CLUB_LEADER.getKey())) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리 장의 권한을 가지고 있지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }
        
        List<Long> memberJoinIds = request.getMemberJoinIds();
        
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

    @Override
    public ResponseEntity<BasicResponse>
    changeClubLeader(ChangeLeaderRequest request) {
        Optional<Member> clubLeaderOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (clubLeaderOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리장을 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member clubLeader = clubLeaderOpt.get();
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(request.getClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SchoolClub schoolClub = schoolClubOpt.get();
        Optional<Member> memberOpt = memberRepository.findBySinumberAndName(request.getStudentId(), request.getName());

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("새 동아리장을 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Member member = memberOpt.get();

        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClubAndRole(member, schoolClub, Role.STUDENT.getKey());

        if (memberJoinOpt.isEmpty() || memberJoinOpt.get().getStatus() != 2) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("1 해당 동아리에 가입되어 있지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<MemberJoin> memberJoinSOpt = memberJoinRepository.findByMemberAndSchoolClub(clubLeader, schoolClub);

        if (memberJoinSOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("2 해당 동아리에 가입되어 있지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        boolean checkMj = false;

        MemberJoin memberJoin = memberJoinOpt.get();
        MemberJoin memberJoinS = memberJoinSOpt.get();
        member.setRole(Role.CLUB_LEADER.getKey());
        memberJoin.setRole(Role.CLUB_LEADER.getKey());
        memberJoinS.setRole(Role.STUDENT.getKey());

        memberJoinRepository.saveAll(List.of(memberJoin, memberJoinS));

        for (MemberJoin mj : memberJoinRepository.findAllByMember(clubLeader)) {
            if (mj.getRole().equals(Role.CLUB_LEADER.getKey())) checkMj = true;
        }

        if (!checkMj) {
            clubLeader.setRole(Role.STUDENT.getKey());
        }

        schoolClub.setLeader(member);

        memberRepository.saveAll(List.of(member, clubLeader));
        schoolClubRepository.save(schoolClub);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("정상적으로 변경되었습니다.")
                .count(1)
                .result(memberJoin)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    @Transactional
    public ResponseEntity<BasicResponse> postNotice(PostNoticeRequest request) {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("작성자를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(request.getClubId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SchoolClub schoolClub = schoolClubOpt.get();

        Notice notice = Notice.builder()
                .content(request.getNotice())
                .writer(memberOpt.get().getName())
                .createdAt(LocalDate.now())
                .build();

        noticeRepository.save(notice);

        schoolClub.getNotices().add(notice);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("정상적으로 변경되었습니다.")
                .count(1)
                .result(notice)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> clubWithDraw(SchoolClubId clubId) {
        Optional<Member> memberOpt = jwtProvider.getMemberByToken(httpServletRequest);

        if (memberOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("사용자를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }
        
        Member member = memberOpt.get();
        Optional<SchoolClub> schoolClubOpt = schoolClubRepository.findById(clubId.getId());

        if (schoolClubOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        SchoolClub schoolClub = schoolClubOpt.get();
        Optional<MemberJoin> memberJoinOpt = memberJoinRepository.findByMemberAndSchoolClub(member, schoolClub);

        if (memberJoinOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("동아리원이 아닙니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        memberJoinRepository.delete(memberJoinOpt.get());

//        MemberJoin memberJoin = memberJoinOpt.get();
//        memberJoin.setStatus(4);
//
//        memberJoinRepository.save(memberJoin);

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("탈퇴가 정상적으로 처리되어 되었습니다.")
                .count(1)
                .result(memberJoin)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }
}
