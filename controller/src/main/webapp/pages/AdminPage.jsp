<%--
  Created by IntelliJ IDEA.
  User: павел
  Date: 23.12.2020
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin</title>
    <link rel = "stylesheet" type = "text/css" href = "${pageContext.request.contextPath}/pages/style.css">
</head>
<body>
<h1>Hello ADMIN!</h1>
<p>You can add salary to one teacher: </p>
<a href="<c:url value='/salary' />">Set Salary</a><br/>
<p>Show average salary: </p>
<a href="<c:url value='/averageSalary' />">Show average salary</a><br/>
<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>
