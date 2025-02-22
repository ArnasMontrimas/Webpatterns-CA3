/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.PasswordResetDao;
import daos.UserDao;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class ForgotPasswordRetrievalCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {            
        UserDao udao = new UserDao();
        PasswordResetDao prd = new PasswordResetDao();
        HttpSession session = request.getSession();
        
        Locale clientLocale = (Locale) session.getAttribute("currentLocale");
        ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);

        String forwardToJspPage = "resetPassword.jsp";

        String email = request.getParameter("email"); 

        int id = udao.getUserIdByEamil(email);
        int low = 0;
        int high = 0;
        int result = 0;

        //Information contained in the email
        String message = bundle.getString("forgotpwdcmd_message");
        String code = "";
        String subject = bundle.getString("forgotpwdcmd_subject");

        //Check that email is valid
        if(id == -1) {
            session.setAttribute("errorMessage", bundle.getString("forgotpwdcmd_email_notfound"));
            return forwardToJspPage;
        }

        //Generate random number + 1 Random Letter at a random position (Since we are retrieving a password this will make the code is much harder to guess)
        Random r = new Random();
        low = 10;
        high = 1000;
        for(int i = 0; i < 5; i++) {
            result = r.nextInt(high-low) + low;
            code += String.valueOf(result);
        }

        //Random Letter
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        high = 25;
        result = r.nextInt(high);
        char letter = alphabet[result];

        //Insert letter at a random position replacing a number with that letter
        high = code.length();
        result = r.nextInt(high);
        code = code.replaceFirst(String.valueOf(code.charAt(result)), String.valueOf(letter));
        
        //Build message
        message += code;
        
        LocalDateTime timeout;
        //Check if there is a timeout set if not just set timeout to now -1 day to allow attempts
        if(prd.getTimeout(id) == null) timeout = LocalDateTime.now().minusDays(1);
        else timeout = prd.getTimeout(id);
        
        //If there is a timeout from step 2 of password reset making user wait at this stage
        if(LocalDateTime.now().isAfter(timeout)) {
            //Send email and if incase email could not be sent for some reason
            if(udao.sendEmailTo(email, message, subject)) {
                //Set a session variable which will decide weather to show form to input code & Store code in the session & Users Email Address
                session.setAttribute("inputCode", code);
                session.setAttribute("userEmail", email);
                session.setAttribute("userId", id);
                session.setAttribute("showCodeInput", true);
                
                //Remove user attempts as going from stage 1 to stage to will be counted as an attempt
                prd.removeUserAttempt(id);
            }
            else {
                session.setAttribute("errorMessage", bundle.getString("forgotpwdcmd_notsent"));
                return forwardToJspPage;
            }
        }
        else {
            //Let the user know how long he has to wait
            Duration duration = Duration.between(LocalDateTime.now(), timeout);
            session.setAttribute("errorMessage", bundle.getString("forgotpwdcmd_toomany_1") + " " +duration.toMinutes()+ " " + bundle.getString("forgotpwdcmd_toomany_2"));
        }
        return forwardToJspPage;
        
    }
    
}
