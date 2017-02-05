<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" uri="http://periodicals.nure.ua" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="tagCountSub" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>user info</title>
    <%@include file="/WEB-INF/jspf/libraries.jspf" %>
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
            <li><a href="/userList">${UserList}</a></li>
            <li class="active"><a href="">${UserInfo}</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${!empty sessionScope.authenticatedLogin}">
                <li><a href="/logout">${LogOut}</a></li>
            </c:if>
        </ul>
    </div>
</nav>

<c:if test="${empty sessionScope.authenticatedLogin}">
    <c:redirect url="/signIn"/>
</c:if>

<c:if test="${sessionScope.authenticatedRole != 'admin'}">
    <c:redirect url="/index"/>
</c:if>

<br>

<div class="container" align="center">
    <tagCountSub:tagCountSub fullName="${requestScope.fullName}" login="${login}" count="${count}"/>
    <br>
    <br>
    <br>
    <tag:list list="${sessionScope.usersInfo}"/>
</div>

</body>
</html>
