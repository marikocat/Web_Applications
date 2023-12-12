package com.my.command.plan;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Status;
import com.my.domain.TariffPlan;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import com.my.util.UtilFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

public class ChangeTariffPlanStatusCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        long planId = Long.parseLong(req.getParameter("planId"));
        long userId = Long.parseLong(req.getParameter("userId"));
        Date startDate = UtilFunctions.getStartDate();
        Date endDate = UtilFunctions.getEndDate();
        TariffPlan tariffPlan = new TariffPlan();
        tariffPlan.setId(planId);
        tariffPlan.setUserId(userId);
        tariffPlan.setStartDate(startDate);
        if (Status.BLOCKED.name().equals(req.getParameter("status"))) {
            tariffPlan.setEndDate(startDate);
            tariffPlan.setStatus(Status.BLOCKED);
        } else {
            tariffPlan.setEndDate(endDate);
            tariffPlan.setStatus(Status.UNBLOCKED);
        }

        try {
            ServiceManager.getInstance().updateTariffPlanStatus(tariffPlan);
        } catch (LogicException e) {
            throw new AppException(e);
        }
        return "controller?command=showTariffPlan&userId=" + userId;
    }
}
