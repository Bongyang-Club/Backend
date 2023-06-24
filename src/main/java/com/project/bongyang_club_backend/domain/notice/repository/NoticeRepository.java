package com.project.bongyang_club_backend.domain.notice.repository;

import com.project.bongyang_club_backend.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
