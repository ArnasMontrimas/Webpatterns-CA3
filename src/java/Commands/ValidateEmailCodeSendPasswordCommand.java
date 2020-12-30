/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.UserDao;
import Daos.*;
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
            String forwardToJspPage = "resetPassword.jsp";
            
            //Get code which was typed by user
            String code = request.getParameter("code");
            
            //Get code which was sent & recipients email address & users id
            String sentCode = session.getAttribute("inputCode").toString();
            String email = session.getAttribute("userEmail").toString();
            int id = Integer.parseInt(session.getAttribute("userId").toString());
            
            //Information contained in the email
            String message = "Use this password to login and change it at the profile screen\nPassword: ";
            String subject = "PASSWORD RETRIEVED";

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
                        session.setAttribute("errorMessage", "Something went wrong with retrieving password");
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
                        session.setAttribute("message", "Password sent to email successfully");
                        forwardToJspPage = "login.jsp";
                    }
                    else {
                        //Keep displaying enter code form so the user can try again
                        session.setAttribute("showCodeInput", true);
                        session.setAttribute("message", "Something went wrong email could not be sent");
                    }
                }
                else {
                    //Keep displaying enter code form so the user can try again
                    session.setAttribute("showCodeInput", true);
                    session.setAttribute("errorMessage", "Pass Code is incorrect try again");
                }
                return forwardToJspPage;
            }
    }
    
}
