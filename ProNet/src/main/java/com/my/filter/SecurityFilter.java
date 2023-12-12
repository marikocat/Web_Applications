package com.my.filter;

import com.my.domain.Role;
import com.my.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private static final Map<String, Set<Role>> permissions = new HashMap<>();
    static {
        Set<Role> all = new HashSet<>();
        all.addAll(Arrays.asList(Role.values()));
        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMINISTRATOR);
        Set<Role> client = new HashSet<>();
        client.add(Role.SUBSCRIBER);

        permissions.put("logOut", all);
        permissions.put("showAdminPage", admin);
        permissions.put("showSubscriberPage", client);
        permissions.put("createUser", admin);
        permissions.put("updateUser", admin);
        permissions.put("deleteUser", admin);
        permissions.put("fillRegisterForm", admin);
        permissions.put("showRegisterForm", admin);
        permissions.put("deleteRequest", admin);
        permissions.put("listUsers", admin);
        permissions.put("listRequests", admin);
        permissions.put("listTariffsByPage",admin);
        permissions.put("editTariff",admin);
        permissions.put("downloadFile", client);
        permissions.put("createTariffPlan", all);
        permissions.put("deleteTariffPlan", admin);
        permissions.put("showTariffPlan", all);
        permissions.put("changeTariffPlanStatus", admin);
        permissions.put("showUserAccount", client);
        permissions.put("refillUserAccount", client);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("SecurityFilter#doFilter before");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getParameter("command");
        System.out.println(url);
        Set<Role> roles = permissions.get(url);
        System.out.println(roles);
        if (roles != null) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("currentUser");
                if (user != null && roles.contains(user.getRole())) {
                    chain.doFilter(request, response);
                    System.out.println("SecurityFilter#doFilter after: roles are not null");
                    return;
                }
            }
        } else {
            chain.doFilter(request, response);
            System.out.println("SecurityFilter#doFilter after: roles are null");
            return;
        }
        System.out.println("go to sendRedirect");
        resp.sendRedirect("controller?command=showLoginForm&message=accessDenied");
        System.out.println("SecurityFilter#doFilter after sendRedirect\n");
    }
}
