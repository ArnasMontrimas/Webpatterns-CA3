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

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        UserDao udao = new UserDao();
        HttpSession session = request.getSession();
        String forwardToJspPage = "login.jsp";

        // Login Data
        String user = request.getParameter("email");        
        String pass = request.getParameter("password");

        if(user != null && pass != null && !user.isEmpty() && !pass.isEmpty()) {
            int userID = udao.validateLogin(user,pass);
            // Username and pass incorrect
            switch (userID) {
                case 0:
                    session.setAttribute("Message","Username or password is incorrect !");
                    break;
                case -1:
                    session.setAttribute("Message","That account is no longer active.");
                    break;
                default:
                    User u = udao.getUserByID(userID);
                    session.setAttribute("user",u);
                    forwardToJspPage = "index.jsp";
                    break;
            }
        } else {
            session.setAttribute("BadLogin","Missing data supplied for the fields !");
        }
        
        return forwardToJspPage;
    }
    
}
