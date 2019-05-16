package ma.shop.servlets;

import ma.shop.database.dao.DatabaseGoodDao;
import ma.shop.database.dao.GoodHibernateDao;
import ma.shop.database.dao.GoodsDao;
import ma.shop.database.model.Good;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/goodsControl")
public class GoodsControlServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(DeleteServlet.class);
    private GoodsDao goodsDao = new GoodHibernateDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Good> goods = goodsDao.getGoods();
        request.setAttribute("goods", goods);
        request.getRequestDispatcher("goodsControl.jsp").forward(request, response);
    }
}
