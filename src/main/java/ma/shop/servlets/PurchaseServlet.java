package ma.shop.servlets;

import ma.shop.database.dao.DatabaseGoodDao;
import ma.shop.database.dao.DatabaseUserDao;
import ma.shop.database.dao.GoodsDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.model.Good;
import ma.shop.database.model.User;
import ma.shop.service.MailService;
import ma.shop.utils.RandomGenerator;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(value = "/buy")
public class PurchaseServlet extends HttpServlet {
    private Map<Long, String> codes = new HashMap<>();
    private GoodsDao goodsDao = new DatabaseGoodDao();
    private UserDao userDao = new DatabaseUserDao();
    private static final Logger log = Logger.getLogger(PurchaseServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        User user = (User) request.getSession().getAttribute("currentUser");

        String codeFromMap = codes.get(user.getId());
        request.setAttribute("userEmail", user.getEmail());
        int good = userDao.getUserById(user.getId()).get().getGood();

        if (code.equals(codeFromMap)) {
            MailService.sendGood(user.getEmail(), goodsDao.getGoodById(good).get());
            request.setAttribute("message", "Goods send to your email:" + user.getEmail());
        } else {
            request.setAttribute("message", "Bad confrimation code!");
        }
        userDao.removeGood(user);
        request.getRequestDispatcher("information.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = RandomGenerator.randomCode();
        User user = (User) request.getSession().getAttribute("currentUser");

        Optional<Good> goodById = goodsDao.getGoodById(Long.parseLong(request.getParameter("buyId")));

        if (goodById.isPresent()) {
            userDao.addGood(goodById.get(), user);
            codes.put(user.getId(), code);

            MailService.sendCode(user.getEmail(), code);

            request.getRequestDispatcher("codeConfrimingPage.jsp").forward(request, response);
        }
    }
}
