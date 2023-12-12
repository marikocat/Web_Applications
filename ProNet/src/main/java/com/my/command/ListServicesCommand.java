package com.my.command;

import com.my.domain.Service;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListServicesCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        int serviceId = Integer.parseInt(req.getParameter("serviceId"));
        req.setAttribute("serviceId", serviceId);
        int sort = Integer.parseInt(req.getParameter("sort"));
        req.setAttribute("sort", sort);
        List<Service> services;
        try {
            services = ServiceManager.getInstance().findAllServices();
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        req.setAttribute("services", services);
        return "list_services.jsp";
    }
}
