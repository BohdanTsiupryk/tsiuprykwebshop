package ma.shop.servlets;

import ma.shop.database.dao.impl.GoodHibernateDao;
import ma.shop.database.dao.GoodsDao;
import ma.shop.database.dao.UserDao;
import ma.shop.database.dao.impl.UserHibernateDao;
import ma.shop.database.model.Good;
import ma.shop.database.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/stash")
public class StashServlet extends HttpServlet {
    private GoodsDao goodsDao = new GoodHibernateDao();
    private UserDao userDao = new UserHibernateDao();
    private static final Logger LOG = Logger.getLogger(PurchaseServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long goodId = Long.parseLong(request.getParameter("buyId"));
        User currentUser = (User) request.getSession().getAttribute("currentUser");

        Optional<Good> goodFromDb = goodsDao.getById(goodId);
        goodFromDb.ifPresent(good -> userDao.addToOrder(good, currentUser));

        request.getRequestDispatcher("/goods").forward(request, response);
    }
}
