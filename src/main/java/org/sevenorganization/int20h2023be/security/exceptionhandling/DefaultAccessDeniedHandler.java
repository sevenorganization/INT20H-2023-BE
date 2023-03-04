package org.sevenorganization.int20h2023be.security.exceptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sevenorganization.int20h2023be.model.dto.HttpExceptionResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        OutputStream outputStream = response.getOutputStream();
        new ObjectMapper().writeValue(outputStream, new HttpExceptionResponseDto(
                HttpStatus.FORBIDDEN, accessDeniedException.getMessage()
        ));
        outputStream.flush();
    }
}