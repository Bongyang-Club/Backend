package com.project.bongyang_club_backend.domain.target.service;

import com.project.bongyang_club_backend.domain.target.domain.Target;
import com.project.bongyang_club_backend.domain.target.repository.TargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetSerivce {

    private final TargetRepository targetRepository;

    @Override
    public List<Target> createTarget(Map<String, Integer> targets) {
        Iterator competitionsIter = targets.entrySet().iterator();
        List<Target> targetList = new ArrayList<>();
        int i = 0;

        while (competitionsIter.hasNext()) {
            Map.Entry<String, Integer> targetEntry = (Map.Entry) competitionsIter.next();
            String condition = targetEntry.getKey();
            int count = targetEntry.getValue();

            log.info("condition: {}", condition);
            log.info("count: {}", count);
            log.info("i: {}", i);

            Target target = Target.builder()
                    .department(condition)
                    .total(count)
                    .build();

            targetList.add(target);
        }

        targetRepository.saveAll(targetList);

        return targetList;
    }

    @Override
    public Integer countPeople(Map<String, Integer> targets) {
        Iterator competitionsIter = targets.entrySet().iterator();
        int i = 0;

        while (competitionsIter.hasNext()) {
            Map.Entry<String, Integer> targetEntry = (Map.Entry) competitionsIter.next();
            i += targetEntry.getValue();
        }

        return i;
    }

}
