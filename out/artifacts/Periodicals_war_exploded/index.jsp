<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>index</title>
    <%@ include file="WEB-INF/jspf/libraries.jspf" %>
</head>

<body>

<fmt:setLocale value="${sessionScope.lang}" scope="session"/>
<%@include file="WEB-INF/jspf/i18n.jspf" %>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/index">${Periodicals}</a>
        </div>
        <ul class="nav navbar-nav">
            <c:if test="${empty sessionScope.authenticatedLogin}">
                <li><a href="/restoreAccess">${RestoreAccess}</a></li>
            </c:if>
            <c:if test="${sessionScope.authenticatedRole == 'user'}">
                <li><a href="/userCabinet">${sessionScope.authenticatedFullName}</a></li>
            </c:if>
            <c:if test="${sessionScope.authenticatedRole == 'admin'}">
                <li class="active"><a href="">${sessionScope.authenticatedFullName}
                    (${sessionScope.authenticatedRole})</a></li>
                <li><a href="/addEdition">${AddEdition}</a></li>
                <li><a href="/userList">${UserList}</a></li>
            </c:if>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <c:if test="${empty sessionScope.authenticatedLogin}">
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span> ${SignUp}</a></li>
                <li><a href="/signIn"><span class="glyphicon glyphicon-log-in"></span> ${SignIn}</a></li>
            </c:if>
            <c:if test="${!empty sessionScope.authenticatedLogin}">
                <li><a href="/logout">${LogOut}</a></li>
            </c:if>
        </ul>
        <form action="/index" class="navbar-form navbar-left">
            <div class="input-group">
                <input name="search" required placeholder="${Edition}" class="form-control">
                <div class="input-group-btn">
                    <button class="btn btn-default" type="submit">
                        <i class="glyphicon glyphicon-search"></i>
                    </button>
                </div>
            </div>
        </form>
    </div>
</nav>

<div align="right" class="container">
    ${requestScope.subscribeInfo}
    <a href="/lang?lang=en">ENG</a>
    /
    <a href="/lang?lang=ru">RU</a>
</div>

<br>

<c:if test="${sessionScope.authenticatedBan == true}">
    <c:redirect url="/logout"/>
</c:if>

<div class="container">

    <div align="right">
        <form action="/index" class="col-xs-2">
            <select name="sort" class="form-control">
                <option selected value="subject">${subject}</option>
                <option value="name">${name}</option>
                <option value="price">${price}</option>
                <option value="reverse price">reverse price</option>
                <option value="rank">${rank}</option>
                <option value="id">${id}</option>
            </select>
            <input type="submit" value="${Sort}"><br><br>
        </form>
    </div>

    <div align="right">
        <form action="/index" class="col-xs-2">
            <select name="subject" class="form-control">
                <option selected value="news">news</option>
                <option value="cars">cars</option>
                <option value="cuisine">cuisine</option>
                <option value="sport">sport</option>
            </select>
            <input type="submit" value="ok"><br><br>
        </form>
    </div>

    <div align="right">
        <form action="/index" class="col-xs-2">
            <input name="filter1" required pattern="^[0-9]+\.?[0-9]?$" placeholder="${PriceFrom}" maxlength="4"
                   class="form-control">
            <input name="filter2" required pattern="^[0-9]+\.?[0-9]?$" placeholder="${PriceTo}" maxlength="4"
                   class="form-control">
            <input type="submit" value="${Filter}"><br>
        </form>
    </div>

    <table class="table">
        <tr>
            <th>${id}</th>
            <th>${name}</th>
            <th>${subject}</th>
            <th>${price}</th>
        </tr>
        <c:forEach items="${editionList}" var="editionList">
            <tr>
                <td>${editionList.id}</td>
                <td>${editionList.name}</td>
                <td>${editionList.subject}</td>
                <td>${editionList.price}</td>
                <c:if test="${!empty sessionScope.authenticatedLogin}">
                    <c:choose>
                        <c:when test="${sessionScope.authenticatedRole == 'user'}">
                            <td><a href="/subscribe?id=${editionList.id}">${Subscribe}</a></td>
                        </c:when>
                        <c:when test="${sessionScope.authenticatedRole == 'admin'}">
                            <td>
                                <a href="/editEdition?eId=${editionList.id}&eName=${editionList.name}&eSubject=${editionList.subject}&ePrice=${editionList.price}">${Edit}</a>
                            </td>
                            <td>
                                <a href="/removeEdition?id=${editionList.id}">${Remove}</a>
                            </td>
                            <c:forEach items="${countSub}" var="countSub">
                                <c:choose>
                                    <c:when test="${countSub.id == editionList.id}">
                                        <td><a href="/editionInfo?info=${editionList.id}">${Info}</a></td>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>