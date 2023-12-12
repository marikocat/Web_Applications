package com.my.command.registration;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Request;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FillRegisterFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        long requestId = Long.parseLong(req.getParameter("requestId"));
        Request userRequest;
        try {
            userRequest = ServiceManager.getInstance().findRequestById(requestId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        req.setAttribute("userRequest", userRequest);
        return "register_page.jsp";
    }
}
