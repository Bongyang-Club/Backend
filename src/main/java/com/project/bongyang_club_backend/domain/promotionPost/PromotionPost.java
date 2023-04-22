package com.project.bongyang_club_backend.domain.promotionPost;

import com.project.bongyang_club_backend.domain.postForm.PostForm;
import com.project.bongyang_club_backend.domain.poster.Poster;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 홍보 포스터
    @OneToOne
    private Poster poster;

    @OneToOne
    private PostForm postForm;

}
