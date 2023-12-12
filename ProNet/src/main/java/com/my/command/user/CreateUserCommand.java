package com.my.command.user;

import com.my.command.AppException;
import com.my.command.Command;
import com.my.domain.*;
import com.my.logic.LogicException;
import com.my.logic.ServiceManager;
import com.my.logic.UserManager;
import com.my.util.UtilFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Properties;

public class CreateUserCommand implements Command {
    private static final Logger log = LogManager.getLogger(CreateUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws AppException {
        User user = new User();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setRole(Role.SUBSCRIBER);

        UserDetails userDetails = new UserDetails();
        userDetails.setFirstName(req.getParameter("firstName"));
        userDetails.setLastName(req.getParameter("lastName"));
        userDetails.setEmail(req.getParameter("email"));
        userDetails.setPhoneNumber(req.getParameter("phoneNumber"));
        userDetails.setAddress(req.getParameter("address"));

        try {
            long userId = UserManager.getInstance().createUserWithDetails(user, userDetails);
            user.setId(userId);
        } catch (LogicException e) {
            throw new AppException(e.getMessage(), e);
        }

        System.out.println(req.getParameter("requestId"));
        System.out.println(req.getParameter("planCost"));
        if (!req.getParameter("requestId").isEmpty()) {
            long requestId = Long.parseLong(req.getParameter("requestId"));
            double planCost = Double.parseDouble(req.getParameter("planCost"));

            TariffPlan tariffPlan = new TariffPlan();
            tariffPlan.setCost(planCost);
            tariffPlan.setStartDate(UtilFunctions.getStartDate());
            tariffPlan.setEndDate(UtilFunctions.getStartDate());
            tariffPlan.setUserId(user.getId());
            tariffPlan.setStatus(Status.BLOCKED);

            try {
                List<Tariff> tariffs = ServiceManager.getInstance().findAllTariffsByRequestId(requestId);
                long planId = ServiceManager.getInstance().createTariffPlanForUser(tariffPlan, tariffs);
                tariffPlan.setId(planId);
                ServiceManager.getInstance().deleteRequest(requestId);
            } catch (LogicException e) {
                throw new AppException(e.getMessage(), e);
            }
            req.removeAttribute("userRequest");
        }

        Thread t = new Thread(() -> sendEmail(req, user, userDetails));
        t.start();

        return  "controller?command=listUsers&page=1&pageSize=3";
    }

    private void sendEmail(HttpServletRequest req, User user, UserDetails userDetails) {
        // reads SMTP server setting from web.xml file
        ServletContext context = req.getServletContext();
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String username = context.getInitParameter("user");
        String password = context.getInitParameter("pass");

        // Sender's email ID needs to be mentioned
        String from = username;

        // Recipient's email ID needs to be mentioned.
        // String to = userDetails.getEmail();
        String to = username;

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        // Get the Session object.
        Session session = Session.getInstance(properties, auth);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Subscriber credentials");

            // Now set the actual message
            message.setText("Your login: " + user.getLogin() + "\n" + "Your password: " + user.getPassword() + "\n");

            // Send message
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Cannot send an email to subscriber");
        }
    }
}
