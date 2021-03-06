package ma.shop.servlets;

import ma.shop.database.dao.impl.GoodHibernateDao;
import ma.shop.database.dao.GoodsDao;
import ma.shop.database.model.Good;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/editGood")
public class EditGoodServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(EditGoodServlet.class);
    private static final GoodsDao goodDao = new GoodHibernateDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.valueOf(request.getParameter("edit"));
        Good good = goodDao.getById(id).get();
        LOG.debug("Get good with name: " + good.getName());

        request.setAttribute("good", good);
        LOG.debug("Send good:" + good.getName() +" to view");

        RequestDispatcher dispatcher = request.getRequestDispatcher("editGood.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double price = Double.valueOf(req.getParameter("price"));

        if (goodDao.update(new Good(name, description, price))) {
            LOG.debug("Good with id: " + id + ", change information");
        }
        List<Good> goods = goodDao.getAll();
        req.setAttribute("goods", goods);
        req.getRequestDispatcher("goodsControl.jsp").forward(req, resp);
    }
}
