package seattlerHub.servlet;

import seattlerHub.dal.HousingDao;
import seattlerHub.model.Housing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/edit")
public class HousingUpdateServlet  extends HttpServlet {
    protected HousingDao houseDao;

    @Override
    public void init() throws ServletException {
        houseDao = HousingDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> messages =  new HashMap<>();
        req.setAttribute("messages",messages);
        req.getRequestDispatcher("/Edit.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> messages = new HashMap<>();
        req.setAttribute("messages",messages);

        int housingId = Integer.parseInt(req.getParameter("housingid").trim());
        String houseName = req.getParameter("housingname");
        int price = Integer.parseInt(req.getParameter("price"));
      
        try {
            Housing housing = houseDao.getHousingByHousingId(housingId);
            housing = houseDao.updateHousing(housing,houseName,price);
            messages.put("success","Successfully updated");
            req.getRequestDispatcher("/Edit.jsp").forward(req,resp);
        } catch (Exception e) {
            messages.put("success","something run");
            e.printStackTrace();
        }
        req.getRequestDispatcher("/Edit.jsp").forward(req,resp);
    }
}
