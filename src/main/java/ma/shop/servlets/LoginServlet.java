package ma.shop.servlets;

import ma.shop.database.dao.UserDao;
import ma.shop.database.dao.UserHibernateDao;
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
    private UserDao userDao = new UserHibernateDao();
    private static final Logger LOG = Logger.getLogger(LoginServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        Optional<User> optionalUser = userDao.containce(email);

        if (optionalUser.isPresent()) {
            User userFromDb = optionalUser.get();
            LOG.info(userFromDb.getEmail());
            if (choosePath(request, response, pass, userFromDb)) {
                return;
            }
            request.setAttribute("badPass", true);
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private boolean choosePath(HttpServletRequest request, HttpServletResponse response, String pass, User userFromDb) throws ServletException, IOException {
        if (SHA512SecureUtil.getSecurePassword(pass, userFromDb.getSalt()).equals(userFromDb.getPassword())) {
            request.getSession().setAttribute("currentUser", userFromDb);
            if (userFromDb.getRole().equals(Role.USER)) {
                request.getRequestDispatcher("/goods").forward(request, response);
                return true;
            } else if (userFromDb.getRole().equals(Role.ADMIN)) {
                request.getRequestDispatcher("/userControl").forward(request, response);
                return true;
            }
        }
        return false;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
