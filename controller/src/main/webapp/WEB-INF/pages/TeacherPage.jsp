<%@ page import="org.example.repository.RepositoryForStudentsInMemory" %>
<%@ page import="org.example.repository.RepositoryForTeachersInMemory" %><%--
  Created by IntelliJ IDEA.
  User: павел
  Date: 23.12.2020
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello Teacher</h1>
<%= RepositoryForTeachersInMemory.getInstance()
        .findByLogin(String.valueOf(session.getAttribute("login"))).get().showGroup()%>
<br/>
<h3>Enter student's login, subject and rating:</h3>
<form method="post" action="<c:url value="/changeRating"/>">
    <input type="text" required placeholder="login" name="loginStudent"><br/>
    <input type="text" required placeholder="subject" name="subjectStudent"><br/>
    <input type="text" required placeholder="rating" name="ratingStudent"><br/>
    <input class="button" type="submit" value="Change rating">
</form>
<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>