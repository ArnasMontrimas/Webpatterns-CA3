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
      <h1>Change Password</h1>

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
          <label for="currentPassword" class="form-label mb-0">Actual Password</label>
          <input type="password" class="form-control" id="currentPassword" name="currentPassword" placeholder="Actual password" required>
        </div>

        <div class="mt-4" id="passwordContainer">
          <label for="password" class="form-label mb-0">Password</label>

          <div class="d-flex flex-column mb-1">
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> must be at least 12 characters long</small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> must contain both upper and lowercase letters (First letter can't be capitalized)</small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> must have at least one number and one symbol (NOT at end)</small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> can't contain your username</small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> can't contain last password</small>
            <small class="text-success text-danger"><i class="fas fa-times me-1"></i><i class="fas fa-check me-1"></i><b>Password</b> has to be the same than confirmation</small>
          </div>

          <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>

          <div id="pwdStrengthMeter">
            <div class="mt-2">Your password is <span>Invalid</span></div>
            <div>
              <div class="meter-level"></div>
              <div class="meter-level"></div>
              <div class="meter-level"></div>
              <div class="meter-level"></div>
            </div>
          </div>
        </div>

        <div class="mt-4">
          <label for="confirmPassword" class="form-label mb-0">Confirm Password</label>
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Password" required>
        </div>
      
        <div class="mt-4">
          <input id="registerSubmit" type="submit" class="btn btn-primary" value="Change password" disabled />
          <a href="profile.jsp" class="btn btn-outline-danger">Cancel</a>
        </div>
      </form>
    </main>

    <script src="./js/zxcvbn.js"></script>
    <script src="./js/register.js"></script>
  </body>
</html>

