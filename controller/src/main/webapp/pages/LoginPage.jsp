<%--
  Created by IntelliJ IDEA.
  User: павел
  Date: 23.12.2020
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div class="form">
    <h1>Вход в систему JSP</h1><br>
    <form method="post" action="">
        <input type="text" required placeholder="login" name="login"><br>
        <input type="password" required placeholder="password" name="password"><br><br>
        <input class="button" type="submit" value="Войти">
    </form>
</div>
</body>
</html>
