package com.project.bongyang_club_backend.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicResponse {

    private Integer code;

    private HttpStatus httpStatus;

    private String message;

    private Integer count;

    private Object result;

    public BasicResponse error(String message) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .count(null)
                .result(null)
                .build();
    }

    public BasicResponse success(String message, Integer count, Object result) {
        return BasicResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message(message)
                .count(count)
                .result(result)
                .build();
    }

}
