package com.my.command.user;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Role;
import com.my.domain.User;
import com.my.domain.UserDetails;
import com.my.logic.LogicException;
import com.my.logic.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        User user = new User();
        user.setId(Long.parseLong(req.getParameter("userId")));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        if (req.getParameter("userRole").equals(Role.ADMINISTRATOR.name())) {
            user.setRole(Role.ADMINISTRATOR);
            try {
                UserManager.getInstance().updateUser(user);
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
        } else {
            user.setRole(Role.SUBSCRIBER);
            UserDetails userDetails = new UserDetails();
            userDetails.setUserId(user.getId());
            userDetails.setFirstName(req.getParameter("firstName"));
            userDetails.setLastName(req.getParameter("lastName"));
            userDetails.setEmail(req.getParameter("email"));
            userDetails.setPhoneNumber(req.getParameter("phoneNumber"));
            userDetails.setAddress(req.getParameter("address"));
            try {
                UserManager.getInstance().updateUserWithDetails(user, userDetails);
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
        }
        String paramPage = req.getParameter("page");
        int page = Integer.parseInt(paramPage);
        return "controller?command=listUsers&page=" + page + "&pageSize=3";
    }
}
