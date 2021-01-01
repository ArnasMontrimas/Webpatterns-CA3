<%@ page import="dtos.*" %>
<%@ page import="daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if not logged in
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String query = (String) session.getAttribute("query");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp" %>
    </head>
    <body>
      <%@include file="navbar.jsp" %>

      <main class="container pt-3 pb-5">
        <div class="row justify-content-between mb-3">
          <h1 class="col-sm-4"><%= bundle.getString("books") %></h1>
          
          <form action="controller" method="post" class="d-flex col-sm-8 col-lg-5 mb-2 mb-sm-0">
            <input type="hidden" name="action" value="searchBook">
            <input class="form-control me-2" type="search" name="query" placeholder="<%= bundle.getString("search_placeholder") %>" aria-label="<%= bundle.getString("search") %>" required>
            <button class="btn btn-secondary" type="submit"><%= bundle.getString("search") %></button>
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

          if (query != null) {
        %>
          <h4 class="text-black-50"><%= bundle.getString("books_query_text") %> "<span class="text-dark"><%= query %></span>"</h4>
        <% } %>

        <div class="row justify-content-around">
          <%
            LoanDao loanDao = new LoanDao();

            // List of books corresponding to research if searched
            ArrayList<Book> books = query == null ? (new BookDao()).getAllBooks() : (ArrayList<Book>) session.getAttribute("books");

            if(books != null) {
              OpinionsDao oDao = new OpinionsDao();
              UserDao uDao = new UserDao();
              for (Book book : books) {
                ArrayList<Opinion> opinions = oDao.getBookOpinions(book.getBookID());
          %> 
            <div class="card mx-2 mb-5 px-0" style="width: 18rem;">
              <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
              <div class="card-body d-flex flex-column justify-content-between">
                <div class="mb-3">
                  <h5 class="card-title mb-0"><%= book.getBookName() %></h5>
                  <h6><%= book.getAuthor() %></h6>
                  <p class="card-text"><%= book.getBookDescription() %></p>
                </div>

                <%
                if (opinions.size() > 0) {
                %>
                  <div class="modal fade" id="bookOpinionsModal<%= book.getBookID() %>" tabindex="-1" aria-labelledby="bookOpinionsModalLabel<%= book.getBookID() %>" aria-hidden="true">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <div class="modal-header">
                          <h5 class="modal-title" id="bookOpinionsModalLabel<%= book.getBookID() %>"><%= bundle.getString("books_opinions_title") %> <strong><%= book.getBookName() %></strong></h5>
                          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="<%= bundle.getString("close") %>"></button>
                        </div>
                        <div class="modal-body">
                <%
                  int sumRatings = 0;
                          for (Opinion opinion : opinions) {
                            sumRatings += opinion.getRating();
                          %>
                            <div>
                              <div class="d-inline-block fw-bold"><%= uDao.getUserById(opinion.getUserId()).getUsername() %></div>
                              <div class="starrr rated" data-rating="<%= opinion.getRating() %>"></div>
                              <p><%= opinion.getComment() %></p>
                            </div>
                          <% } %>
                            
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal"><%= bundle.getString("close") %></button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="mb-3">
                    <strong><%= bundle.getString("books_average_rating") %></strong><div class="starrr rated" data-rating="<%= sumRatings / opinions.size() %>"></div>
                    <button type="button" class="btn btn-primary mt-1 w-100" data-bs-toggle="modal" data-bs-target="#bookOpinionsModal<%= book.getBookID() %>"><%= bundle.getString("books_all_opinions") %> (<%= opinions.size() %>)</button>
                  </div>
                <%
                }
                if (loanDao.checkIfLoaned(user.getUserID(), book.getBookID())) {
                %>
                  <!-- Book is loaned -->
                  <a href="loans.jsp" class="btn btn-outline-warning"><%= bundle.getString("books_view_loans") %></a>
                <% } else if (book.getQuantityInStock() > 0) { %>
                  <a href="controller?action=loan&bookId=<%= book.getBookID() %>" class="btn btn-primary"><%= bundle.getString("books_loan_book") %></a>
                <% } else { %>
                  <button class="btn btn-outline-secondary" disabled aria-disabled="true"><%= bundle.getString("books_unavailable") %></button>
                <% } %>
              </div>
            </div>
          <%
              }
            } else if (query != null && books == null) {
              // Searched and found nothing
          %>
            <div class="d-flex flex-column align-items-center">
              <h4 class="text-black-50"><%= bundle.getString("books_no_found") %></h4>
              <a href="books.jsp" class="btn btn-outline-primary"><%= bundle.getString("books_see_all") %></a>
            </div>
          <%
            } else {
          %>
            <div class="d-flex flex-column align-items-center">
              <h4 class="text-black-50"><%= bundle.getString("books_no_books") %></h4>
            </div>
          <%
            }
            
            session.removeAttribute("query");
            session.removeAttribute("books");
          %>
        </div>
      </main>

      <script src="./js/starrr.js"></script>
      <script src="./js/previousLoans.js"></script>
    </body>
</html>