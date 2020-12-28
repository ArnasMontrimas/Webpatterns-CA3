<%@ page import="Dtos.*" %>
<%@ page import="Daos.*" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if not logged in
    User user = (User)session.getAttribute("user");
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

      <main class="container pt-3 pb-5">
        <h1>Profile Page</h1>
        
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
                    <strong>Type:&nbsp;</strong><span class="text-capitalize"><%= user.getType() %></span>
                </p>

                <div>
                  <div>
                    <a href="editProfile.jsp" class="btn btn-primary">Edit Profile</a>
                    <a href="changePassword.jsp" class="btn btn-warning">Change Password</a>
                  </div>
                  <div class="mt-3">
                    <a href="previousLoans.jsp" class="btn btn-outline-primary">Loan History</a>
                    <a href="loans.jsp" class="btn btn-outline-primary">Current Loans</a>
                  </div>
                </div>
            </div>
        </div>
      </main>
    </body>
</html>

