package Commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class NoActionSuppliedCommand implements Command{
    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response){
        
        String forwardToJsp = null;
        HttpSession session = request.getSession();
        String pathBack = null;
        
        //String referer = request.getHeader("Referer");
        //System.out.println(request.getContextPath());
           
        
        session.setAttribute("errorMessage","No valid action was supplied in this request");
        // For now
        session.setAttribute("PathBack","Register.jsp");
        forwardToJsp = "Error.jsp";
        return forwardToJsp;
    }
}
