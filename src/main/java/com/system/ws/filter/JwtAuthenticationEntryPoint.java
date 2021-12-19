package com.system.ws.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.ws.constant.SecurityConstant;
import com.system.ws.domain.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        HttpResponse customHttpResponse = new HttpResponse(HttpStatus.FORBIDDEN.value(),
                                                            HttpStatus.FORBIDDEN,
                                                            HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(),
                                                            SecurityConstant.FORBIDDEN_MESSAGE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper =new ObjectMapper();
        objectMapper.writeValue(outputStream, customHttpResponse);
        outputStream.flush();
    }
}
