package com.project.bongyang_club_backend.domain.promotionPost.service;

import com.project.bongyang_club_backend.global.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PromotionPostService {

    ResponseEntity<BasicResponse> getPromotionPostById(Long id);

    ResponseEntity<BasicResponse> getPromotionPosts();

}
