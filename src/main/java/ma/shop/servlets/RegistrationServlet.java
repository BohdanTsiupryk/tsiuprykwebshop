package ma.shop.servlets;

import ma.shop.database.dao.DatabaseUserDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.exception.NoSuchUserIdException;
import ma.shop.database.model.Role;
import ma.shop.database.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {
    private UserDao userDao = new DatabaseUserDao();
    private static final Logger log = Logger.getLogger(RegistrationServlet.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        if (!password.equals(repassword)) {
            log.info("Email: " + email + ", password and repassword not the same");
            request.setAttribute("samePass", false);
            request.getRequestDispatcher("registration.jsp").forward(request, response);
        } else {
            log.info("Try register user with email: " + email);

            try {
                userDao.addUser(new User(email, password, address, Role.USER));
            } catch (NoSuchUserIdException e) {
                log.info("Try register user with email: " + email);
                request.setAttribute("message", "This login already exist");
                request.getRequestDispatcher("info/information.jsp").forward(request, response);
            }

            log.info("Success register user with email: " + email);
            request.setAttribute("successReg", "You successfully register");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }
}
