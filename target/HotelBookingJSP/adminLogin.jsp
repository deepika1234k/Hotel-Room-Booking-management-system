
<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<header>
    <h1>Hotel Paradise</h1>
    <p>Administrator Login</p>
</header>

<div class="container">

    <h2>Admin Login</h2>

    <form action="adminLogin" method="post">

        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">Login</button>

    </form>

    <% if ("invalid".equals(request.getParameter("error"))) { %>
        <p style="color:red;">Invalid admin username or password!</p>
    <% } %>

</div>

</body>
</html>
