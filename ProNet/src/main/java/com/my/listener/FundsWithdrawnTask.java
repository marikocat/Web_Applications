package com.my.listener;

import com.my.domain.Account;
import com.my.domain.Status;
import com.my.domain.TariffPlan;
import com.my.domain.User;
import com.my.logic.AccountManager;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import com.my.logic.UserManager;
import com.my.util.UtilFunctions;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.util.List;

public class FundsWithdrawnTask extends Task {
    @Override
    public void execute(TaskExecutionContext taskExecutionContext) throws RuntimeException {
        System.out.println("Start to withdraw funds");
        List<User> subscribers;
        try {
            subscribers = UserManager.getInstance().findAllSubscribers();
        } catch (LogicException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        for (User s : subscribers) {
            Account account;
            TariffPlan tariffPlan;
            try {
                account = AccountManager.getInstance().findAccountByUserId(s.getId());
                tariffPlan = ServiceManager.getInstance().findTariffPlanByUserId(s.getId());
                if (tariffPlan != null) {
                    if (account.getBalance() >= tariffPlan.getCost()) {
                        double newBalance = account.getBalance() - tariffPlan.getCost();
                        account.setBalance(newBalance);
                        tariffPlan.setStatus(Status.UNBLOCKED);
                        tariffPlan.setStartDate(UtilFunctions.getStartDate());
                        tariffPlan.setEndDate(UtilFunctions.getEndDate());
                        AccountManager.getInstance().updateAccountAndStatus(account, tariffPlan);
                    } else {
                        tariffPlan.setStatus(Status.BLOCKED);
                        tariffPlan.setStartDate(UtilFunctions.getStartDate());
                        tariffPlan.setEndDate(UtilFunctions.getStartDate());
                        ServiceManager.getInstance().updateTariffPlanStatus(tariffPlan);
                    }
                }
            } catch (LogicException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

        System.out.println("End to withdraw funds");
    }
}
