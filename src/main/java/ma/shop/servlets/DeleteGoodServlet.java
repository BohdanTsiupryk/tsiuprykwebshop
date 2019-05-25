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

@WebServlet(value = "/deleteGood")
public class DeleteGoodServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(DeleteGoodServlet.class);
    private static final GoodsDao goodDao = new GoodHibernateDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String delete = req.getParameter("delete");
        long deleteId = Long.valueOf(delete);

        LOG.debug("Try delete good with id: " + deleteId);
        if (goodDao.deleteById(deleteId)) {
            LOG.debug("Successful deleted id: " + deleteId);
        }

        List<Good> goods = goodDao.getAll();
        LOG.debug("Get goods, count: " + goods.size());
        req.setAttribute("goods", goods);

        req.getRequestDispatcher("goodsControl.jsp").forward(req, resp);
    }
}
