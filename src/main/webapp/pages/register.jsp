<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register page</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/register" method="post">
    <input type="hidden" value="register" name="command">

    <label for="username">Enter username</label>
    <input type="text" id="username" placeholder="Username" name="username">

    <label for="email">Enter email</label>
    <input type="email" id="email" placeholder="Email" name="email">

    <label for="password">Enter password</label>
    <input type="password" id="password" placeholder="Password"
           name="password1">

    <label for="password-confirmation">Re-enter your password</label>
    <input type="password" id="password-confirmation"
           placeholder="Confirm your password"
           name="password2">

    <input type="submit" name="submit" value="log in"/>

    <p>${requestScope.error}</p>

</form>

</body>
</html>
