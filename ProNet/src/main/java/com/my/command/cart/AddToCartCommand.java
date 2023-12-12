package com.my.command.cart;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Tariff;
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

public class AddToCartCommand implements Command {
    private static final Logger log = LogManager.getLogger(AddToCartCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        HttpSession session = req.getSession();
        List<Tariff> cart = (List<Tariff>) session.getAttribute("cart");

        int tariffId = Integer.parseInt(req.getParameter("tariffId"));
        Tariff tariff;
        try {
            tariff = ServiceManager.getInstance().findTariffById(tariffId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        if (cart == null) {
            cart = new ArrayList<>();
            addToCart(session, tariff, cart);
        } else {
            int index = isInCart(tariff, cart);
            if (index != -1) {
                log.info("Cannot add a service to a cart because it is already in the cart");
                throw new AppException("Service already in the cart!");
            }
            addToCart(session, tariff, cart);
        }

        return "controller?command=showCart";
    }

    // helper methods
    private void addToCart(HttpSession session, Tariff tariff, List<Tariff> cart) {
        cart.add(tariff);
        session.setAttribute("cart", cart);
        double totalCost = UtilFunctions.findTotalCost(cart);
        session.setAttribute("totalCost", totalCost);
    }

    private int isInCart(Tariff tariff, List<Tariff> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == tariff.getId() || cart.get(i).getService().getId() == tariff.getService().getId()) {
                return i;
            }
        }
        return -1;
    }
}
