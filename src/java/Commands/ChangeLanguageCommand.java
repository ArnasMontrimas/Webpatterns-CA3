/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author samue
 */
public class ChangeLanguageCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        
            HttpSession session = request.getSession();
            String forwardToJspPage = null;
            
            String language = request.getParameter("language");
            if(language != null){
                Locale currentLocale = new Locale(language);
                session.setAttribute("currentLocale", currentLocale);
            }
            try{
                String refererPage = new URI(request.getHeader("Referer")).getPath();
                String[] pathPieces = refererPage.split("/");
                forwardToJspPage = pathPieces[pathPieces.length-1];
            } catch (URISyntaxException ex){
                System.out.println("An error occured when trying to get the page that sent the client here: " + ex.getMessage());
                forwardToJspPage = "Register.jsp";
            }
            
            return forwardToJspPage;
    }
    
}
