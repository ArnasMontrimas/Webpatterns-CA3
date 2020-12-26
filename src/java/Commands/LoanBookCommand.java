package Commands;

import java.util.ArrayList;

import Daos.BookDao;
import Daos.LoanDao;
import Dtos.Book;
import Dtos.Loan;
import Dtos.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Loaning a book
 * @author Malo
 */
public class LoanBookCommand implements Command {
  @Override
  public String doAction(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
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
        session.setAttribute("errorMessage", "You have to be logged in");
        forwardToJspPage = "login.jsp";
      } else if (book == null) {
        session.setAttribute("errorMessage", "This book doesn't exist");
      } else if (book.getQuantityInStock() == 0) {
        session.setAttribute("errorMessage", "This book isn't available anymore");
      } else {
        LoanDao loanDao = new LoanDao();

        // Check if user loans this book
        ArrayList<Book> loanedBooks = new ArrayList<>();
        ArrayList<Loan> loans = loanDao.getAllActiveUserLoans(user);

        for (Loan loan : loans) {
          loanedBooks.add(loan.getLoanBook());
        }

        if (loanedBooks.contains(book)) {
          session.setAttribute("errorMessage", "You already loan a copy of this book");
        } else {
          // Loan the book for the user
          loanDao.loanBook(bookId, 7, user.getUserID());
          session.setAttribute("message", "Successfully loaned " + "<b>" + book.getBookName() + "</b>");
        }
      }
    }

    return forwardToJspPage;
  }
}
