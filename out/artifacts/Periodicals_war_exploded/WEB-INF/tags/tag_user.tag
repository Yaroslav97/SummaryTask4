<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="fullName" required="true" %>
<%@ attribute name="login" required="true" %>
<%@ attribute name="email" required="true" %>
<%@ attribute name="notification" required="true" %>

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
        <td>Email</td>
        <td>${email}</td>
    </tr>
    <tr>
        <td>Notification</td>
        <td>${notification}</td>
    </tr>
</table>
