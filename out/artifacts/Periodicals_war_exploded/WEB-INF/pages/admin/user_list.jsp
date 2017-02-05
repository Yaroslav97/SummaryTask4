<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>user list</title>
    <%@include file="/WEB-INF/jspf/libraries.jspf"%>
</head>
<body>

<%@include file="/WEB-INF/jspf/i18n.jspf"%>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/index">${Periodicals}</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/index">${sessionScope.authenticatedFullName}</a></li>
            <li><a href="/addEdition">${AddEdition}</a></li>
            <li class="active"><a href="/userList">${UserList}</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${!empty sessionScope.authenticatedLogin}">
                <li><a href="/logout">${LogOut}</a></li>
            </c:if>
        </ul>
        <form action="/userList" method="post" class="navbar-form navbar-left">
            <div class="input-group">
                <input name="search" required placeholder="${FullName}" class="form-control">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</nav>

<c:if test="${empty sessionScope.authenticatedLogin}">
    <c:redirect url="/signIn"/>
</c:if>

<c:if test="${sessionScope.authenticatedRole != 'admin'}">
    <c:redirect url="/index"/>
</c:if>

<c:if test="${sessionScope.authenticatedBan == true}">
    <c:redirect url="/logout"/>
</c:if>

<div class="container">
    <form action="/userList" method="post" class="col-xs-2">
        <select name="role" class="form-control">
            <option selected value="users">${Users}</option>
            <option value="admins">${Admins}</option>
        </select>
        <input type="submit" value="ok">
    </form>

    <table class="table">
        <tr>
            <th>${FullName}</th>
            <th>${Login}</th>
            <th>${Email}</th>
            <th>${Score}</th>
            <th>${Role}</th>
            <th>${Ban}</th>
            <th>${ChangeBan}</th>
        </tr>

        <c:forEach items="${userList}" var="userList">
            <tr>
                <td>${userList.fullName}</td>
                <td>${userList.login}</td>
                <td>${userList.email}</td>
                <td>${userList.score}</td>
                <td>${userList.role}</td>
                <td>${userList.ban}</td>
                <td><a href="/changeStatus?login=${userList.login}&role=${userList.role}">${ChangeStatus}</a></td>
                <td><a href="/userInfo?login=${userList.login}&fullName=${userList.fullName}">${Info}</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
