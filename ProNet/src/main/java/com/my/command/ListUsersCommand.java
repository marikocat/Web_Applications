package com.my.command;

import com.my.domain.User;
import com.my.logic.LogicException;
import com.my.logic.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ListUsersCommand implements Command {
    private static final int shift = 1;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        String paramPage = req.getParameter("page");
        String paramPageSize = req.getParameter("pageSize");

        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);

        List<User> users;
        long size;
        try {
            users = UserManager.getInstance().findUsersByPage(pageSize * (page - 1), pageSize);
            size = UserManager.getInstance().getUsersSize();
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        int minPagePossible = page - shift < 1 ? 1 : page - shift;
        System.out.println("size ==> " + size);
        System.out.println("pageSize ==> " + pageSize);
        int pageCount = (int)Math.ceil((double) size / pageSize);
        System.out.println("pageCount ==> " + pageCount);
        int maxPagePossible = page + shift > pageCount ? pageCount : page + shift;

        req.setAttribute("users", users);
        req.setAttribute("pageCount", pageCount);
        req.setAttribute("page", page);
        req.setAttribute("pageSize", pageSize);
        System.out.println("minPossiblePage ==> " + minPagePossible);
        req.setAttribute("minPossiblePage", minPagePossible);
        System.out.println("maxPossiblePage ==> " + maxPagePossible);
        req.setAttribute("maxPossiblePage", maxPagePossible);


        //req.getSession().setAttribute("users", users);
        return "list_users.jsp";
    }
}
