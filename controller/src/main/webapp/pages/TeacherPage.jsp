
<%@ page import="org.example.constans.Attributes" %>
<%@ page import="org.example.repository.producer.TeacherProducer" %>
<%@ page import="org.example.service.TeachersServ" %><%--
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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/pages/style.css">
</head>
<body>
<h1>Hello Teacher</h1>
<%= new TeachersServ().showGroup(
        TeacherProducer.getRepository()
                .findByLoginAndPassword(
                        String.valueOf(session.getAttribute(Attributes.LOGIN)),
                        String.valueOf(session.getAttribute(Attributes.PASSWORD)))
                .get())%>
<br/>
<h3>Enter student's id, subject and rating:</h3>
<form method="post" action="<c:url value="/changeRating"/>">
    <input type="text" required placeholder="id" name="idMyStudent"><br/>
    <input type="text" required placeholder="subject" name="subjectMyStudent"><br/>
    <input type="number" required placeholder="rating" name="ratingMyStudent"><br/>
    <input class="button" type="submit" value="Change rating">
</form>

<form method="post" action="<c:url value="/logout"/>">
    <input class="button" type="submit" value="LogoutButton">
</form>
<%--<a href="<c:url value='/logout' />">Logout</a>--%>
</body>
</html>
