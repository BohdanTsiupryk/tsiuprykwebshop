<%--
  Created by IntelliJ IDEA.
  User: tsiupryk
  Date: 10.05.19
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit ${good.getName()}</title>
</head>
<body>
<form action="/editGood" method="post" accept-charset="UTF-8">
    <input type="hidden" value="${good.getId()}" name="id"/>
    Email       <br> <input type="text" value="${good.getName()}" placeholder="Email" required size="20" name="email"/> <br><br>
    Name        <br> <input type="" value="${good.getDescription()}" placeholder="Description" required size="20" name="description"/> <br><br>
    Description <br> <input type="text" value="${good.getPrice()}" placeholder="Price" required size="20" name="price"/> <br><br>
    <input type="submit" name="submit" value="Save Changes" size="10">
</form>

</body>
</html>
