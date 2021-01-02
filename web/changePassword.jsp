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
      <h1><%= bundle.getString("change_pwd_change") %></h1>

      <% if (session.getAttribute("errorMessage") != null) { %>
        <div class="alert alert-danger" role="alert">
          ${sessionScope.errorMessage}
        </div>
      <%
        }
        session.removeAttribute("errorMessage");
      %>

      <form action="controller">
        <input type="hidden" name="action" value="changePassword">
        <input type="hidden" name="username" id="username" value="<%= user.getUsername() %>">

        <div>
          <label for="currentPassword" class="form-label mb-0"><%= bundle.getString("change_pwd_actual") %></label>
          <input type="password" class="form-control" id="currentPassword" name="currentPassword" placeholder="<%= bundle.getString("change_pwd_actual") %>" required>
        </div>

        <div class="mt-4" id="passwordContainer">
          <label for="password" class="form-label mb-0"><%= bundle.getString("password") %></label>

          <div class="d-flex flex-column mb-1">
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_length") %></small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_cases") %></small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_symbols") %></small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("register_invalid_username") %></small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("change_pwd_contain_actual") %></small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><%= bundle.getString("change_pwd_same_confirmation") %></small>
          </div>

          <input type="password" class="form-control" id="password" name="password" placeholder="<%= bundle.getString("password") %>" required>

          <div id="pwdStrengthMeter" data-levels-text="<%= bundle.getString("register_password_levels") %>">
            <div class="mt-2"><%= bundle.getString("register_password_meter_text") %> <span></span></div>
            <div>
              <div class="meter-level"></div>
              <div class="meter-level"></div>
              <div class="meter-level"></div>
              <div class="meter-level"></div>
            </div>
          </div>
        </div>

        <div class="mt-4">
          <label for="confirmPassword" class="form-label mb-0"><%= bundle.getString("change_pwd_confirm") %></label>
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="<%= bundle.getString("password") %>" required>
        </div>
      
        <div class="mt-4">
          <input id="registerSubmit" type="submit" class="btn btn-primary" value="<%= bundle.getString("change_pwd_change") %>" disabled />
          <a href="profile.jsp" class="btn btn-outline-danger"><%= bundle.getString("cancel") %>Cancel</a>
        </div>
      </form>
    </main>

    <script src="./js/zxcvbn.js"></script>
    <script src="./js/register.js"></script>
  </body>
</html>

