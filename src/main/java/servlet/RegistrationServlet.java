package servlet;

import model.User;
import service.registration.RegistrationResult;
import service.registration.RegistrationService;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = ("servlet/RegistrationServlet"))
public class RegistrationServlet extends HttpServlet {

    @EJB
    private RegistrationService service;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(username, password);
        service.setUser(user);
        RegistrationResult result = service.execute();

        HttpSession session = request.getSession(true);

        if (result.success()) {
            session.setAttribute("content", result.getContent());
            response.sendRedirect("/WeekendProject/login.jsp");
        } else {
            session.setAttribute("failureReason", result.getFailureReason());
            response.sendRedirect("/WeekendProject/registration.jsp");
        }
    }
}