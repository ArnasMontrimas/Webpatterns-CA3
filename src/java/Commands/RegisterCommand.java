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
import static Daos.Dao.*;

/**
 *
 * @author samue
 */
public class RegisterCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        
        UserDao udao = new UserDao();
        HttpSession session = request.getSession();
        String forwardToJspPage = "register.jsp";
        
        // Get info for the users table.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Get info for the Security Answers table.
        String schoolAnswer = request.getParameter("schoolSecurityQuestion");
        String foodAnswer = request.getParameter("foodSecurityQuestion");
        String placeAnswer = request.getParameter("placeSecurityQuestion");

        // Get info for the Payment Details table.
        String cardNumber = request.getParameter("cardNumber");
        String cardCvv = request.getParameter("cardCvv");
        String ownerName = request.getParameter("ownerName");
        String expirationDate = request.getParameter("expirationDate");

          if(
              username != null && password != null &&  email != null &&
              schoolAnswer != null && foodAnswer != null && placeAnswer != null &&
              !username.isEmpty() && !password.isEmpty() && !email.isEmpty() &&
              !schoolAnswer.isEmpty() && !foodAnswer.isEmpty() && !placeAnswer.isEmpty() 
              && cardNumber != null && ownerName != null && expirationDate != null && cardCvv != null  &&
                !cardNumber.isEmpty() && !ownerName.isEmpty() && !expirationDate.isEmpty() && !cardCvv.isEmpty()
            ){
              boolean lengthCheck = password.length() >= 12;
              boolean lowerCheck = password.matches(".*[a-z].*");
              boolean upperCheck = password.matches("^[^A-Z].*[A-Z].*");
              boolean numberCheck = password.matches(".*[0-9].*[^0-9]$");
              boolean symbolCheck = password.matches(".*[\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s].*[^\\!\"#$%&'()*+,-./:;<=>?@\\Q[]|\\E^_`{}~\\s]$");
              boolean notUserName = !password.toLowerCase().contains(username.toLowerCase());

              // Check if password conditions aren't respected
              if (!lengthCheck || !lowerCheck || !upperCheck || !numberCheck || !symbolCheck || !notUserName) {
                session.setAttribute("errorMessage", "Password conditions aren't respected");
                return forwardToJspPage;
              }

              // Check if card number and CVV are valid
              boolean cardNumberCheck = cardNumber.matches("[0-9]{16}");
              boolean cardCvvCheck = cardCvv.matches("[0-9]{3,4}");
              if (!cardNumberCheck || !cardCvvCheck) {
                  session.setAttribute("errorMessage", "Card number or Card security code are incorrect");
                  return forwardToJspPage;
              }

              // If email is unique and not taken
              if (!udao.validateEmail(email)) {

              int result = udao.registerUser(username,email,password);

              // If the user has been sucessfully registered
              if (result != 0) {
                  // Insert payment details
                  PaymentDetailsDao pdao = new PaymentDetailsDao();
                  pdao.insertPaymentDetails(result, cardNumber, cardCvv, ownerName, expirationDate);

                  forwardToJspPage = "Login.jsp";
                  session.setAttribute("Message","You have been registered please login !");
              } else
              {  // Perhaps some internal problem with server at that time just in case.
                  session.setAttribute("errorMessage","Could not be registered at this time please try again later.");
              }
              } else {
                 session.setAttribute("errorMessage","Email already in use !");
              }
          } else{
                  session.setAttribute("errorMessage","Missing data supplied for the fields !");
              }
        
        return forwardToJspPage;
    }
    
}
