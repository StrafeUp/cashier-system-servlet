<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="view"/>

<head>
    <title>Register page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/register.css">
</head>
<body>
<div class="container">
    <div class="row align-items-center vertical-center">
        <div class="col-3"></div>
        <div class="col-6 rounded shadow bg-white">
            <div class="index-block justify-content-center">
                <div class="blankDiv"></div>
                <form action="${pageContext.request.contextPath}/user/register" method="post">
                    <div class="form-group">
                        <label for="username"><fmt:message key="register.username"/></label>
                        <input class="form-control" type="text" id="username" name="username">
                        <small class="form-text text-muted"><fmt:message key="register.username.info"/></small>
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key="register.email"/></label>
                        <input class="form-control" type="email" id="email" name="email">
                        <small class="form-text text-muted"><fmt:message key="register.email.info"/></small>
                    </div>
                    <div class="form-group">
                        <label for="password"><fmt:message key="register.password1"/> </label>
                        <input class="form-control" type="password" id="password"
                               name="password1">
                        <small class="form-text text-muted"><fmt:message key="register.password1.info"/></small>
                    </div>
                    <div class="form-group">
                        <label for="password-confirmation"><fmt:message key="register.password2"/></label>
                        <input class="form-control" type="password" id="password-confirmation"
                               name="password2">
                        <small class="form-text text-muted"><fmt:message key="register.password2.info"/></small>
                    </div>

                    <p>${requestScope.error}</p>

                    <input class="btn btn-primary" type="submit" value="Log in"/>
                </form>
            </div>
        </div>
        <div class="col-3"></div>
    </div>
</div>


</body>
</html>
