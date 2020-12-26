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
public class ValidateEmailCodeCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
            String forwardToJspPage = "ForgotUsername.jsp";
            String code = request.getParameter("code");
            
            //Get code which was sent & recipients email address
            String sentCode = session.getAttribute("RetrieveUsernamePassCode").toString();
            String email = session.getAttribute("usersEmail").toString();
            
            //Information contained in the emial
            String message = "Your Username:\n";
            String subject = "USERNAME RETRIEVED";

            // For now only this
            if(code != null && sentCode.equals(code)) {
                    //Make sure email sent successfully
                    if(udao.sendEmailTo(email, message, subject)) {
                        //Everything checks out so we remove these session variables
                        session.removeAttribute("RetrieveUsernamePassCode");
                        session.removeAttribute("usersEmail");
                        
                        //Return user back to login page and let them know email was sent successfully
                        session.setAttribute("Message", "Username sent to email successfully");
                        forwardToJspPage = "Login.jsp";
                    }
                    else {
                        //Keep displaying enter code form so the user can try again
                        session.setAttribute("ShowCodeInput","true");
                        session.setAttribute("Messsage", "Something went wrong email could not be sent");
                    }
            }
            else {
                //Keep displaying enter code form so the user can try again
                session.setAttribute("ShowCodeInput","true");
                session.setAttribute("Message", "Pass Code is incorrect try again");
            }
            return forwardToJspPage;
    }
    
}
