<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 17.04.17
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Table</title>
    <link rel="stylesheet" type="text/css"
          href="<%=application.getContextPath() %>/additional/style.css" >
</head>
<body>
    <form action="add">
        <button class="addNewDoc" type="submit">Add new document</button>
    </form>
    <form class="login-form" action="filter" method="get">
        <select name="fieldBy">
            <option>Mark</option>
            <option>Type</option>
            <option>Name</option>
            <option>Code</option>
            <option>Building</option>
            <option>Executor</option>
            <option>Developer</option>
        </select>
        <input type="text" name="filter_value" />
        <button class="addNewDoc" type="submit">Filter</button>
    </form>
    <form class="login-form" action="reset" method="post">
        <button class="addNewDoc" type="submit">Reset</button>
    </form>
    <form class="login-form" action="signOut" method="get">
        <button class="addNewDoc" type="submit">sign out</button>
    </form>
    <form>
        <p>Document type</p>
        <%--<select>--%>
            <%--<option>--%>
                <%----%>
            <%--</option>--%>
        <%--</select>--%>
        <input type="text" name="type"/>
        <p>Name</p>
        <input type="text" name="name"/>
    </form>
    <table>
        <thead>
            <tr>
                <th>Mark</th>
                <th>Type</th>
                <th>Name</th>
                <th>Code</th>
                <th>Executor</th>
                <th>Developer</th>
                <th>Building</th>
                <th>Version</th>
                <th>Init date</th>
                <th>Last change date</th>
                <th>File</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="document" items="${docs}">
                <tr>
                    <td class="narrow"><c:out value="${document.mark.name}" /></td>
                    <td><c:out value="${document.documentType.type}" /></td>
                    <td><c:out value="${document.name}" /></td>
                    <td class="narrow"><c:out value="${document.code}" /></td>
                    <td><c:out value="${document.executor.name}" /></td>
                    <td><c:out value="${document.developer.name}" /></td>
                    <td><c:out value="${document.building.name}" /></td>
                    <td class="narrow"><c:out value="${document.lastVersion}" />
                    <td><c:out value="${document.initDate}" /></td>
                    <td><c:out value="${document.lastChangeDate}" /></td>
                    <td><c:out value="${document.file}" /></td>
                    <td><i class="button edit">edit</i></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
