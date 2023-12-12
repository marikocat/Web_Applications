package com.my.logic;

public class LogicException extends Exception {
    public LogicException() { super(); }

    public LogicException(String message) { super(message); }

    public LogicException(String message, Throwable cause) { super(message, cause); }

    public LogicException(Throwable cause) { super(cause); }
}
