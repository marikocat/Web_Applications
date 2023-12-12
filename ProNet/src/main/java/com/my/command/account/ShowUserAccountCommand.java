package com.my.command.account;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Account;
import com.my.logic.AccountManager;
import com.my.logic.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserAccountCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        long userId = Long.parseLong(req.getParameter("userId"));
        Account account;
        try {
            account = AccountManager.getInstance().findAccountByUserId(userId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        req.setAttribute("account", account);
        return "account_page.jsp";
    }
}
