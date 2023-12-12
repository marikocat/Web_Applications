package com.my.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowLoginFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        String message = req.getParameter("message");
        System.out.println("message ==> " + message);
        if (message != null) {
            req.setAttribute("message", "Access denied. You should log in first.");
        }
        return "login_page.jsp";
    }
}
