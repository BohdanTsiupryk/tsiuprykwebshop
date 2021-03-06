package ma.shop.servlets;

import ma.shop.database.dao.impl.GoodHibernateDao;
import ma.shop.database.dao.GoodsDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/goods")
public class GoodsServlet extends HttpServlet {
    private GoodsDao goodsDao = new GoodHibernateDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentUser", request.getSession().getAttribute("currentUser"));
        request.setAttribute("goods", goodsDao.getAll());

        request.getRequestDispatcher("goods.jsp").forward(request, response);
    }
}
