package com.my.command.plan;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.command.LogInCommand;
import com.my.domain.*;
import com.my.logic.AccountManager;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import com.my.util.UtilFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class CreateTariffPlanCommand implements Command {
    private static final Logger log = LogManager.getLogger(LogInCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");

        long userId = Long.parseLong(req.getParameter("userId"));
        double planCost;
        List<Tariff> tariffs;
        if (currentUser.getRole().equals(Role.ADMINISTRATOR)) {
            tariffs = new ArrayList<>();
            try {
                List<Service> services = ServiceManager.getInstance().findAllServices();
                for (Service s : services) {
                    System.out.println(s.getName() + " ==> " + req.getParameter(s.getName()) + "!");
                    if (req.getParameter(s.getName()) != null) {
                        int tariffId = Integer.parseInt(req.getParameter(s.getName()));
                        tariffs.add(ServiceManager.getInstance().findTariffById(tariffId));
                    }
                }
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
            if (tariffs.size() == 0) {
                log.info("No tariff was chosen to change tariff plan");
                throw new AppException("You should choose at least one tariff to change tariff plan!");
            }
            planCost = UtilFunctions.findTotalCost(tariffs);
        } else {
            tariffs = (List<Tariff>) session.getAttribute("cart");
            planCost = (Double) session.getAttribute("totalCost");
            session.removeAttribute("cart");
            session.removeAttribute("totalCost");
        }

        TariffPlan tariffPlan = new TariffPlan();
        tariffPlan.setCost(planCost);
        tariffPlan.setStartDate(UtilFunctions.getStartDate());
        tariffPlan.setEndDate(UtilFunctions.getStartDate());
        tariffPlan.setUserId(userId);
        tariffPlan.setStatus(Status.BLOCKED);

        try {
            TariffPlan existingTariffPlan = ServiceManager.getInstance().findTariffPlanByUserId(userId);
            if (existingTariffPlan == null) {
                long planId = ServiceManager.getInstance().createTariffPlanForUser(tariffPlan, tariffs);
                tariffPlan.setId(planId);
            } else {
                tariffPlan.setId(existingTariffPlan.getId());
                ServiceManager.getInstance().updateTariffPlanForUser(tariffPlan, tariffs);
            }
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        Account account;
        try {
            account = AccountManager.getInstance().findAccountByUserId(userId);
            if (account.getBalance() >= planCost) {
                double newBalance = account.getBalance() - planCost;
                account.setBalance(newBalance);
                tariffPlan.setStatus(Status.UNBLOCKED);
                tariffPlan.setStartDate(UtilFunctions.getStartDate());
                tariffPlan.setEndDate(UtilFunctions.getEndDate());
                AccountManager.getInstance().updateAccountAndStatus(account, tariffPlan);
            }
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        return "controller?command=showTariffPlan&userId=" + userId;
    }
}
