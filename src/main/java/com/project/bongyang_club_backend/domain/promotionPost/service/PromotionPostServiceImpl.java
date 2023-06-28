package com.project.bongyang_club_backend.domain.promotionPost.service;

import com.project.bongyang_club_backend.domain.promotionPost.domain.PromotionPost;
import com.project.bongyang_club_backend.domain.promotionPost.repository.PromotionPostRepository;
import com.project.bongyang_club_backend.global.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionPostServiceImpl implements PromotionPostService {

    private final PromotionPostRepository promotionPostRepository;

    @Override
    public ResponseEntity<BasicResponse> getPromotionPostById(Long id) {
        Optional<PromotionPost> promotionPostOpt = promotionPostRepository.findById(id);

        if (promotionPostOpt.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("홍보 포스터를 찾을 수 없습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("홍보 포스터를 정상적으로 찾았습니다.")
                .count(1)
                .result(promotionPostOpt.get())
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

    @Override
    public ResponseEntity<BasicResponse> getPromotionPosts() {
        List<PromotionPost> promotionPosts = promotionPostRepository.findAllByStatus(true);

        if (promotionPosts.isEmpty()) {
            BasicResponse basicResponse = new BasicResponse()
                    .error("홍보 포스터가 존재하지 않습니다.");

            return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
        }

        BasicResponse basicResponse = BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("홍보 포스터를 정상적으로 찾았습니다.")
                .count(promotionPosts.size())
                .result(promotionPosts)
                .build();

        return new ResponseEntity<>(basicResponse, basicResponse.getHttpStatus());
    }

}
