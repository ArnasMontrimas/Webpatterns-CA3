<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#"><%= bundle.getString("navbar_title") %></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <% if (session.getAttribute("user") != null) { %>
          <!-- Logged in -->
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#"><%= bundle.getString("home") %></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="books.jsp"><%= bundle.getString("books") %></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="loans.jsp"><%= bundle.getString("loans") %></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="previousLoans.jsp"><%= bundle.getString("loans_history") %></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="profile.jsp"><%= bundle.getString("profile") %></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="controller?action=logout"><%= bundle.getString("logout") %></a>
          </li>
        <% } else { %>
          <!-- Logged out -->
          <li class="nav-item">
            <a class="nav-link" href="login.jsp"><%= bundle.getString("login") %></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="register.jsp"><%= bundle.getString("register") %></a>
          </li>
        <% } %>
      </ul>
      <% if (session.getAttribute("user") != null) { %>
      <!-- Only Logged in Users can search for books -->
      <form action="controller" method="post" class="d-flex">
          <input type="hidden" name="action" value="searchBook">
        <input class="form-control me-2" type="search" name="query" placeholder="<%= bundle.getString("search_placeholder") %>" aria-label="<%= bundle.getString("search") %>" required>
        <button class="btn btn-secondary" type="submit"><%= bundle.getString("search") %></button>
      </form>
      <% } %>
    </div>
  </div>
</nav>