package com.project.bongyang_club_backend.domain.promotionPost.repository;

import com.project.bongyang_club_backend.domain.promotionPost.domain.PromotionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionPostRepository extends JpaRepository<PromotionPost, Long> {

    List<PromotionPost> findAllByStatus(Boolean status);

}
