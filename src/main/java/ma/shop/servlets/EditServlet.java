package ma.shop.servlets;

import ma.shop.database.dao.DatabaseUserDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.dao.UserHibernateDao;
import ma.shop.database.model.Role;
import ma.shop.database.model.User;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/edit")
public class EditServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(EditServlet.class);
    private static final UserDao userService = new UserHibernateDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.valueOf(request.getParameter("edit"));
        User user = userService.getUserById(id).get();
        User userSession = (User) request.getSession().getAttribute("currentUser");
        log.debug("Get user with email: " + user.getEmail());

        request.setAttribute("user", user);
        request.setAttribute("editorRole", userSession.getRole().getName());
        log.debug("Send user:" + user.getEmail() +" to view");

        RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        Role role = req.getParameter("role") == null ? Role.USER : Role.valueOf(req.getParameter("role"));

        if (userService.updateUser(id, new User(email, password, address,role))) {
            log.debug("User with id: " + id + ", change information");
        }

        User user = (User) req.getSession().getAttribute("currentUser");

        if (user.getRole().equals(Role.ADMIN)) {
            List<User> users = userService.getUsers();
            log.debug("Get users, count: " + users.size());
            req.setAttribute("users", users);
            req.getRequestDispatcher("admin/userControl.jsp").forward(req, resp);
        }

        User userFromDB = userService.getUserById(id).get();
        req.setAttribute("user", userFromDB);

        req.getRequestDispatcher("userProfile.jsp").forward(req, resp);
    }
}
