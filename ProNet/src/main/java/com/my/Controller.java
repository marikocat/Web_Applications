package com.my;

import com.my.command.Command;
import com.my.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error_page.jsp";
        String commandName = req.getParameter("command");
        System.out.println("commandName ==> " + commandName);

        Command command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (RuntimeException e) {
            address = "error_page2.jsp";
        } catch (Exception e) {
            req.setAttribute("exception", e);
        }
        System.out.println("address ==> " + address);

        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error_page.jsp";
        String commandName = req.getParameter("command");
        System.out.println("commandName ==> " + commandName);

        Command command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (RuntimeException e) {
            address = "error_page2.jsp";
        } catch (Exception e) {
            req.getSession().setAttribute("exception", e);
        }
        System.out.println("address ==> " + address);

        resp.sendRedirect(address);
    }
}
