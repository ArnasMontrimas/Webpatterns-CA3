/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.BookDao;
import daos.OpinionsDao;
import daos.UserDao;
import dtos.User;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author grallm
 */
public class RateBookCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
        
            Locale clientLocale = (Locale) session.getAttribute("currentLocale");
            ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);
    
            String forwardToJspPage = "previousLoans.jsp";

            User user = (User) session.getAttribute("user");
            
            // Get relevant information from request
            String bookIdStr = request.getParameter("bookId");
            String ratingStr = request.getParameter("rating");
            int bookId = 0;
            int rating = -1;
            try {
              bookId = Integer.parseInt(bookIdStr);
              rating = Integer.parseInt(ratingStr);
            } catch (Exception e) {}
            String comment = request.getParameter("comment");
            
            if(bookId > 0 && rating > -1){
              OpinionsDao oDao = new OpinionsDao();
              BookDao bDao = new BookDao();

              if (oDao.checkIfUserHasOpinion(user.getUserID(), bookId) != null) {
                session.setAttribute("errorMessage", bundle.getString("rate_already"));
              } else {
                // Try adding opinion
                if (oDao.addOpinion(user.getUserID(), bookId, rating, comment)) {
                  session.setAttribute("message", bundle.getString("rate_success") + " <strong>" + bDao.getBookByID(bookId).getBookName() + "</strong>.");
                }else{
                  session.setAttribute("errorMessage", bundle.getString("general_error"));
                }
              }
            } else {
                session.setAttribute("errorMessage", bundle.getString("missing_params"));
            }
         return forwardToJspPage;
    }
    
}
