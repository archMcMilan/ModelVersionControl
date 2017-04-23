<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 22.04.17
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Table</title>
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/additional/css/bootstrap.css" >
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/additional/style.css" >
    <%--<script type="text/javascript" src="js/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="js/edit.js"></script>--%>
    <%--<script>--%>
    <%--function handleClick()--%>
    <%--{--%>
    <%--$("#documents").slideUp();--%>
    <%--$("#edit_form").slideToggle();--%>
    <%--}--%>
    <%--</script>--%>
</head>
<body>
<nav class="navbar navbar-inverse navbar-default">
    <div class="collapse navbar-collapse row">
        <div class="col-xs-6" align="left">
            <form class="navbar-form navbar-left" action="filter" method="get">
                <div class="form-group">
                    <select name="fieldBy">
                        <option>Mark</option>
                        <option>Type</option>
                        <option>Name</option>
                        <option>Code</option>
                        <option>Building</option>
                        <option>Developer</option>
                    </select>
                </div>
                <div class="form-group">
                    <input class="form-control filter" type="text" name="filter_value" />
                </div>
                <button class="btn btn-primary" type="submit">Filter</button>
            </form>
            <form class="navbar-form navbar-left" action="reset" method="post">
                <button class="btn btn-primary" type="submit">Reset</button>
            </form>
        </div>
        <div class="col-xs-6 " align="right">
            <a>
                <form class="navbar-form navbar-right" action="signOut" method="post">
                    <button class="btn btn-primary" type="submit">Sign out</button>
                </form>
            </a>
        </div>
    </div><!-- /.navbar-collapse -->
</nav>
<div class="container-fluid">

    <div class="panel panel-default" id="documents">
        <div class="panel-heading" align="center">
            <h1><c:out value="${user.company.name}" /></h1>
        </div>
        <div class="table-container" id="table">
            <table class="table table-hover">
                <tr>
                    <th>Mark</th>
                    <th>Type</th>
                    <th>Name</th>
                    <th>Code</th>
                    <th>Developer</th>
                    <th>Building</th>
                    <th>Version</th>
                    <th>Init date</th>
                    <th>Last change date</th>
                    <th>File</th>
                    <th>Edit</th>
                </tr>
                <c:forEach var="document" items="${docs}">
                    <tr>

                        <td><c:out value="${document.mark.name}" /></td>
                        <td><c:out value="${document.documentType.type}" /></td>
                        <td><c:out value="${document.name}" /></td>
                        <td><c:out value="${document.code}" /></td>
                        <td><c:out value="${document.developer.name}" /></td>
                        <td><c:out value="${document.building.name}" /></td>
                        <td><c:out value="${document.lastVersion}" /></td>
                        <td><c:out value="${document.initDate}" /></td>
                        <td><c:out value="${document.lastChangeDate}" /></td>
                        <td>
                            <form action="edit" method="post">
                                <button class="btn btn-primary" type="submit" title="Download" onclick="downloadClick();">
                                    <span class="glyphicon glyphicon-download-alt"></span>
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>
