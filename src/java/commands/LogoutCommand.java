package commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command{
    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response){
        String forwardToJsp = "login.jsp";
        HttpSession session = request.getSession();
        // To log a user out, wipe their session clear
        session.invalidate();
        return forwardToJsp;
    }
    
}
