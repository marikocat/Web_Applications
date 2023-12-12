package com.my.command;

import com.my.domain.Service;
import com.my.domain.Tariff;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListTariffsByPageCommand implements Command {
    private static final int shift = 1;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        String paramPage = req.getParameter("page");
        int page = Integer.parseInt(paramPage);
        int sort = 3;
        List<Tariff> tariffs;
        List<Service> services;
        int pageCount;
        try {
            tariffs = ServiceManager.getInstance().findAllTariffsByServiceId(page, sort);
            services = ServiceManager.getInstance().findAllServices();
            pageCount = services.size();
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        int minPagePossible = page - shift < 1 ? 1 : page - shift;
        int maxPagePossible = page + shift > pageCount ? pageCount : page + shift;

        req.setAttribute("tariffs", tariffs);
        req.setAttribute("services", services);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("page", page);
        System.out.println("minPossiblePage ==> " + minPagePossible);
        req.setAttribute("minPossiblePage", minPagePossible);
        System.out.println("maxPossiblePage ==> " + maxPagePossible);
        req.setAttribute("maxPossiblePage", maxPagePossible);

        return "list_tariffs_by_page.jsp";
    }
}
