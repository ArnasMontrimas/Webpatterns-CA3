/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.PasswordResetDao;
import daos.UserDao;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class ValidateEmailCodeSendPasswordCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {            
            UserDao udao = new UserDao();
            PasswordResetDao prd = new PasswordResetDao();
            HttpSession session = request.getSession();
        
            Locale clientLocale = (Locale) session.getAttribute("currentLocale");
            ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
    
            String forwardToJspPage = "resetPassword.jsp";
            
            //Get code which was typed by user
            String code = request.getParameter("code");
            
            //Get code which was sent & recipients email address & users id
            String sentCode = session.getAttribute("inputCode").toString();
            String email = session.getAttribute("userEmail").toString();
            int id = Integer.parseInt(session.getAttribute("userId").toString());
            
            //Information contained in the email
            String message = bundle.getString("validateemailcmd_message");
            String subject = bundle.getString("validateemailcmd_subject");

            //Check user attempts at entering correct code
            if(prd.handlePasswordResetAttempts(id, session)) {
                //Attempts have been exceeded
                //Remove varaibles from session & Make the user start the proccess from step 1
                //Will be annoying for fradulent users, but for a legit user 3 attempts should be plenty
                session.removeAttribute("code");
                session.removeAttribute("userEmail");
                session.removeAttribute("userId");
                session.removeAttribute("showCodeInput");
                
                return forwardToJspPage;
            }
            else {
                if(code != null && sentCode.equals(code)) {
                    //Give a user a new password to be used for login he then may change it at the profile page
                    String password = udao.generateSetNewPassword(id);
                    if(password == null) {
                        session.setAttribute("errorMessage", bundle.getString("general_error"));
                        return forwardToJspPage;
                    }
                    
                    //Make sure email sent successfully
                    if(udao.sendEmailTo(email, message+password, subject)) {
                        //Everything checks out so we remove these session variables
                        session.removeAttribute("code");
                        session.removeAttribute("userEmail");
                        session.removeAttribute("userId");
                        session.removeAttribute("showCodeInput");
                        
                        //Return user back to login page and let them know email was sent successfully
                        session.setAttribute("message", bundle.getString("validateemailcmd_success"));
                        forwardToJspPage = "login.jsp";
                    }
                    else {
                        //Keep displaying enter code form so the user can try again
                        session.setAttribute("showCodeInput", true);
                        session.setAttribute("message", bundle.getString("forgotpwdcmd_notsent"));
                    }
                }
                else {
                    //Keep displaying enter code form so the user can try again
                    session.setAttribute("showCodeInput", true);
                    session.setAttribute("errorMessage", bundle.getString("validateemailcmd_incorrect_code"));
                }
                return forwardToJspPage;
            }
    }
    
}
