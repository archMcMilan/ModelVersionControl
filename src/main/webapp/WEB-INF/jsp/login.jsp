<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 17.04.17
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/additional/style.css" >
</head>
<body>
    <div class="login-page">
        <div class="form">
            <form class="login-form" action="signIn" method="post">
                <input type="text" placeholder="username" name="login" required/>
                <input type="password" placeholder="password" name="password" required/>
                <button type="submit">login</button>
                <p class="message">Forget password? <a href="#">Send me a password</a></p>
            </form>
        </div>
    </div>
</body>
</html>
