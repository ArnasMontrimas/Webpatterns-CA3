/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;
import Daos.UserDao;
import Dtos.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Samuel and Malo
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
        String forwardToJspPage = "login.jsp";

        if (fromIndex) return forwardToJspPage;

        // Login Data
        String email = request.getParameter("email");        
        String pass = request.getParameter("password");

        if(email != null && pass != null && !email.isEmpty() && !pass.isEmpty()) {
            User user = udao.validateLogin(email, pass);

            // Username and pass incorrect
            if (user == null) {
              session.setAttribute("errorMessage", "Username or password is incorrect");
            } else if (!user.isActiveAccount()){
              // Account is inactive
              session.setAttribute("errorMessage", "That account is no longer active");
            } else {
              session.setAttribute("user", user);
              forwardToJspPage = "loans.jsp";
            }
        } else {
            session.setAttribute("errorMessage", "Missing username or password");
        }
        
        return forwardToJspPage;
    }
    
}
