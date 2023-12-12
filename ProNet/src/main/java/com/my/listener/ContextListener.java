package com.my.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.*;
import it.sauronsoftware.cron4j.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String logPath = ctx.getRealPath("/WEB-INF/log4j2.log");
        System.setProperty("logFile", logPath);
        final Logger log = LogManager.getLogger(ContextListener.class);
        log.debug("logPath = " + logPath);

        String localesFileName = ctx.getInitParameter("locales");
        String localesFilePath = ctx.getRealPath(localesFileName);
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFilePath));
        } catch (IOException e) {
            log.error("Cannot load locale");
        }
        ctx.setAttribute("locales", locales);
        locales.list(System.out);

        Scheduler s = new Scheduler();
        s.schedule("52 11 20 * *", new FundsWithdrawnTask());
        s.start();
    }
}
