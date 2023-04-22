package com.project.bongyang_club_backend.domain.postForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostFormRepository extends JpaRepository<PostForm, Long> {
}
