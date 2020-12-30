/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

import Daos.BookDao;
import Daos.OpinionsDao;
import Daos.UserDao;
import Dtos.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Malo
 */
public class RateBookCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
            
            UserDao udao = new UserDao();
            HttpSession session = request.getSession();
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

              // Try updating username
              if (oDao.addOpinion(user.getUserID(), bookId, rating, comment)) {
                session.setAttribute("message", "Successfully shared your opinion for <strong>" + bDao.getBookByID(bookId).getBookName() + "</strong>.");
              }else{
                session.setAttribute("errorMessage", "An error occurred, try again later");
              }
            } else {
                session.setAttribute("errorMessage", "All fields are not filled with data");
            }
         return forwardToJspPage;
    }
    
}
