package com.system.ws.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.ws.constant.SecurityConstant;
import com.system.ws.domain.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HttpResponse customHttpResponse = new HttpResponse(HttpStatus.UNAUTHORIZED.value(),
                                                            HttpStatus.UNAUTHORIZED,
                                                            HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(),
                                                            SecurityConstant.ACCESS_DENIED_MESSAGE);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper =new ObjectMapper();
        objectMapper.writeValue(outputStream, customHttpResponse);
        outputStream.flush();
    }
}
