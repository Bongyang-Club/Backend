package com.project.bongyang_club_backend.domain.memberJoin.repository;

import com.project.bongyang_club_backend.domain.member.domain.Member;
import com.project.bongyang_club_backend.domain.memberJoin.domain.MemberJoin;
import com.project.bongyang_club_backend.domain.schoolClub.domain.SchoolClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberJoinRepository extends JpaRepository<MemberJoin, Long> {

    Optional<MemberJoin> findByMemberAndSchoolClub(Member member, SchoolClub schoolClub);

    Optional<MemberJoin> findByMemberAndSchoolClubAndRole(Member member, SchoolClub schoolClub, String role);

    List<MemberJoin> findAllBySchoolClub(SchoolClub schoolClub);

}
