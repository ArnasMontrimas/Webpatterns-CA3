/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.UserDao;
import Dtos.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class ChangeUsernameCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
            String forwardToJspPage = "ChangeUsername.jsp";
            
            // Get relevant information from request
            String currentUsername = request.getParameter("CurrentUsername");        
            String newUsername = request.getParameter("NewUsername");
            
             if(currentUsername != null && newUsername != null && !currentUsername.isEmpty() && !newUsername.isEmpty()){
                 
                 // Username is available and updated
                 if (udao.changeUsername(newUsername, currentUsername)) 
                 {  
                    // Update session object 
                    User user = (User)session.getAttribute("User");
                    user.setUsername(newUsername);
                    session.setAttribute("Message","Username has been updated.");                                    
                 }else{
                    session.setAttribute("Message","That username is taken please try again.");
                 } 
             } else {
                    session.setAttribute("Message","Missing data supplied for the fields !");
                }
         return forwardToJspPage;
    }
    
}
