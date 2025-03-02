package com.microrobot.user.exception;

import com.microrobot.user.dto.response.ExceptionDto;
import com.microrobot.user.exception.entities.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

    @Test
    void handleUserNotFoundException_ReturnsNotFoundStatus() {
        UserNotFoundException ex = new UserNotFoundException("User not found");

        ResponseEntity<ExceptionDto> response = handler.handleUserNotFoundException(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals("User not found", response.getBody().getMessage());
    }

    @Test
    void handleGeneralException_ReturnsInternalServerError() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<ExceptionDto> response = handler.handleUnknownException(ex);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("internal_error", response.getBody().getMessage());
    }
}

