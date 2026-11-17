
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Login | Hotel Paradise</title>
    <link rel="stylesheet"
      href="<%= request.getContextPath() %>/css/style.css">
</head>
<body class="auth-page">

    <div class="auth-card">

        <div class="auth-brand">
            <div class="brand-icon">H</div>
            <h1>HOTEL PARADISE</h1>
            <p>Your Comfort, Our Priority</p>
        </div>

        <div class="auth-heading">
            <h2>Welcome Back!</h2>
            <p>Login to continue your journey</p>
        </div>

        <% if ("invalid".equals(request.getParameter("error"))) { %>
            <p class="message error-message">
                Invalid email or password! Please try again.
            </p>
        <% } %>

        <% if ("registered".equals(request.getParameter("success"))) { %>
            <p class="message success-message">
                Registration successful! Please login.
            </p>
        <% } %>

        <form action="login" method="post">

            <label for="email">Email Address</label>
            <input type="email"
                   id="email"
                   name="email"
                   placeholder="Enter your email"
                   required>

            <label for="password">Password</label>
            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Enter your password"
                   required>

            <button type="submit" class="auth-button">
                Login to Account
            </button>

        </form>

        <div class="auth-links">
            <p>Don't have an account?
                <a href="register.jsp">Create Account</a>
            </p>

            <a href="index.jsp" class="home-link">← Back to Home</a>
        </div>

    </div>

</body>
</html>
