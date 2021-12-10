package seattlerHub.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seattlerHub.dal.*;
import seattlerHub.model.*;


@WebServlet("/filterHousing")
public class FilterHousingServlet extends HttpServlet {
	List<Housing> results;
	CrimeCasesDao crimeCasesDao;
	RestaurantsDao restaurantsDao;	
	SchoolsDao schoolsDao;
	ParksDao parksDao;
	CulturalSpacesDao culturalSpacesDao;
	
    @Override
    public void init() throws ServletException {
        crimeCasesDao = CrimeCasesDao.getInstance();
        restaurantsDao = RestaurantsDao.getInstance();
        schoolsDao = SchoolsDao.getInstance();
        parksDao = ParksDao.getInstance();
        culturalSpacesDao = CulturalSpacesDao.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> messages =  new HashMap<>();
        results = new ArrayList<>();
        List<List<Housing>> allReturned = new ArrayList<List<Housing>>() ;

        req.setAttribute("messages",messages);
        try {
        	String[] choices = req.getParameterValues("filterchoice");
        	for (String choice : choices) {
        		if (choice.equals("crimecases")) {
        			allReturned.add(crimeCasesDao.getTop20HousingsWithLessCrimeCases());
        		}
        		if (choice.equals("restaurants")) {
        			allReturned.add(restaurantsDao.getTop20HousingsWithMostRestaurants());
        		}
        		if (choice.equals("schools")) {
        			allReturned.add(schoolsDao.getTop20HousingsWithMostSchools());
        		}
        		if (choice.equals("parks")) {
        			allReturned.add(parksDao.getTop20HousingsWithMostParks());
        		}
        		if (choice.equals("culturalspaces")) {
        			allReturned.add(culturalSpacesDao.getTop20HousingsWithMostCulturalSpaces());
        		}
        	}
        	
        	if (allReturned.size() == 0)                
        		messages.put("title","no more list");
        	else if (allReturned.size() == 1)
        		results = allReturned.get(0);
        	else {
        		results = allReturned.get(0);
        		for (int i = 1; i < allReturned.size(); i++) {
        			List<Housing> curList = allReturned.get(i);
        			List<Integer> toRemove = new ArrayList<>();
        			int removeIndex = -1;
        			for (int j = 0; j < results.size(); j++) {
        				if (!curList.contains(results.get(j))) {
        					toRemove.add(j);
        				}
        			}
        			if (toRemove.size() != 0) {
            			for (int k = toRemove.size() - 1; k >= 0; k--) {
            				removeIndex = (int) toRemove.get(k);
            				results.remove(removeIndex);	
            			}
        			}
        		}
        	}
        	
            if(results==null){
                messages.put("title","no more list");
            }else{
                messages.put("title","works");
            }
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("housings",results);
        req.getRequestDispatcher("/Home.jsp").forward(req, resp);
    }
}





