package com.project.bongyang_club_backend.domain.memberJoin;

import com.project.bongyang_club_backend.domain.member.Member;
import com.project.bongyang_club_backend.domain.schoolClub.SchoolClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberJoinRepository extends JpaRepository<MemberJoin, Long> {

    Optional<MemberJoin> findByMemberAndSchoolClub(Member member, SchoolClub schoolClub);

    List<MemberJoin> findAllBySchoolClub(SchoolClub schoolClub);

}
