package ma.shop.servlets;

import ma.shop.database.dao.DatabaseUserDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.model.Role;
import ma.shop.database.model.User;
import ma.shop.utils.SHA512SecureUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new DatabaseUserDao();
    private static final Logger log = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        Optional<User> optionalUser = userDao.containce(email);

        if (optionalUser.isPresent()) {
            User userFromDb = optionalUser.get();
            if (SHA512SecureUtil.getSecurePassword(pass, userFromDb.getSalt()).equals(userFromDb.getPassword())) {
                request.getSession().setAttribute("currentUser", userFromDb);
                if (userFromDb.getRole().equals(Role.USER)) {
                    request.getRequestDispatcher("/goods").forward(request, response);
                    return;
                } else if (userFromDb.getRole().equals(Role.ADMIN)) {
                    request.getRequestDispatcher("/userControl").forward(request, response);
                    return;
                }
            }
            request.setAttribute("badPass", true);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
