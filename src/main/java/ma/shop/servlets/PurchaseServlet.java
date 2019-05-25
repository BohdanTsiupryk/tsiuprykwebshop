package ma.shop.servlets;

import ma.shop.database.dao.impl.GoodHibernateDao;
import ma.shop.database.dao.GoodsDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.dao.impl.UserHibernateDao;
import ma.shop.database.model.Order;
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

@WebServlet(value = "/buy")
public class PurchaseServlet extends HttpServlet {
    private Map<Long, String> codes = new HashMap<>();
    private GoodsDao goodsDao = new GoodHibernateDao();
    private UserDao userDao = new UserHibernateDao();
    private static final Logger LOG = Logger.getLogger(PurchaseServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        User user = (User) request.getSession().getAttribute("currentUser");

        String codeFromMap = codes.get(user.getId());
        request.setAttribute("userEmail", user.getEmail());
        Order order = userDao.getById(user.getId()).get().getOrder();

        if (code.equals(codeFromMap)) {
            MailService.sendGood(user.getEmail(), order.getGoods());
            request.setAttribute("message", "Goods send to your email:" + user.getEmail());
        } else {
            request.setAttribute("message", "Bad confrimation code!");
        }
        userDao.clearOrder(user);
        request.getRequestDispatcher("information.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = RandomGenerator.randomCode();
        User user = (User) request.getSession().getAttribute("currentUser");

        codes.put(user.getId(), code);

        MailService.sendCode(user.getEmail(), code);

        request.getRequestDispatcher("codeConfrimingPage.jsp").forward(request, response);
    }
}
