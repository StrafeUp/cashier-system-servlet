<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/users.css">

</head>
<body>
<div class="row align-items-center bg-white">
    <div class="col"></div>
    <div class="col">
        <div class="container rounded shadow">
            <c:if test="${sessionScope.user != null}">
                <p>User:</p>
                <p>Id:${sessionScope.user.id}</p>
                <p>Username:${sessionScope.user.username}</p>
                <p>Email:${sessionScope.user.email}</p>
                <p>Role:${sessionScope.user.role}</p>

                <form action="${pageContext.request.contextPath}/user/logout" method="post">
                    <input class="btn btn-primary" type="submit" value="Logout">
                </form>
            </c:if>
        </div>
    </div>
    <div class="col"></div>
</div>
<div class="row align-items-center bg-white">
    <div class="col"></div>
    <div class="col container rounded shadow ">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Password</th>
                <th scope="col">Role</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.password}"/></td>
                    <td><c:out value="${user.role}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:forEach items="${requestScope.pages}" var="page">
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/user/listAllUsers?page=${page}">${page}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
    <div class="col"></div>
</div>

</body>
</html>
