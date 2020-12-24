/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import static Daos.Dao.DEFAULT_DB;
import static Daos.Dao.DEFAULT_JDBC;
import Daos.UserDao;
import Dtos.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class LoginCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            
            UserDao udao = new UserDao(DEFAULT_DB,DEFAULT_JDBC);
            HttpSession session = request.getSession();
            String forwardToJspPage = "Login.jsp";
            
            // Get relevant information from request
            String user = request.getParameter("username");        
            String pass = request.getParameter("password");
            
             if(user != null && pass != null && !user.isEmpty() && !pass.isEmpty()){
                   
                 int userID = udao.validateLogin(user,pass);
                 // Username and pass incorrect
                 if (userID == 0) 
                 {  
                    session.setAttribute("Message","Username or password is incorrect !");                                    
                 }else if (userID == -1) {
                    session.setAttribute("Message","That account is no longer active.");
                 } else {
                   User u = udao.getUserByID(userID);
                   session.setAttribute("User",u);
                   forwardToJspPage = "ViewBooks.jsp";     
                 }
             } else {
                    session.setAttribute("BadLogin","Missing data supplied for the fields !");
                }
         return forwardToJspPage;
    }
    
}
