<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>
        Login
    </title>
</head>
<body>
<form action="servlet/LoginServlet" method="post">

    <c:if test="${not empty sessionScope.content}">
        <c:out value="${sessionScope.content}"/>
        <c:remove var="content" scope="session"/>
        <br>
    </c:if>

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

    <input type="submit" value="Login">

</form>
</body>
</html>
