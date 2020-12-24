<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; UTF-8" language="java" %>

<html>
  <head>
    <title>Teacher</title>
  </head>
    <body>

    <%= session.getAttribute("report") %>
    <%= new Date()%>

    </body>
</html>