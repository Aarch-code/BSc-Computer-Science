<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Author</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <div class="container">
        <h1>Edit Author</h1>
        
        <!-- Displaying error message if exists -->
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        
        <!-- Edit Author Form -->
        <form action="${pageContext.request.contextPath}/authors/edit/${author.id}" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${author.name}" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${author.email}" required>
            </div>
            <button type="submit" class="btn">Update Author</button>
            <a href="${pageContext.request.contextPath}/authors" class="btn cancel">Cancel</a>
        </form>
    </div>
</body>
</html>
