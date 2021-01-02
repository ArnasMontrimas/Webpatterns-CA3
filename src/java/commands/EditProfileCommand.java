/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.UserDao;
import dtos.User;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author grallm
 */
public class EditProfileCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
        
            Locale clientLocale = (Locale) session.getAttribute("currentLocale");
            ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
    
            String forwardToJspPage = "editProfile.jsp";

            User user = (User) session.getAttribute("user");
            
            // Get relevant information from request
            String newUsername = request.getParameter("username");
            
            if(newUsername != null && !newUsername.isEmpty()){
              // Try updating username
              if (udao.changeUsername(user, newUsername)) {  
                // Update session object 
                user.setUsername(newUsername);
                session.setAttribute("message", bundle.getString("editprofilecmd_success"));
                
                forwardToJspPage = "profile.jsp";
              }else{
                session.setAttribute("errorMessage", bundle.getString("general_error"));
              }
            } else {
                session.setAttribute("errorMessage", bundle.getString("missing_params"));
            }
         return forwardToJspPage;
    }
    
}
