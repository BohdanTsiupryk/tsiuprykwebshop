<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: bohdantsiupryk
  Date: 05.05.19
  Time: 12:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div align="center">
    Registration form: <br>
    <form action="/registration" method="post" accept-charset="UTF-8">
        <label>Email <input type="email" placeholder="Email" required size="20" name="email"/> </label>
        <label>Address <input type="text" placeholder="Address" required size="20" name="address"/> </label>
        <label>Password <br>  <input type="password" placeholder="Password" required size="20" name="password"/></label>
        <c:if test="${samePass == false}">
            <h4 style="color: red">Password are not the same</h4>
        </c:if>
        <label>Repeat Password <br> <input type="password" placeholder="Repeat password" required size="20" name="repassword"/> </label>
        <input type="submit" name="submit" value="SignUp" size="10">
    </form>
    <br>
    <a href="/index"><button>LogIn</button></a>
</div>
</body>
</html>
