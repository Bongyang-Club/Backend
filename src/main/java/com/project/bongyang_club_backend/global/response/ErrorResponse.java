package com.project.bongyang_club_backend.global.response;

import com.project.bongyang_club_backend.domain.error.domain.FieldError;
import com.project.bongyang_club_backend.domain.error.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;

@Getter
public class ErrorResponse {

    private String message;

    private int status;

    private List<FieldError> errors;

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.errors = errors;
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

}
