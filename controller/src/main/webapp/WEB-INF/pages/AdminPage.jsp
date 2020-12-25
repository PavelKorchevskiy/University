<%--
  Created by IntelliJ IDEA.
  User: павел
  Date: 23.12.2020
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
    <h1>Hello ADMIN!</h1>
    <a href="<c:url value='/salary' />">Set Salary</a><br/>
    <a href="<c:url value='/logout' />">Logout</a>
</body>
</html>
