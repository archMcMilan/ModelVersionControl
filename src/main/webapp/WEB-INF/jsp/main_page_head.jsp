<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 21.04.17
  Time: 21:51
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
                        <option>Executor</option>
                    </select>
                </div>
                <div class="form-group">
                    <input class="form-control filter" type="text" name="filter_value"  maxlength="255"/>
                </div>
                <button class="btn btn-primary" type="submit">Filter</button>
            </form>
            <form class="navbar-form navbar-left" action="reset" method="post">
                <button class="btn btn-primary" type="submit">Reset</button>
            </form>
            <%--<button class="btn btn-primary" type="submit" onclick="handleClick();">Add new document</button>--%>
            <form class="navbar-form navbar-left" action="addNewDocumentBtn" method="post">
                <button class="btn btn-primary" type="submit">Add new document</button>
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

<c:if test="${isAddnewDocument eq true}">
    <div class="panel panel-default" id="add_form" >
        <div class="panel-body">
            <form action="addNewDocument" method="post">
                <div class="form-group">
                    <div class="form-group">
                        <label>Mark</label>
                        <select name="docMarkField">
                            <c:forEach var="mark" items="${marks}">
                                <option><c:out value="${mark.name}" /></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Type</label>
                            <select name="docTypeField">
                                <c:forEach var="documentType" items="${documentTypes}">
                                    <option><c:out value="${documentType.type}" /></option>
                                </c:forEach>
                            </select>
                    </div>
                    <div class="form-group">
                        <label>Building</label>
                        <select name="docBuildingField">
                            <c:forEach var="building" items="${buildings}">
                                <option><c:out value="${building.name}" /></option>
                            </c:forEach>
                        </select>
                        <input class="form-control" name="docNewBuildingField"
                               placeholder="input here name of the new building if necessary" maxlength="255"/>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                            <input class="form-control" name="docNameField" maxlength="255"/>
                        </p>
                    </div>
                    <div class="form-group">
                        <label>Code</label>
                        <input type="text" class="form-control" name="docCodeField"
                               onkeyup="this.value=this.value.replace(/[^\d]/,'')" maxlength="5">
                    </div>
                    <div class="form-group">
                        <label>Developer</label>
                        <select name="docDevField">
                            <c:forEach var="developer" items="${developers}">
                                <option><c:out value="${developer.name}" /></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Executor</label>
                        <select name="docExecField">
                            <c:forEach var="executor" items="${executors}">
                                <option><c:out value="${executor.name}" /></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit">Add new</button>
                    </div>
                </div>
            </form>
            <div class="form-group">
                <form action="cancelNewDocumentBtn" method="post">
                    <button class="btn btn-primary" type="submit">Cancel</button>
                </form>

            </div>
        </div>
    </div>
</c:if>

<div class="container-fluid">

    <div class="panel panel-default" id="documents">
        <div class="table-container" id="table">
            <table class="table table-hover">
                <tr>
                    <th>Mark</th>
                    <th>Type</th>
                    <th>Name</th>
                    <th>Code</th>
                    <th>Developer</th>
                    <th>Executor</th>
                    <th>Building</th>
                    <th>Version</th>
                    <th>Init date</th>
                    <th>Last change date</th>
                    <th>File</th>
                </tr>
                <c:forEach var="document" items="${docs}">
                    <tr>

                        <td><c:out value="${document.mark.name}" /></td>
                        <td><c:out value="${document.documentType.type}" /></td>
                        <td><c:out value="${document.name}" /></td>
                        <td><c:out value="${document.code}" /></td>
                        <td><c:out value="${document.developer.name}" /></td>
                        <td><c:out value="${document.executor.name}" /></td>
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
