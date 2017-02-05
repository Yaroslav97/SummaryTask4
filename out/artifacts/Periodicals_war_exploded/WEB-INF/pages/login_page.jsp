<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>sign in</title>
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
            </c:if> <c:if test="${!empty sessionScope.authenticatedLogin}">
        </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${empty sessionScope.authenticatedLogin}">
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span>${SignUp}</a></li>
                <li class="active"><a href="/signIn"><span class="glyphicon glyphicon-log-in"></span> ${SignIn}</a></li>
            </c:if>
            <c:if test="${not empty sessionScope.authenticatedLogin}">
                <li><a href="/logout">${LogOut}</a></li>
            </c:if>
        </ul>
    </div>
</nav>

<br>
<br>
<br>

<div class="container">
    <form action="/signIn" method="post" class="col-xs-6">
        <input name="login" pattern="^[A-zА-я0-9]+$" required placeholder="${Login}" minlength="4" class="form-control"><br>
        <input type="password" name="password" required placeholder="${Password}" minlength="4" class="form-control"><br>
        <input type="submit" value="${SignIn}"><br>
    </form>
</div>

<hr>
<br>
<br>
<div align="center">
    <h4>${requestScope.signInInfo}</h4>
</div>
</body>
</html>
