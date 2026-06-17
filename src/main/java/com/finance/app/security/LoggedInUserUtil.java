package com.finance.app.security;

import org.springframework.stereotype.Component;

@Component
public class LoggedInUserUtil {

    private static final ThreadLocal<String>
            usernameHolder =
                    new ThreadLocal<>();

    public void setUsername(
            String username) {

        usernameHolder.set(username);
    }

    public String getUsername() {

        return usernameHolder.get();
    }

    public void clear() {

        usernameHolder.remove();
    }
}