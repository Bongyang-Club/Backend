package com.project.bongyang_club_backend.domain.member.repository;

import com.project.bongyang_club_backend.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {}
