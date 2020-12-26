<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Redirect if already logged in
    if (session.getAttribute("user") != null) {
        response.sendRedirect("index.jsp");
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

      <main class="container">
        <form class="mt-5 m-auto" style="max-width: 500px;">
          <input type="hidden" value="login">
          
          <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
          <label for="email" class="visually-hidden">Email address</label>
          <input type="email" id="email" name="email" class="form-control" placeholder="Email address" required autofocus>
          <label for="password" class="visually-hidden">Password</label>
          <input type="password" id="password" name="password" class="form-control mt-2" placeholder="Password" required>
          <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Sign in</button>
        </form>
      </main>
    </body>
</html>