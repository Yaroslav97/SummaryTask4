<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>registration</title>
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
            <li><a href="/restoreAccess">${RestoreAccess}</a></li>
            <c:if test="${sessionScope.authenticatedRole == 'user'}">
                <li><a href="/userCabinet">${sessionScope.authenticatedFullName}</a></li>
            </c:if>
            <c:if test="${sessionScope.authenticatedRole == 'admin'}">
                <li><a href="/adminCabinet">${sessionScope.authenticatedFullName}</a></li>
            </c:if>
            <c:if test="${!empty sessionScope.authenticatedLogin}">
                <li><a href="/logout">${LogOut}</a></li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${empty sessionScope.authenticatedLogin}">
                <li class="active"><a href="/registration"><span class="glyphicon glyphicon-user"></span> ${SignUp}</a></li>
                <li><a href="/signIn"><span class="glyphicon glyphicon-log-in"></span> ${SignIn}</a></li>
            </c:if>
        </ul>
    </div>
</nav>

<br>
<br>
<br>

<div class="container">
    <form action="/registration" method="post" class="col-xs-6">
        <input name="fullName" pattern="^[A-zА-я]+ [A-zА-я]+$" required placeholder="${FullName}" minlength="4" maxlength="30" class="form-control"><br>
        <input name="login" pattern="^[A-zА-я0-9]+$" required placeholder="${Login}" minlength="4" maxlength="30" class="form-control"><br>
        <input type="email" pattern="^[a-z0-9]+\.?_?[a-z0-9]+@.{2,9}\..{2,3}$" name="email" required placeholder="${Email}" minlength="4" maxlength="30" class="form-control"><br>
        <input type="password" name="password" required placeholder="${Password}" minlength="4" maxlength="30" class="form-control"><br>
        <select name="role" class="form-control">
            <option selected value="user">${User}</option>
            <option value="admin">${Admin}</option>
        </select>
        <br>
        <input type="submit" value="${SignUp}"><br>
    </form>
</div>

<hr>
<br>

<div align="center" class="container">
    <h3>${requestScope.regInfo}</h3>
</div>

</body>
</html>
