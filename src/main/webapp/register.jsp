
<!DOCTYPE html>
<html>
<head>
    <title>Customer Registration | Hotel Paradise</title>
    <link rel="stylesheet"
      href="<%= request.getContextPath() %>/css/style.css">
</head>
<body class="auth-page">

    <div class="auth-card register-card">

        <div class="auth-brand">
            <div class="brand-icon">H</div>
            <h1>HOTEL PARADISE</h1>
            <p>Your Comfort, Our Priority</p>
        </div>

        <div class="auth-heading">
            <h2>Create Your Account</h2>
            <p>Join us for a comfortable stay</p>
        </div>

        <form action="register" method="post">

            <label for="name">Full Name</label>
            <input type="text"
                   id="name"
                   name="name"
                   placeholder="Enter your full name"
                   required>

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
                   placeholder="Create a password"
                   required>

            <label for="phone">Phone Number</label>
            <input type="text"
                   id="phone"
                   name="phone"
                   placeholder="Enter your phone number"
                   required>

            <button type="submit" class="auth-button">
                Create Account
            </button>

        </form>

        <div class="auth-links">
            <p>Already have an account?
                <a href="login.jsp">Login here</a>
            </p>

            <a href="index.jsp" class="home-link">← Back to Home</a>
        </div>

    </div>

</body>
</html>
