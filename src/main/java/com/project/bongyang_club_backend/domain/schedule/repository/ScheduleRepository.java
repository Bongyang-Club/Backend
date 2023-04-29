package com.project.bongyang_club_backend.domain.schedule.repository;

import com.project.bongyang_club_backend.domain.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
