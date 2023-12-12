package com.my.command.registration;

import com.my.command.AppException;
import com.my.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRegisterFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        System.out.println("attr ==> " + req.getAttribute("userRequest"));
        System.out.println("par ==> " + req.getParameter("userRequest"));
        req.removeAttribute("userRequest");
        return "register_page.jsp";
    }
}
