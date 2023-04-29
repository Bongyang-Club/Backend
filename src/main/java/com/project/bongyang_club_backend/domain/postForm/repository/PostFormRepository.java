package com.project.bongyang_club_backend.domain.postForm.repository;

import com.project.bongyang_club_backend.domain.postForm.domain.PostForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFormRepository extends JpaRepository<PostForm, Long> {
}
