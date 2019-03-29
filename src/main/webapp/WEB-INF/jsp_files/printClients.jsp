<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.drizhiruk.domain.Client" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Clients</title>
</head>
<body>

<p><h1>List of clients</h1></p>

<c:if test="${not empty message}">

    <ul>
        <c:forEach var="listValue" items="${message}">
            <li>${listValue}</li>
        </c:forEach>
    </ul>

</c:if>

</body>
</html>