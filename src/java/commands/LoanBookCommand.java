package commands;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import daos.BookDao;
import daos.LoanDao;
import dtos.Book;
import dtos.Loan;
import dtos.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Loaning a book
 * @author grallm
 */
public class LoanBookCommand implements Command {
  @Override
  public String doAction(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
        
    Locale clientLocale = (Locale) session.getAttribute("currentLocale");
    ResourceBundle bundle = ResourceBundle.getBundle("languages.libraryTranslation", clientLocale);

    String forwardToJspPage = "books.jsp";

    String bookIdStr = request.getParameter("bookId");
    int bookId = 0;
    try {
      bookId = Integer.parseInt(bookIdStr);
    } catch (Exception e) {}

    if (bookId > 0) {
      BookDao bookDao = new BookDao();
      User user = (User) session.getAttribute("user");

      Book book = bookDao.getBookByID(bookId);

      if (user == null) {
        session.setAttribute("errorMessage", bundle.getString("loancmd_login"));
        forwardToJspPage = "login.jsp";
      } else if (book == null) {
        session.setAttribute("errorMessage", bundle.getString("loancmd_book_inexistant"));
      } else if (book.getQuantityInStock() == 0) {
        session.setAttribute("errorMessage", bundle.getString("loancmd_book_unavailable"));
      } else {
        LoanDao loanDao = new LoanDao();

        // Check if user loans this book
        if (loanDao.checkIfLoaned(user.getUserID(), bookId)) {
          session.setAttribute("errorMessage", bundle.getString("loancmd_book_already"));
        } else {
          loanDao.loanBook(bookId, 7, user.getUserID());
          //Update book quantity after loaning book
          bookDao.updateBookQuantity(bookId, 1, false);
          session.setAttribute("message", bundle.getString("loancmd_success") + " <b>" + book.getBookName() + "</b>");
        }
      }
    }

    return forwardToJspPage;
  }
}
