<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>add addition</title>
    <%@include file="/WEB-INF/jspf/libraries.jspf" %>
</head>
<body>

<%@include file="/WEB-INF/jspf/i18n.jspf" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/index">${Periodicals}</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/index">${sessionScope.authenticatedFullName}</a></li>
            <li class="active"><a href="/addEdition">${AddEdition}</a></li>
            <li><a href="/userList">${UserList}</a></li>
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
<br>
<br>

<div class="container">
    <form action="/addEdition" method="post" class="col-xs-6">
        <input name="name" pattern="^[A-zА-я0-9 ]+$" required minlength="3" placeholder="${Name}"
               class="form-control"><br>
        <input name="subject" pattern="^[A-zА-я ]+$" required minlength="3" placeholder="${Subject}"
               class="form-control"><br>
        <input pattern="^[0-9]+\.?[0-9]?$" required name="price" minlength="1" maxlength="3"
               placeholder="${Price}" class="form-control"><br>
        <input type="submit" value="${Add}"><br>
    </form>
</div>


<br>
<hr>
<br>

<div align="center" class="container">
    <h4>${requestScope.addEditionInfo}</h4>
</div>

</body>
</html>
