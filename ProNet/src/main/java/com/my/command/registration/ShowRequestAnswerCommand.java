package com.my.command.registration;

import com.my.command.AppException;
import com.my.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRequestAnswerCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        return "answer_to_request.jsp";
    }
}
