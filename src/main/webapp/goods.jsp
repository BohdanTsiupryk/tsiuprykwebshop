<%--
  Created by IntelliJ IDEA.
  User: bohdantsiupryk
  Date: 07.05.19
  Time: 0:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goods list</title>
</head>
<body>
<form action="/profile" accept-charset="UTF-8">
    <button>My Profile</button>
</form>
<br>
<table>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Description</th>
        <th>Buy</th>
    </tr>
    <c:forEach var="good" items="${goods}">
        <tr>
            <td>${good.getName()}</td>
            <td>${good.getPrice()}</td>
            <td>${good.getDescription()}</td>
            <td>
                <form action="/buy" accept-charset="UTF-8">
                    <button value="${good.getId()}" name="buyId">Buy</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
