package com.project.bongyang_club_backend.domain.schoolClub.dto;

import com.project.bongyang_club_backend.domain.image.domain.Image;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MySchoolClubDto {

    private Long clubId;

    private Image image;

}
