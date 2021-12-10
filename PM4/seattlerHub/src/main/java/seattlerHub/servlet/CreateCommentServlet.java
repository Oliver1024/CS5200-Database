package seattlerHub.servlet;


import seattlerHub.dal.HousingDao;
import seattlerHub.dal.ReviewsDao;
import seattlerHub.dal.UsersDao;
import seattlerHub.model.Housing;
import seattlerHub.model.Reviews;
import seattlerHub.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/comments")
public class CreateCommentServlet extends HttpServlet {
    protected HousingDao housingDao;
    protected ReviewsDao reviewsDao;
    protected UsersDao usersDao;
	List<Reviews> results;

    @Override
    public void init() throws ServletException {
        housingDao = HousingDao.getInstance();
        reviewsDao = ReviewsDao.getInstance();
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Map<String,String> messages = new HashMap<>();
    	req.setAttribute("messages", messages);
    	int housingId = Integer.parseInt(req.getParameter("housingId"));
        try {
             Housing housing = housingDao.getHousingByHousingId(housingId);
             results = reviewsDao.getReviewsByHousing(housing);
             if (results.size() != 0)
            	 messages.put("title","comments for Housing " + housingId);
             else
            	 messages.put("title", "no comments");
         } catch (SQLException e) {
             e.printStackTrace();
        	 messages.put("title", "something wrong");
         }
         req.setAttribute("reviews", results);
         req.getRequestDispatcher("/Comments.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> messages = new HashMap<>();
        String Rating = req.getParameter("ratinghouse");
        double rating = Double.parseDouble(Rating);
        String userName = req.getParameter("username");
        String comments = req.getParameter("comment");
        String housingid = req.getParameter("housingId");
        int housingId =Integer.parseInt(housingid);
        try {
            Housing house = housingDao.getHousingByHousingId(housingId);
            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            Users user= usersDao.getUserByUserName(userName);
            Reviews reviews = new Reviews(-1,timestamp,comments, rating,user,house);
            reviewsDao.create(reviews);
            messages.put("title","works!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/Comments.jsp").forward(req,resp);
    }
}
