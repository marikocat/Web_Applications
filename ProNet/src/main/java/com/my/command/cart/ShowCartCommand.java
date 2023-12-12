package com.my.command.cart;

import com.my.command.AppException;
import com.my.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        return "cart_page.jsp";
    }
}
