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
        <h1>Profile Page</h1>
        

        <div class="row justify-content-around">
            <% 
                User user = (User)session.getAttribute("user");
            %>
        
            <div>
                <p>
                    <strong>Email:&nbsp;</strong><%= user.getEmail() %>
                </p>
                <p>
                    <strong>Username:&nbsp;</strong><%= user.getUsername() %>
                </p>
                <p>
                    <strong>Registered:&nbsp;</strong><%= user.getDateRegistered() %>
                </p>
                <p>
                    <strong>Type:&nbsp;</strong><%= user.getType() %>
                </p>
                <a href="previousLoans.jsp" class="btn btn-outline-primary">Loan History</a>
                <a href="loans.jsp" class="btn btn-outline-primary">Current Loans</a>
                <a href="#" class="btn btn-outline-primary">Edit Profile</a>
            </div>
        </div>
      </main>
    </body>
</html>

