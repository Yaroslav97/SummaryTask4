<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="fullName" required="true" %>
<%@ attribute name="login" required="true" %>
<%@ attribute name="count" required="true" %>

<table class="table">
    <tr>
        <td>Full Name</td>
        <td>${fullName}</td>
    </tr>
    <tr>
        <td>Login</td>
        <td>${login}</td>
    </tr>
    <tr>
        <td>Coun sub</td>
        <td>${count}</td>
    </tr>
</table>