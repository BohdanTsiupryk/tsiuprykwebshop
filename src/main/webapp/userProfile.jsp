<%--
  Created by IntelliJ IDEA.
  User: tsiupryk
  Date: 30.04.19
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Profile ${user.email}</title>
</head>
<body aling="center">
    <table>
        <tr>
            <td>Email</td>
            <td>${user.getEmail()}</td>
        </tr>
        <tr>
            <td>Password</td>
            <td>${user.getPassword()}</td>
        </tr>
        <tr>
            <td>Address</td>
            <td>${user.getAddress()}</td>
        </tr>
        <tr>
            <td>Role</td>
            <td>${user.getRole()}</td>
        </tr>
    </table>
    <form action="/edit" accept-charset="UTF-8">
        <button value="${user.getId()}" name="edit">edit</button>
    </form>
</body>
</html>
