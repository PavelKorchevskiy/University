<%@ page import="org.example.repository.memory.RepositoryForStudentsInMemory" %>
<%@ page import="org.example.constans.Attributes" %>
<%@ page import="org.example.repository.producer.StudentProducer" %><%--
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
<h1>Hello Student</h1>
<%= StudentProducer.getRepository()
        .findByLoginAndPassword(
                String.valueOf(session.getAttribute(Attributes.LOGIN)),
                String.valueOf(session.getAttribute(Attributes.PASSWORD)))
        .get()
        .getRatingAsString()%>
<br/>
<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>
