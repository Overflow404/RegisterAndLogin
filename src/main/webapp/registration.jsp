<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        Registration
    </title>
</head>
<body>
<form action="servlet/RegistrationServlet" method="post">

    <c:if test="${not empty sessionScope.failureReason}">
        <c:out value="${sessionScope.failureReason}"/>
        <c:remove var="failureReason" scope="session"/>
        <br>
    </c:if>

    <label>
        Username
        <input name="username" type="text">
    </label>

    <label>
        Password
        <input name="password" type="text">
    </label>

    <input type="submit" value="Register">

</form>
</body>
</html>
