/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import daos.BookDao;
import dtos.Book;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Arnas
 */
public class SearchBookCommand implements Command {

    @Override
    public String doAction(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        BookDao bookDao = new BookDao();
        String query = request.getParameter("query");
        
        if(query == null) {
            session.setAttribute("books", null);
        }
        else {
            ArrayList<Book> books = bookDao.searchBooks(query);
            if(books.isEmpty()) books = null;
            session.setAttribute("books", books);
            session.setAttribute("query", query);
        }
        
        return "books.jsp";
    }
    
}
