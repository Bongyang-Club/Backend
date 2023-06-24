package com.project.bongyang_club_backend.domain.test.service;

import com.project.bongyang_club_backend.domain.member.enums.Role;
import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.member.repository.MemberRepository;
import com.project.bongyang_club_backend.domain.memberJoin.domain.MemberJoin;
import com.project.bongyang_club_backend.domain.memberJoin.repository.MemberJoinRepository;
import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import com.project.bongyang_club_backend.domain.schoolClub.repository.SchoolClubRepository;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;

    private final MemberJoinRepository memberJoinRepository;
    
    private final SchoolClubRepository schoolClubRepository;

    public ResponseEntity<BasicResponse> test() {
        try {
            // 사용자
            List<Member> members = new ArrayList<>();
            Member student1 = Member.builder()
                    .sinumber("1201")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("일일일")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("1")
                    .role(Role.STUDENT.getKey())
                    .build();

            Member student2 = Member.builder()
                    .sinumber("1202")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("이이이")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("2")
                    .role(Role.STUDENT.getKey())
                    .build();

            Member student3 = Member.builder()
                    .sinumber("1203")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("삼삼삼")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("3")
                    .role(Role.STUDENT.getKey())
                    .build();

            Member student4 = Member.builder()
                    .sinumber("1204")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("사사사")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("4")
                    .role(Role.STUDENT.getKey())
                    .build();

            Member student5 = Member.builder()
                    .sinumber("1205")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("오오오")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("5")
                    .role(Role.STUDENT.getKey())
                    .build();

            Member clubLeader1 = Member.builder()
                    .sinumber("1206")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("육육육")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("6")
                    .role(Role.CLUB_LEADER.getKey())
                    .build();

            Member clubLeader2 = Member.builder()
                    .sinumber("1207")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("칠칠칠")
                    .sgrade("3")
                    .sclass("2")
                    .snumber("7")
                    .role(Role.CLUB_LEADER.getKey())
                    .build();

            Member teacher1 = Member.builder()
                    .sinumber("선생님1")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("선생님1")
                    .role(Role.TEACHER.getKey())
                    .build();

            Member teacher2 = Member.builder()
                    .sinumber("선생님2")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("선생님2")
                    .role(Role.TEACHER.getKey())
                    .build();

            Member teacher3 = Member.builder()
                    .sinumber("선생님3")
                    .password("$2a$10$0DtPSK0efLSdBKfbvyVDVuUcFo.9V/2MLaoKmlVB4YjfOJ5FwVcCC")
                    .name("선생님3")
                    .role(Role.TEACHER.getKey())
                    .build();

            members.addAll(List.of(student1, student2, student3, student4, student5, teacher1, teacher2, teacher3, clubLeader1, clubLeader2));

            memberRepository.saveAll(members);

            // 동아리
            List<SchoolClub> schoolClubs = new ArrayList<>();
            SchoolClub club1 = SchoolClub.builder()
                    .name("동아리1")
                    .leader(clubLeader1)
                    .teacher(teacher1)
                    .introduce("소개")
                    .m_type(1)
                    .status(1)
                    .build();

            SchoolClub club2 = SchoolClub.builder()
                    .name("동아리2")
                    .leader(clubLeader2)
                    .teacher(teacher2)
                    .introduce("소개")
                    .m_type(1)
                    .status(1)
                    .build();

            SchoolClub club3 = SchoolClub.builder()
                    .name("동아리3")
                    .leader(clubLeader1)
                    .teacher(teacher3)
                    .introduce("소개")
                    .m_type(1)
                    .status(1)
                    .build();

            schoolClubs.addAll(List.of(club1, club2, club3));

            schoolClubRepository.saveAll(schoolClubs);

            // 가입
            List<MemberJoin> memberJoins = new ArrayList<>();
            memberJoins.add(MemberJoin.builder()
                    .member(clubLeader1)
                    .schoolClub(club1)
                    .role(Role.CLUB_LEADER.getKey())
                    .status(2)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(clubLeader2)
                    .schoolClub(club2)
                    .role(Role.CLUB_LEADER.getKey())
                    .status(2)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(clubLeader1)
                    .schoolClub(club3)
                    .role(Role.CLUB_LEADER.getKey())
                    .status(2)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(student1)
                    .schoolClub(club1)
                    .role(Role.STUDENT.getKey())
                    .status(1)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(student2)
                    .schoolClub(club1)
                    .role(Role.STUDENT.getKey())
                    .status(1)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(student3)
                    .schoolClub(club1)
                    .role(Role.STUDENT.getKey())
                    .status(1)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(student4)
                    .schoolClub(club1)
                    .role(Role.STUDENT.getKey())
                    .status(1)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(student5)
                    .schoolClub(club2)
                    .role(Role.STUDENT.getKey())
                    .status(1)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoins.add(MemberJoin.builder()
                    .member(student1)
                    .schoolClub(club3)
                    .role(Role.STUDENT.getKey())
                    .status(1)
                    .joinAt(LocalDateTime.now())
                    .applicationAt(LocalDateTime.now())
                    .build());

            memberJoinRepository.saveAll(memberJoins);

            BasicResponse basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("정상적으로 작동하였습니다.")
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        } catch (Exception exception) {
            BasicResponse basicResponse = BasicResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("오류.")
                    .build();

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }
    }

}
