package ma.shop.servlets;

import ma.shop.database.dao.impl.GoodHibernateDao;
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

@WebServlet(value = "/addGood")
public class AddGoodServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AddGoodServlet.class);
    private GoodsDao goodsDao = new GoodHibernateDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        if (goodsDao.add(new Good(name, description, price))) {
            LOG.info("Add good with name - " + name);
        } else {
            LOG.error("Cant add good with name - " + name);
        }
        List<Good> goods = goodsDao.getAll();
        LOG.debug("Get goods, count: " + goods.size());
        request.setAttribute("goods", goods);

        request.getRequestDispatcher("/goodsControl.jsp").forward(request, response);
    }

}
