package ru.penza.security.config;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirect", "/main");
        jsonObject.put("status", "200");
        System.out.println(jsonObject.toString());

        response.getWriter().append(jsonObject.toString());
    }
}
