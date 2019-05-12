<%--
  Created by IntelliJ IDEA.
  User: bohdantsiupryk
  Date: 07.05.19
  Time: 1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Page</title>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>E-mail</th>
        <th>Password</th>
        <th>Address</th>
        <th>Role</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.getId()}</td>
            <td>${user.getEmail()}</td>
            <td>${user.getPassword()}</td>
            <td>${user.getAddress()}</td>
            <td>${user.getRole()}</td>
            <td>
                <form action="/edit" accept-charset="UTF-8">
                    <button value="${user.getId()}" name="edit">edit</button>
                </form>
            </td>
            <td>
                <form action="/delete" accept-charset="UTF-8">
                    <button value="${user.getId()}" name="delete">delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="/goodsControl">
    <button>Goods Control</button>
</form>
</body>
</html>
