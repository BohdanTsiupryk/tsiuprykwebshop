<%--
  Created by IntelliJ IDEA.
  User: bohdantsiupryk
  Date: 07.05.19
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WebShop Code confirmation</title>
</head>
<body>
    <div align="center">
        <form method="post" action="/buy">
            <label>Your Code <br><input type="text" name="code"></label>
            <input type="submit" value="confrim">
        </form>
    </div>
</body>
</html>
