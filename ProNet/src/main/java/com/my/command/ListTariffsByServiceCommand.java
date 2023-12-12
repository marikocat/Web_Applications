package com.my.command;

import com.my.domain.Service;
import com.my.domain.Tariff;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListTariffsByServiceCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        int serviceId = Integer.parseInt(req.getParameter("serviceId"));
        int sort = Integer.parseInt(req.getParameter("sort"));
        List<Tariff> tariffs;
        try {
            tariffs = ServiceManager.getInstance().findAllTariffsByServiceId(serviceId, sort);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        String[] options = {"Name ascending", "Name descending", "Price"};
        req.setAttribute("tariffs", tariffs);
        req.setAttribute("sort", sort);
        req.setAttribute("options", options);
        req.setAttribute("size", options.length);

        if ("disabled".equals(req.getParameter("header"))) {
            req.setAttribute("disabled", true);
        } else {
            req.setAttribute("disabled", false);
        }

        return "list_tariffs.jsp";
    }
}
