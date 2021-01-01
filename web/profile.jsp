<%@ page import="dtos.*" %>
<%@ page import="daos.*" %>
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
        <%@include file="head.jsp" %>
    </head>
    <body>
      <%@include file="navbar.jsp" %>

      <main class="container pt-3 pb-5">
        <h1><%= bundle.getString("profile_title") %></h1>
        
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
                    <strong><%= bundle.getString("email") %>:&nbsp;</strong><%= user.getEmail() %>
                </p>
                <p>
                    <strong><%= bundle.getString("username") %>:&nbsp;</strong><%= user.getUsername() %>
                </p>
                <p>
                    <strong><%= bundle.getString("profile_registered") %>:&nbsp;</strong><%= user.getDateRegistered() %>
                </p>
                <p>
                    <strong><%= bundle.getString("profile_type") %>:&nbsp;</strong><span class="text-capitalize"><%= user.getType() %></span>
                </p>
                <p>
                  <%= bundle.getString("profile_saved_cards") %>
                </p>

                <div>
                  <div>
                    <a href="editProfile.jsp" class="btn btn-primary"><%= bundle.getString("edit_profile_title") %></a>
                    <a href="changePassword.jsp" class="btn btn-warning"><%= bundle.getString("change_pwd_title") %></a>
                  </div>
                  <div class="mt-3">
                    <a href="previousLoans.jsp" class="btn btn-outline-primary"><%= bundle.getString("ploans_title") %></a>
                    <a href="loans.jsp" class="btn btn-outline-primary"><%= bundle.getString("loans_title") %></a>
                  </div>
                </div>
            </div>
        </div>
      </main>
    </body>
</html>

