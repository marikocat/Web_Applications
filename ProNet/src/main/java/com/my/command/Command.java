package com.my.command;

import com.my.dao.DAOException;
import com.my.logic.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException;
}
