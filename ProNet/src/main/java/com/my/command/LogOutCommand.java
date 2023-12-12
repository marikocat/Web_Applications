package com.my.command;

import com.my.logic.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("currentUser");
        }
        return "index.jsp";
    }
}
