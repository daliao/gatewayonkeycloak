package com.lxg.keycloak.callme.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/res2")
public class Res2Controller {

    @GetMapping("/ping")
    public String ping(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userName = (String) ((JwtAuthenticationToken) authentication).getTokenAttributes().getOrDefault("preferred_username", "");

        Instant instant = Instant.now(); // 获取 UTC 时间
        ZoneId zoneId = ZoneId.systemDefault(); // 获取系统默认时区
        ZonedDateTime zonedDateTime = instant.atZone(zoneId); // 将 UTC 时间转换为本地时间

        String path = request.getRequestURI();
        return String.format("URL: %s === UserName: %s === Time: %s", path, userName, zonedDateTime);
    }
}
