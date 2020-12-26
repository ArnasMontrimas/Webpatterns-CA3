<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Library Online</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <% if (session.getAttribute("user") != null) { %>
          <!-- Logged in -->
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="books.jsp">Books</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Loans</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Profile</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Logout</a>
          </li>
        <% } else { %>
          <!-- Logged out -->
          <li class="nav-item">
            <a class="nav-link" href="login.jsp">Log in</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="register.jsp">Register</a>
          </li>
        <% } %>
      </ul>

      <form class="d-flex">
        <input class="form-control me-2" type="search" placeholder="Search for books" aria-label="Search">
        <button class="btn btn-outline-success" type="submit">Search</button>
      </form>
    </div>
  </div>
</nav>