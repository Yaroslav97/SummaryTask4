<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Subscribers Information</title>
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
            <li class="active"><a href="">${EditionInfo}</a></li>
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

<div class="container">

    <table class="table">
        <c:forEach items="${editionInfo}" var="editionInfo">
            <tr>
                <th>${Id}</th>
                <td>${editionInfo.id}</td>
            </tr>
            <tr>
                <th>${Name}</th>
                <td>${editionInfo.name}</td>
            </tr>
            <tr>
                <th>${Subject}</th>
                <td>${editionInfo.subject}</td>
            </tr>
            <tr>
                <th>${Subscribers}</th>
                <td>${editionInfo.countSubscribe}</td>
            </tr>

            <%--todo--%>
            <tr>
                <th>${CountUnsubscribers}</th>
                <td>${sessionScope.countUnsubscribers}</td>
            </tr>

            <tr>
                <th>${TotalSum}</th>
                <td>${editionInfo.price}</td>
            </tr>
        </c:forEach>
    </table>

    <hr>
    <br>

    <table class="table">
        <tr>
            <th>${FullName}</th>
            <th>${Login}</th>
            <th>${Email}</th>
            <th>${Ban}</th>
        </tr>

        <c:forEach items="${subList}" var="subList">
            <tr>
                <td>${subList.fullName}</td>
                <td>${subList.login}</td>
                <td>${subList.email}</td>
                <td>${subList.ban}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
