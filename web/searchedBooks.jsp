<%@ page import="Dtos.*" %>
<%@ page import="Daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if not logged in
    if (session.getAttribute("user") == null) {
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
            
            if(session.getAttribute("books") != null) {
                ArrayList<Book> books = (ArrayList<Book>)session.getAttribute("books");
                for (Book book : books) {
          %> 
                    <div class="card mx-2" style="width: 18rem;">
                      <img src="./images/books/<%= book.getImagePath() %>" class="card-img-top w-100" alt="<%= book.getBookName() %>">
                      <div class="card-body">
                        <h5 class="card-title mb-0"><%= book.getBookName() %></h5>
                        <h6><%= book.getAuthor() %></h6>
                        <p class="card-text"><%= book.getBookDescription() %></p>
                        <% if (book.getQuantityInStock() > 0) { %>
                          <a href="controller?action=returnLoan&bookId=<%= book.getBookID() %>" class="btn btn-primary">Return book</a>
                        <% } else { %>
                          <button class="btn btn-outline-primary" disabled aria-disabled="true">Unavailable</button>
                        <% } %>
                      </div>
                    </div>
                <%
                  }
                %>
          <%
            } else {
          %>
            <h1>No books found</h1>
          <%
            }
          %>
        </div>
      </main>
    </body>
</html>
