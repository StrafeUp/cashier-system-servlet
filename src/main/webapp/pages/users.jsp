<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

</head>
<body>

<c:if test="${sessionScope.user} != null">
    <td>User:</td>
    ${sessionScope.user}</c:if>

<table class="table">
    <c:forEach items="${requestScope.users}" var="user">
        <tr>
            <td>User ID: <c:out value="${user.id}"/></td>
            <td>Username: <c:out value="${user.username}"/></td>
            <td>Email: <c:out value="${user.email}"/></td>
            <td>Password: <c:out value="${user.password}"/></td>
            <td>Role: <c:out value="${user.role}"/></td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
