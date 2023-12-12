package com.my.command.plan;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTariffPlanCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        long userId = Long.parseLong(req.getParameter("userId"));
        long planId = Long.parseLong(req.getParameter("planId"));
        try {
            ServiceManager.getInstance().deleteTariffPlanById(planId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        return "controller?command=showTariffPlan&userId=" + userId;
    }
}
