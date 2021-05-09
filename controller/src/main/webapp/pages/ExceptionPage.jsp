<%--
  Created by IntelliJ IDEA.
  User: pavel
  Date: 11.02.2021
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<% String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
%>
<html>
<head>
    <title>Exception</title>
    <style>
        <%@include file="/pages/style.css" %>
    </style>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Type: <%= exception%>
</p>
<p>Message: <%= message %>
</p>
<input type="button" onclick="history.back();" value="Back"/>
</body>
</html>
