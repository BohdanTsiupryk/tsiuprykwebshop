package ma.shop.servlets;

import ma.shop.database.dao.DatabaseGoodDao;
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
    private static final Logger log = Logger.getLogger(AddGoodServlet.class);
    private GoodsDao goodsDao = new DatabaseGoodDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        if (goodsDao.addGood(new Good(name, description, price))) {
            log.info("Add good with name - " + name);
        } else {
            log.error("Cant add good with name - " + name);
        }
        List<Good> goods = goodsDao.getGoods();
        log.debug("Get goods, count: " + goods.size());
        request.setAttribute("goods", goods);

        request.getRequestDispatcher("/goodsControl.jsp").forward(request, response);
    }

}
