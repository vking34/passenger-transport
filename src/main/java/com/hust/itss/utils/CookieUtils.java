package com.hust.itss.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtils {

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, boolean cookieOverHttpsOnly) {

        fetchCookie(request, cookieName).ifPresent(cookie -> {

            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setSecure(cookieOverHttpsOnly);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        });
    }

    public static Optional<Cookie> fetchCookie(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }
}
