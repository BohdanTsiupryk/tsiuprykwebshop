<%--
  Created by IntelliJ IDEA.
  User: tsiupryk
  Date: 10.05.19
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goods editor</title>
</head>
<body>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="good" items="${goods}">
        <tr>
            <td>${good.getId()}</td>
            <td>${good.getName()}</td>
            <td>${good.getDescription()}</td>
            <td>${good.getPrice()}</td>
            <td>
                <form action="/editGood" accept-charset="UTF-8">
                    <button value="${good.getId()}" name="edit">edit</button>
                </form>
            </td>
            <td>
                <form action="/deleteGood" accept-charset="UTF-8">
                    <button value="${good.getId()}" name="delete">delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="/addGood" method="post" accept-charset="UTF-8">
    <label>Name        <input type="text" placeholder="Name" required size="20" name="name"/> </label>
    <label>Description <input type="text" placeholder="Description" required size="20" name="description"/> </label>
    <label>Price <br>  <input type="text" placeholder="Price" required size="20" name="price"/></label>
    <input type="submit" name="submit" value="ADD" size="10">
</form>
<form action="userControle">
    <button>Goods Control</button>
</form>
</body>
</html>
