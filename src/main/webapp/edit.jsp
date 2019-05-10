<%--
  Created by IntelliJ IDEA.
  User: tsiupryk
  Date: 24.04.19
  Time: 21:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body aling="center">
EDIT INFORMATION ABOUT USER(${user.getEmail()}) <br><br>
<form action="/edit" method="post" accept-charset="UTF-8">
    <input type="hidden" value="${user.getId()}" name="id"/>
    Email <br> <input type="text" value="${user.getEmail()}" placeholder="Email" required size="20" name="email"/> <br><br>
    Password <br> <input type="text" value="${user.getPassword()}" placeholder="Password" required size="20" name="password"/> <br><br>
    Address <br> <input type="text" value="${user.getAddress()}" placeholder="Address" required size="20" name="address"/> <br><br>
    <c:if test="${editorRole == 'ADMIN'}">
        Role <br>
        <select size="1" name="role" >
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
        </select> <br><br>
    </c:if>
    <input type="submit" name="submit" value="Save Changes" size="10">
</form>
</body>
</html>
