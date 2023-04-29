package com.project.bongyang_club_backend.domain.target.repository;

import com.project.bongyang_club_backend.domain.target.domain.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends JpaRepository<Target, Long> {
}
