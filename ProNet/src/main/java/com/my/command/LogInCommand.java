package com.my.command;

import com.my.domain.Role;
import com.my.domain.User;
import com.my.domain.UserDetails;
import com.my.logic.LogicException;
import com.my.logic.UserManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInCommand implements Command {
    private static final Logger log = LogManager.getLogger(LogInCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        String login = req.getParameter("login");
        System.out.println("login ==> " + login);
        String password = req.getParameter("password");
        System.out.println("password ==> " + password);

        User user;
        try {
            user = UserManager.getInstance().findUser(login);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        if (user == null) {
            log.info("User with such login doesn't exists in db");
            throw new AppException("User with such login doesn't exists!");
        }

        if (!user.getPassword().equals(password)) {
            log.info("Password doesn't match for user with this login");
            throw new AppException("Password doesn't match!");
        }

        UserDetails userDetails = null;
        if (user.getRole().equals(Role.SUBSCRIBER)) {
            try {
                userDetails = UserManager.getInstance().findUserDetails(user.getId());
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
        }
        user.setUserDetails(userDetails);
        req.getSession(true).setAttribute("currentUser", user);
        if (user.getRole().equals(Role.ADMINISTRATOR)) {
            return "controller?command=showAdminPage";
        }
        return "controller?command=showSubscriberPage";
    }
}
