<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

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
        <% if(session.getAttribute("showCodeInput") == Boolean.FALSE || session.getAttribute("showCodeInput") == null) { %>
          <form class="mt-5 m-auto" style="max-width: 500px;" action="controller" method="post">
                <h1 class="h3 mb-3 fw-normal">Password Reset (Email)</h1>
                <input type="hidden" name="action" value="forgotPasswordRetrieval">
                <label for="email" class="visually-hidden">Email address</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="Email address" required autofocus>
                <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Next</button>
          </form>
        <% } else {%>
            <form class="mt-5 m-auto" style="max-width: 500px;" action="controller" method="post">
                <h1 class="h3 mb-3 fw-normal">Password Reset (Code)</h1>
                <input type="hidden" name="action" value="validateEmailCodeSendPassword">
                <label for="code" class="visually-hidden">Code</label>
                <input type="text" id="code" name="code" class="form-control" placeholder="code" required autofocus>
                <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">Retrieve</button>
            </form>
        <% } %>
        <% session.removeAttribute("showCodeInput"); %>
        </div>
      </main>
    </body>
</html>
