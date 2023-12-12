package com.my.command.account;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Account;
import com.my.domain.Status;
import com.my.domain.TariffPlan;
import com.my.logic.AccountManager;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import com.my.util.UtilFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefillUserAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        long userId = Long.parseLong(req.getParameter("userId"));
        Account account;
        TariffPlan tariffPlan;
        try {
            account = AccountManager.getInstance().findAccountByUserId(userId);
            tariffPlan = ServiceManager.getInstance().findTariffPlanByUserId(account.getUserId());
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        double sum = Double.parseDouble(req.getParameter("sum"));
        double newBalance = account.getBalance() + sum;

        try {
            if (tariffPlan != null) {
                if (tariffPlan.getStatus().equals(Status.BLOCKED)) {
                    if (newBalance >= tariffPlan.getCost()) {
                        newBalance -= tariffPlan.getCost();
                        account.setBalance(newBalance);
                        tariffPlan.setStatus(Status.UNBLOCKED);
                        tariffPlan.setStartDate(UtilFunctions.getStartDate());
                        tariffPlan.setEndDate(UtilFunctions.getEndDate());
                        AccountManager.getInstance().updateAccountAndStatus(account, tariffPlan);
                    } else {
                        account.setBalance(newBalance);
                        AccountManager.getInstance().updateAccount(account);
                    }
                } else {
                    account.setBalance(newBalance);
                    AccountManager.getInstance().updateAccount(account);
                }
            } else {
                account.setBalance(newBalance);
                AccountManager.getInstance().updateAccount(account);
            }
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        return "controller?command=showUserAccount&userId=" + account.getUserId();
    }
}
