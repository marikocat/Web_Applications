package com.my.command.registration;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        long requestId = Long.parseLong(req.getParameter("requestId"));
        try {
            ServiceManager.getInstance().deleteRequest(requestId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        return "controller?command=listRequests";
    }
}
