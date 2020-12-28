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
 * @author Malo
 */
public class EditProfileCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
            String forwardToJspPage = "editProfile.jsp";

            User user = (User) session.getAttribute("user");
            
            // Get relevant information from request
            String newUsername = request.getParameter("username");
            
            if(newUsername != null && !newUsername.isEmpty()){
              // Try updating username
              if (udao.changeUsername(user, newUsername)) {  
                // Update session object 
                user.setUsername(newUsername);
                session.setAttribute("message", "Username has been updated.");
                
                forwardToJspPage = "profile.jsp";
              }else{
                session.setAttribute("errorMessage", "An error occurred, try again later");
              }
            } else {
                session.setAttribute("errorMessage", "All fields are not filled with data");
            }
         return forwardToJspPage;
    }
    
}
