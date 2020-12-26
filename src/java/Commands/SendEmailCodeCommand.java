/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.UserDao;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class SendEmailCodeCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
            String forwardToJspPage = "ForgotUsername.jsp";
            String email = request.getParameter("email");
            
            //Information contained in the emial
            String message = "Retrieve Username\nUse this pass code: ";
            String code = "";
            String subject = "RETRIEVE USERNAME PASS CODE";
            
            
            //Generate random number
            Random r = new Random();
            int low = 10;
            int high = 100;
            for(int i = 0; i < 5; i++) {
                int result = r.nextInt(high-low) + low;
                code += String.valueOf(result);
            }
            //------------------------------//
            
            //Build message
            message += code;
            
            // For now only this
            if (email != null && udao.validateEmail(email)) {
                //Make sure email sent successfully
                if(udao.sendEmailTo(email, message, subject)) {
                    //Store code in the session & recipients email address
                    session.setAttribute("RetrieveUsernamePassCode", code);
                    session.setAttribute("usersEmail", email);
                    
                    //Display enter code form
                    session.setAttribute("ShowCodeInput","true");
                }
                else session.setAttribute("Messsage", "Something went wrong email could not be sent");
                
            }
            else session.setAttribute("Message", "No Account found with this email");
            
            
            return forwardToJspPage;
    }
    
}
