package seattlerHub.servlet;

import seattlerHub.dal.UsersDao;
import seattlerHub.model.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/register")
public class CreateUserServlet extends HttpServlet {
    protected UsersDao usersDao;
    protected Users user;
    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // for secure the message do trans at do post. much safe
        Map<String, String> message = new HashMap<>();
        req.setAttribute("message",message);
        req.getRequestDispatcher("/Register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> message =  new HashMap<>();
        req.setAttribute("message",message);
        String UserName = req.getParameter("username");
        try {
        
        	if(UserName == null || usersDao.getUserByUserName(UserName)!=null ||UserName.trim().isEmpty()){
        	
        	message.put("success","invalid username");

        }else{
            String Email = req.getParameter("email");
            String FirstName = req.getParameter("firstname");
            String LastName = req.getParameter("lastname");
            String phone = req.getParameter("phonenumber");
            int phoneNum = Integer.parseInt(phone);
            String Password = req.getParameter("password");
            
                user = new Users(UserName, Password,FirstName,LastName,
                        Email,phoneNum,null,false,false);
                user = usersDao.create(user);
            }
        }catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        req.getRequestDispatcher("/Register.jsp").forward(req,resp);
        resp.sendRedirect("Home.jsp");


    }
}
