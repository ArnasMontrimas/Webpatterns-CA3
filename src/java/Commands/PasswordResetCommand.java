/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.UserDao;
import Daos.*;


import Dtos.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class PasswordResetCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
            String forwardToJspPage = "PasswordReset.jsp";
            
            // Get both password data
            String currentPassword = request.getParameter("currentPassword");        
            String newPassword = request.getParameter("newPassword");
            User u = (User)session.getAttribute("User");
            
            // The security question they answered
            // Dont need to check for null becasue it can never be null
            // As a default option is always sent with the form
            String question = request.getParameter("securityQuestion");
            // Server side validation needed for this one though
            String answer = request.getParameter("securityAnswer");

            
             if(u != null && currentPassword != null && newPassword != null && answer != null 
                     && !currentPassword.isEmpty() && !newPassword.isEmpty() && !answer.isEmpty() ){
                boolean lengthCheck = newPassword.length() >= 12;
                boolean lowerCheck = newPassword.matches(".*[a-z].*");
                boolean upperCheck = newPassword.matches("^[^A-Z].*[A-Z].*");
                boolean numberCheck = newPassword.matches(".*[0-9].*[^0-9]$");
                boolean symbolCheck = newPassword.matches(".*[\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s].*[^\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s]$");
                boolean notUserName = !newPassword.toLowerCase().contains(u.getUsername().toLowerCase());
                boolean notOldPwd = !newPassword.toLowerCase().contains(currentPassword.toLowerCase());

                // Check if password conditions aren't respected
                if (!lengthCheck || !lowerCheck || !upperCheck || !numberCheck || !symbolCheck || !notUserName || !notOldPwd) {
                  session.setAttribute("Message", "Password conditions aren't respected");
                  return forwardToJspPage;
                }

                //If the old password matches the one in database
                if (udao.passwordReset(currentPassword,newPassword,u.getUsername())) {
                  session.setAttribute("Message","Your password has been reset !");
                }  else
                {
                  session.setAttribute("Message","Current password is incorrect !");
                }
             } else {
                session.setAttribute("Message","Missing data supplied for the fields !");
            }
            
            return forwardToJspPage;
    }
    
}
