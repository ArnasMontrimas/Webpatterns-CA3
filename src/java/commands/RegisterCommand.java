/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.PaymentDetailsDao;
import daos.UserDao;
import dtos.User;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 * @author grallm
 */
public class RegisterCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        
        UserDao udao = new UserDao();
        HttpSession session = request.getSession();
        
        Locale clientLocale = (Locale) session.getAttribute("currentLocale");
        ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
        
        String forwardToJspPage = "register.jsp";
        
        // Get info for the users table.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Get info for the Payment Details table.
        String cardNumber = request.getParameter("cardNumber");
        String cardCvv = request.getParameter("cardCvv");
        String ownerName = request.getParameter("ownerName");
        String expirationDate = request.getParameter("expirationDate");

        if(
            username != null && password != null &&  email != null &&
            !username.isEmpty() && !password.isEmpty() && !email.isEmpty() &&
            cardNumber != null && ownerName != null && expirationDate != null && cardCvv != null  &&
            !cardNumber.isEmpty() && !ownerName.isEmpty() && !expirationDate.isEmpty() && !cardCvv.isEmpty()
          ){
            boolean lengthCheck = password.length() >= 12;
            boolean lowerCheck = password.matches(".*[a-z].*");
            boolean upperCheck = password.matches("^[^A-Z].*[A-Z].*");
            boolean numberCheck = password.matches(".*[0-9].*[^0-9]$");
            boolean symbolCheck = password.matches(".*[\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s].*[^\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s]$");
            boolean notUserName = !password.toLowerCase().contains(username.toLowerCase());
            //TODO NEED VALIDATION ON THE BACKEND FOR EXPIRATION DATE (Validation in HTML can be easiliy bypassed)
            // Check if password conditions aren't respected
            if (!lengthCheck || !lowerCheck || !upperCheck || !numberCheck || !symbolCheck || !notUserName) {
              session.setAttribute("errorMessage", bundle.getString("password_conditions_fail"));
              return forwardToJspPage;
            }

            // Check if card number and CVV are valid
            boolean cardNumberCheck = cardNumber.matches("[0-9]{16}");
            boolean cardCvvCheck = cardCvv.matches("[0-9]{3,4}");
            if (!cardNumberCheck || !cardCvvCheck) {
                session.setAttribute("errorMessage", bundle.getString("cvv_incorrect"));
                return forwardToJspPage;
            }

            // If email is unique and not taken
            if (!udao.validateEmail(email)) {
              User user = udao.registerUser(username,email,password);

              // If the user has been sucessfully registered
              if (user != null) {
                  // Insert payment details
                  PaymentDetailsDao pdao = new PaymentDetailsDao();
                  pdao.insertPaymentDetails(user.getUserID(), cardNumber, cardCvv, ownerName, expirationDate);

                  // Log in user
                  session.setAttribute("user", user);

                  forwardToJspPage = "loans.jsp";
              } else {  
                session.setAttribute("errorMessage",bundle.getString("register_fail"));
              }
            } else {
                session.setAttribute("errorMessage",bundle.getString("register_email_used"));
            }
        } else{
            session.setAttribute("errorMessage", bundle.getString("missing_params"));
        }
        
        return forwardToJspPage;
    }
    
}
