package com.my.command.plan;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.*;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import com.my.logic.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTariffPlanCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");
        long userId = Long.parseLong(req.getParameter("userId"));

        if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
            List<List<Tariff>> allTariffs = new ArrayList<>();
            User user = null;
            try {
                List<Service> services = ServiceManager.getInstance().findAllServices();
                for (Service s : services) {
                    allTariffs.add(ServiceManager.getInstance().findAllTariffsByServiceId(s.getId(), 3));
                }
                user = UserManager.getInstance().findUserById(userId);
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
            req.setAttribute("allTariffs", allTariffs);
            req.setAttribute("user", user);
        }

        TariffPlan tariffPlan;
        try {
            tariffPlan = ServiceManager.getInstance().findTariffPlanByUserId(userId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        req.setAttribute("tariffPlan", tariffPlan);

        if (tariffPlan != null) {
            List<Tariff> tariffs;
            try {
                tariffs = ServiceManager.getInstance().findAllTariffsByPlanId(tariffPlan.getId());
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
            req.setAttribute("tariffs", tariffs);
        } else {
            req.setAttribute("userId", userId);
        }
        return "tariff_plan.jsp";
    }
}
