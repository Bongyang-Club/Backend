package com.project.bongyang_club_backend.domain.target.service;

import com.project.bongyang_club_backend.domain.target.domain.Target;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TargetSerivce {

    List<Target> createTarget(Map<String, Integer> targets);

    Integer countPeople(Map<String, Integer> targets);

}
