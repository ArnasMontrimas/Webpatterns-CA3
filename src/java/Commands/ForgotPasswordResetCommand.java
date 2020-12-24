/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.UserDao;
import Daos.*;
import static Daos.Dao.DEFAULT_DB;
import static Daos.Dao.DEFAULT_JDBC;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class ForgotPasswordResetCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {            
            PasswordResetDao psdao = new PasswordResetDao(DEFAULT_DB,DEFAULT_JDBC);
            UserDao udao = new UserDao(DEFAULT_DB,DEFAULT_JDBC);
            SecurityAnswersDao sdao = new SecurityAnswersDao(DEFAULT_DB,DEFAULT_JDBC);
            String forwardToJspPage = "ForgotPassword.jsp";
            HttpSession session = request.getSession();
            String username = request.getParameter("Username"); 
            
            //Store clinet IP address here
            String remoteAddr = "";

            /******************************-- CHANGE IP_ADDRESS TO USERNAME (Make locking out Account specific and not Computer specific) --*****************************************/
            
            //Get clients IP address
            if (request != null) {
                remoteAddr = request.getHeader("X-FORWARDED-FOR");
                if (remoteAddr == null || "".equals(remoteAddr)) {
                    remoteAddr = request.getRemoteAddr();
                }
            }
            
            //Method here will handle users attempts at reseting their password
            if(psdao.handlePasswordResetAttempts(remoteAddr, session)) return forwardToJspPage;
            
            /******************************-- CHANGE IP_ADDRESS TO USERNAME (Make locking out Account specific and not Computer specific) --*****************************************/
            
            // Check if they have completed the security checks can be done in same servlet 
            String password = request.getParameter("password");
            if (password != null) {
              boolean lengthCheck = password.length() >= 12;
              boolean lowerCheck = password.matches(".*[a-z].*");
              boolean upperCheck = password.matches("^[^A-Z].*[A-Z].*");
              boolean numberCheck = password.matches(".*[0-9].*[^0-9]$");
              boolean symbolCheck = password.matches(".*[\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s].*[^\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s]$");

              // Check if password conditions aren't respected
              if (!lengthCheck || !lowerCheck || !upperCheck || !numberCheck || !symbolCheck) {
                session.setAttribute("Message", "Password conditions aren't respected");
                session.setAttribute("ShowPassInput","true"); 
                return forwardToJspPage;
              } else {
                  // Username cant be null if sent with password so now finally update the password
                  udao.forgotPasswordReset(password, username);
                  session.setAttribute("Message", "Password has been reset please login !");
                  return "Login.jsp";
              }
             }
            
            // The security question they answered
            // Dont need to check for null becasue it can never be null
            // As a default option is always sent with the form
            String question = request.getParameter("securityQuestion");
            // Server side validation needed for this one though
            String answer = request.getParameter("securityAnswer");

            
             if(username != null && answer != null && !username.isEmpty() && !answer.isEmpty()){
                 
                 // If the security question they answered is correct.
                 if (sdao.validateSecurityQuestion(answer,question,udao.getIDByUsername(username))) {
                     
                     // Correct username with the security answer corresponding to the security question chosen
                     // So let them update / choose a new password
                     session.setAttribute("ShowPassInput","true");
                     session.setAttribute("Username",username);
                     
                 } else {
                   session.setAttribute("Message","Either the security answer or the username is not correct"); 
                 }        
             } else {
                session.setAttribute("Message","Missing data supplied for the fields !");
            }
            
            return forwardToJspPage;
    }
    
}
