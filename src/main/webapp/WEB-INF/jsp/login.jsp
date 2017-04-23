<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 17.04.17
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Login page</title>
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/additional/css/bootstrap.css" >
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/additional/style2.css" >
</head>
<body>

    <div class="container-fluid">
        <div class="container c_main">
            <section class="col-xs-6 col-xs-offset-3">
                <form action="signIn" method="post">
                    <input type="text" class="form-control" placeholder="username" name="login"  maxlength="50" required/>
                    </br>
                    <input type="password" class="form-control" placeholder="password" name="password" maxlength="50" required/>
                    <div class="modal-footer">
                         <button class="btn btn-primary" type="submit">login</button>
                    </div>
                    <c:if test="${isLogin eq false}">
                        <p id="error">Sorry! Your password or email is incorrect! Please try again.</p>
                    </c:if>
                        <p class="message">Forget password? <a href="#">Send me a password</a></p>
                </form>
            </section>
        </div>
    </div>


    <%--<div class="login-page">--%>
        <%--<div class="form">--%>
            <%--<form class="login-form" action="signIn" method="post">--%>
                <%--<input type="text" placeholder="username" name="login" required/>--%>
                <%--<input type="password" placeholder="password" name="password" required/>--%>
                <%--<button type="submit">login</button>--%>
                <%--<c:if test="${isLogin eq false}">--%>
                    <%--<p id="error">Sorry! Your password or email is incorrect! Please try again.</p>--%>
                <%--</c:if>--%>
                <%--<p class="message">Forget password? <a href="#">Send me a password</a></p>--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>
</body>
</html>
