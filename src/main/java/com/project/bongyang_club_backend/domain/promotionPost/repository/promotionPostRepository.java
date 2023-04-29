package com.project.bongyang_club_backend.domain.promotionPost.repository;

import com.project.bongyang_club_backend.domain.promotionPost.domain.PromotionPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface promotionPostRepository extends JpaRepository<PromotionPost, Long> {
}
