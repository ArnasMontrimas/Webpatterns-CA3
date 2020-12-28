<%@ page import="Dtos.*" %>
<%@ page import="Daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if not logged in
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Online Library</title>
        <%@include file="head.jsp" %>
    </head>
    <body>
      <%@include file="navbar.jsp" %>

      <main class="container pt-3 pb-5" id="booksPage">
        <div class="row justify-content-between mb-3">
          <h1 class="col-sm-4">Books</h1>
          
          <form action="controller" method="post" class="d-flex col-sm-8 col-lg-5 mb-2 mb-sm-0">
            <input type="hidden" name="action" value="searchBook">
            <input class="form-control me-2" type="search" name="query" placeholder="Search for books" aria-label="Search" required>
            <button class="btn btn-secondary" type="submit">Search</button>
          </form>
        </div>
        
        <% if (session.getAttribute("errorMessage") != null) { %>
          <div class="alert alert-danger" role="alert">
            ${sessionScope.errorMessage}
          </div>
        <%
          }
          session.removeAttribute("errorMessage");
        %>
        <% if (session.getAttribute("message") != null) { %>
          <div class="alert alert-success" role="alert">
            ${sessionScope.message}
          </div>
        <%
          }
          session.removeAttribute("message");
        %>

        <div class="row justify-content-around">
          <%
            LoanDao loanDao = new LoanDao();

            ArrayList<Book> books = (new BookDao()).getAllBooks();
            for (Book book : books) {
              if (loanDao.checkIfLoaned(user.getUserID(), book.getBookID())) continue;
          %> 
            <div class="card mx-2 mb-5" style="width: 18rem;">
              <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
              <div class="card-body">
                <h5 class="card-title mb-0"><%= book.getBookName() %></h5>
                <h6><%= book.getAuthor() %></h6>
                <p class="card-text"><%= book.getBookDescription() %></p>
                <% if (book.getQuantityInStock() > 0) { %>
                  <a href="controller?action=loan&bookId=<%= book.getBookID() %>" class="btn btn-primary">Loan book</a>
                <% } else { %>
                  <button class="btn btn-outline-primary" disabled aria-disabled="true">Unavailable</button>
                <% } %>
              </div>
            </div>
          <%
            }
          %>
        </div>
      </main>
    </body>
</html>