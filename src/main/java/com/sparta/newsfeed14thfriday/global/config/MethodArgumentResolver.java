package com.sparta.newsfeed14thfriday.global.config;

import com.sparta.newsfeed14thfriday.global.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isTokenMemberEmail = parameter.getParameterAnnotation(TokenUserEmail.class) != null;

        boolean isString = String.class.equals(parameter.getParameterType());

        return isTokenMemberEmail&&isString;
    }

    @Override
    public Object resolveArgument(
            @Nullable MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        authorization = jwtUtil.substringToken(authorization);
        if (authorization == null) {
            return null;
        }
        Claims claims = jwtUtil.extractClaims(authorization);
        return claims.get("email",String.class);
    }
}