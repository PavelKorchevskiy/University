<%@ page import="org.example.constans.Attributes" %>
<%@ page import="org.example.repository.producer.StudentProducer" %>
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
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/pages/style.css">
</head>
<body>
<h1>Hello Student</h1>
<%= StudentService.getRatingAsString(StudentProducer.getRepository()
        .findByLoginAndPassword(
                String.valueOf(session.getAttribute(Attributes.LOGIN)),
                String.valueOf(session.getAttribute(Attributes.PASSWORD)))
        .get())%>
<br/>
<form method="post" action="<c:url value="/logout"/>">
    <input class="button" type="submit" value="LogoutButton">
</form>
<%--<a href="<c:url value='/logout' />">Logout</a>--%>
</body>
</html>
