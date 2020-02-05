<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Index page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/index.css">
</head>

<body>
<fmt:setLocale value="en"/>
<fmt:setBundle basename="view"/>

<div class="container">
    <div class="row align-items-center vertical-center">
        <div class="col-3"></div>
        <div class="col-6 rounded shadow bg-white">
            <div class="d-flex flex-column index-block justify-content-center">
                <h1 class=""><fmt:message key="welcome"/></h1>
                <h3 class=""><fmt:message key="welcome.text"/></h3>
                <div class=""><a class="btn btn-primary btn-block" role="button"
                                 href="<c:url value="pages/login.jsp"/>"><fmt:message
                        key="login"/></a>
                </div>

                <div class=""><a class="btn btn-primary btn-block" role="button"
                                 href="<c:url value="pages/register.jsp"/>"><fmt:message
                        key="register"/></a></div>
            </div>
        </div>
        <div class="col-3"></div>
    </div>

</div>
</body>
</html>