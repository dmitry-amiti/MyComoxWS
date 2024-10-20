package ru.penza.security.config;

import org.json.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();

        if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            jsonObject.put("status", "401");
        } else {
            jsonObject.put("status", "404");
        }

        System.out.println(jsonObject.toString());

        response.getWriter().append(jsonObject.toString());
    }
}
