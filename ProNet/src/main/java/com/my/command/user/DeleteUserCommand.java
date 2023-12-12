package com.my.command.user;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.logic.LogicException;
import com.my.logic.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserCommand implements Command {
    private static final Logger log = LogManager.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        double balance = Double.parseDouble(req.getParameter("balance"));
        System.out.println("balance ==> " + balance);
        if (balance > 0) {
            log.info("Cannot delete user with non-zero balance");
            throw new AppException("Cannot delete user with non-zero balance!");
        }
        long userId = Long.parseLong(req.getParameter("userId"));
        try {
            UserManager.getInstance().deleteUserById(userId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        return "controller?command=listUsers&page=1&pageSize=3";
    }
}
