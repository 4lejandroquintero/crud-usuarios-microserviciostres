package com.microrobot.task.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionDto {

    private String message;
    private List<String> errors;

    public ExceptionDto(String message) {
        this.message = message;
    }

    public ExceptionDto(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}