<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="../css/login.css">
</head>
<body>

<div class="container">
    <div class="row align-items-center vertical-center">
        <div class="col-3"></div>
        <div class="col-6 rounded shadow bg-white">
            <div class="index-block justify-content-center">
                <div class="blankDiv"></div>
                <form action="${pageContext.request.contextPath}/user/login" method="post">
                    <div class="form-group">
                        <label for="emailInput">Email address</label>
                        <input id="emailInput" class="form-control" type="email" aria-describedby="emailHelp"
                               placeholder="Enter email" name="email">
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                            else.</small>
                    </div>
                    <div class="form-group">
                        <label for="passwordInput">Password</label>
                        <input id="passwordInput" class="form-control" type="password"
                               placeholder="Password" name="password">
                    </div>
                    <p>${requestScope.error}</p>
                    <input class="btn btn-primary" type="submit" name="submit" value="Log in"/>

                </form>

            </div>
        </div>
        <div class="col-3"></div>
    </div>

</div>

</body>
</html>