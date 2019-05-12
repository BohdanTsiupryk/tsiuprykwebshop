package ma.shop.servlets;

import ma.shop.database.dao.DatabaseUserDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/userControl")
public class AdminServlet extends HttpServlet {
    private UserDao userDao = new DatabaseUserDao();
    private static final Logger log = Logger.getLogger(AdminServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.getUsers();

        request.setAttribute("users", users);
        request.getRequestDispatcher("userControl.jsp").forward(request, response);
    }
}
