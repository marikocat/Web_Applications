package com.my.command;

import com.my.domain.Request;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListRequestsCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        List<Request> requests;
        try {
            requests = ServiceManager.getInstance().findAllRequests();
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        req.setAttribute("requests", requests);
        return "list_requests.jsp";
    }
}
