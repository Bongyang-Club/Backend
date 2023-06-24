package com.project.bongyang_club_backend.domain.member.repository;

import com.project.bongyang_club_backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findBySinumberAndName(String sinumber, String name);

}
