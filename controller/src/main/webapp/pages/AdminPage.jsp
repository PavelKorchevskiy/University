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
    <style><%@include file="/pages/style.css"%></style>
</head>
<body>
<h1>Hello ADMIN!</h1>
<p>You can add salary to one teacher: </p>
<a href="<c:url value='/salary' />">Set Salary</a><br/>
<p>Show average salary: </p>
<a href="<c:url value='/averageSalary' />">Show average salary</a><br/>
<form method="post" action="<c:url value="/logout"/>">
    <input class="button" type="submit" value="LogoutButton">
</form>
</body>
</html>
