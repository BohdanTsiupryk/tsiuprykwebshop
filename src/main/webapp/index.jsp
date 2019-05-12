<%--
  Created by IntelliJ IDEA.
  User: bohdantsiupryk
  Date: 05.05.19
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Tsiupryk WebShop</title>
  <c:if test="${sessionScope.currentUser != null}">
    <meta http-equiv="refresh" content="0; url=/goods" />
  </c:if>
</head>
<body>
<div align="center">
  <h2>Hello guest!</h2>
  <h3>LogIn please =)</h3>
</div>
<div align="center">
  <c:if test="${badPass}">
    <b style="color:red">Bad login/password try again or <a href="/registration.jsp">SignUp</a></b> <br><br>
  </c:if>
  ${successReg}<br>
  Please enter your: <br>
  <form action="/login" method="post" accept-charset="UTF-8">
    <label>Email <input type="email" placeholder="Email" required size="20" name="email"/> </label><br>
    <label>Password <input type="password" placeholder="Password" required size="20" name="password"/> </label><br>
    <input type="submit" value="LogIn">
  </form>
  <br><br>
  <a href="registration.jsp"><button>Registration</button></a>
</div>
</body>
</html>
