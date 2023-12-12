package com.my.command;

import com.my.domain.Tariff;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTariffCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        int tariffId = Integer.parseInt(req.getParameter("tariffId"));
        String description = req.getParameter("description");
        double cost = Double.parseDouble(req.getParameter("cost"));
        Tariff tariff = new Tariff();
        tariff.setId(tariffId);
        tariff.setDescription(description);
        tariff.setCost(cost);
        try {
            ServiceManager.getInstance().updateTariff(tariff);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }
        return "controller?command=listTariffsByPage&page=1";
    }
}
