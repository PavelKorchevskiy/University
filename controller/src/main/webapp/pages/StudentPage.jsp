<%@ page import="org.example.constans.Attributes" %>
<%@ page import="org.example.service.StudentService" %><%--
  Created by IntelliJ IDEA.
  User: павел
  Date: 23.12.2020
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file="/pages/style.css" %>
    </style>
</head>
<body>
<h1>Hello Student</h1>
<%= session.getAttribute(Attributes.RATING)%>
<br/>
<form method="post" action="<c:url value="/logout"/>">
    <input class="button" type="submit" value="LogoutButton">
</form>
</body>
</html>
