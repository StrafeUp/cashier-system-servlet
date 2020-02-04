<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>


<html>
<head>
    <title>Index page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>

<body>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="view"/>
<fmt:requestEncoding value="UTF-8"/>


<div>
    <a class="btn btn-primary" href="pages/login.jsp"><fmt:message key="login"/></a>
    <%--<form action="user" method="get">
        <input type="hidden" value="listAllUsers" name="command">
        <input class="btn btn-primary" type="submit" name="submit" value="ListAllUsers"/>
    </form>--%>
    <a class="btn btn-primary" href="<c:url value="pages/register.jsp"/>"><fmt:message key="register"/></a>
    <a class="btn btn-primary" href="<c:url value="/user/listAllUsers"/>">ListAllUsers</a>
</div>
</body>
</html>