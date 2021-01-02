/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.UserDao;
import dtos.User;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author arnas
 * @author grallm 
 */
public class LoginCommand implements Command {
    private boolean fromIndex = false;

    public LoginCommand() {
    }

    public LoginCommand(boolean fromIndex) {
      this.fromIndex = fromIndex;
    }

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        UserDao udao = new UserDao();
        HttpSession session = request.getSession();
        
        Locale clientLocale = (Locale) session.getAttribute("currentLocale");
        if(clientLocale == null) clientLocale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);

        String forwardToJspPage = "login.jsp";

        if (fromIndex) return forwardToJspPage;

        // Login Data
        String email = request.getParameter("email");        
        String pass = request.getParameter("password");

        if(email != null && pass != null && !email.isEmpty() && !pass.isEmpty()) {
            User user = udao.validateLogin(email, pass);

            // Username and pass incorrect
            if (user == null) {
              session.setAttribute("errorMessage", bundle.getString("login_credentials_incorrect"));
            } else if (!user.isActiveAccount()){
              // Account is inactive
              session.setAttribute("errorMessage", bundle.getString("login_inactive"));
            } else {
              session.setAttribute("user", user);
              forwardToJspPage = "loans.jsp";
            }
        } else {
            session.setAttribute("errorMessage", bundle.getString("missing_params"));
        }
        
        return forwardToJspPage;
    }
    
}
