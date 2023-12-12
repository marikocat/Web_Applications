package com.my.command.registration;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Request;
import com.my.domain.Tariff;
import com.my.domain.UserDetails;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CreateRequestCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        HttpSession session = req.getSession(false);
        List<Tariff> tariffs = (List<Tariff>) session.getAttribute("cart");
        session.removeAttribute("cart");

        Request userRequest = new Request();
        userRequest.setId(0);

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(req.getParameter("firstName"));
        userDetails.setLastName(req.getParameter("lastName"));
        userDetails.setEmail(req.getParameter("email"));
        userDetails.setPhoneNumber(req.getParameter("phoneNumber"));
        String address = req.getParameter("street") + " " + req.getParameter("building");
        if (!req.getParameter("apartment").equals("")) {
            address += ", Apt. " + req.getParameter("apartment");
        }
        userDetails.setAddress(address);
        userRequest.setUserDetails(userDetails);

        double totalCost = (double) session.getAttribute("totalCost");
        session.removeAttribute("totalCost");
        userRequest.setPlanCost(totalCost);

        try {
            long requestId = ServiceManager.getInstance().createRequest(userRequest, tariffs);
            userRequest.setId(requestId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        session.setAttribute("userRequest", userRequest);
        return "controller?command=showRequestAnswer";
    }
}
