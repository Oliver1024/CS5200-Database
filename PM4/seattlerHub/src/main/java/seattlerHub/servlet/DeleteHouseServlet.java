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

@WebServlet("/delete")
public class DeleteHouseServlet extends HttpServlet {
    protected HousingDao housingDao;

    @Override
    public void init() throws ServletException {
        housingDao =HousingDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    	 String HousingId = req.getParameter("housingId");
//         int housingId = Integer.parseInt(HousingId);
//         try {
//             Housing housing = housingDao.getHousingByHousingId(housingId);
//             housingDao.delete(housing);
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
         req.getRequestDispatcher("/Delete.jsp").forward(req,resp);
     
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String HousingId = req.getParameter("housingid");
        int housingId = Integer.parseInt(HousingId.trim());
        try {
            Housing housing = housingDao.getHousingByHousingId(housingId);
            housingDao.delete(housing);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/Home.jsp").forward(req,resp);
    }
}
