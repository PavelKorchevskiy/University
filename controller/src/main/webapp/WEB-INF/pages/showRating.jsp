<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: павел
  Date: 23.12.2020
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rating</title>
</head>
<body>
<%= session.getAttribute("answer")%> <br/>
<%= new Date().getDate()%>
</body>
</html>
