package com.my.command.cart;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.Tariff;
import com.my.logic.LogicException;
import com.my.util.UtilFunctions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RemoveFromCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        HttpSession session = req.getSession();
        List<Tariff> cart = (List<Tariff>) session.getAttribute("cart");
        int tariffId = Integer.parseInt(req.getParameter("tariffId"));
        int index = findIndex(tariffId, cart);
        cart.remove(index);

        if (cart.size() == 0) {
            session.removeAttribute("cart");
            session.removeAttribute("totalCost");
        } else {
            session.setAttribute("cart", cart);
            double totalCost = UtilFunctions.findTotalCost(cart);
            session.setAttribute("totalCost", totalCost);
        }
        return "controller?command=showCart";
    }

    // helper method
    private int findIndex(int tariffId, List<Tariff> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == tariffId) {
                return i;
            }
        }
        return -1;
    }
}
