/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.UserDao;
import dtos.*;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author grallm
 */
public class ChangePasswordCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
        
            Locale clientLocale = (Locale) session.getAttribute("currentLocale");
            ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
    
            String forwardToJspPage = "changePassword.jsp";
            
            // Get both password data
            String currentPassword = request.getParameter("currentPassword");        
            String newPassword = request.getParameter("password");
            User user = (User) session.getAttribute("user");
            
            if(user != null && currentPassword != null && newPassword != null
                && !currentPassword.isEmpty() && !newPassword.isEmpty()){
              boolean lengthCheck = newPassword.length() >= 12;
              boolean lowerCheck = newPassword.matches(".*[a-z].*");
              boolean upperCheck = newPassword.matches("^[^A-Z].*[A-Z].*");
              boolean numberCheck = newPassword.matches(".*[0-9].*[^0-9]$");
              boolean symbolCheck = newPassword.matches(".*[\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s].*[^\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s]$");
              boolean notUserName = !newPassword.toLowerCase().contains(user.getUsername().toLowerCase());
              boolean notOldPwd = !newPassword.toLowerCase().contains(currentPassword.toLowerCase());

              // Check if password conditions aren't respected
              if (!lengthCheck || !lowerCheck || !upperCheck || !numberCheck || !symbolCheck || !notUserName || !notOldPwd) {
                session.setAttribute("errorMessage", bundle.getString("changecmd_conditions"));
                return forwardToJspPage;
              }

              //If the old password matches the one in database
              if (udao.passwordReset(currentPassword,newPassword,user.getUsername())) {
                session.setAttribute("message", bundle.getString("changecmd_success"));
                forwardToJspPage = "profile.jsp";
              } else {
                session.setAttribute("errorMessage", bundle.getString("changecmd_current_incorrect"));
              }
            } else {
              session.setAttribute("errorMessage", bundle.getString("missing_params"));
            }
            
            return forwardToJspPage;
    }
    
}
