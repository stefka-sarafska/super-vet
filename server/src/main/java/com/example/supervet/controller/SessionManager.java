package com.example.supervet.controller;


import com.example.supervet.exceptions.UnauthorizedAccessException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionManager {

    public SessionManager() {
    }

    public void setSessionAttributes(HttpSession session, String email) {
        session.setAttribute("email", email);
    }

    public void checkIfLoggedIn(HttpSession session, String email) {
        if (session.getAttribute("email") == null && !session.getAttribute("email").equals(email)) {
            throw new UnauthorizedAccessException("You are not logged in!");
        }
    }

    public boolean isLoggedIn(HttpSession session, String email) {
        return session.getAttribute("email") != null && session.getAttribute("email").equals(email);
    }


}
