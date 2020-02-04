<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/login" method="post">
    <input type="hidden" value="login" name="command">

    <input type="email" id="email" placeholder=Email name="email">
    <input type="password" id="password" placeholder="Password" name="password">

    <input class="btn btn-primary" type="submit" name="submit" value="Log in"/>
    <p>${requestScope.error}</p>
</form>

</body>
</html>