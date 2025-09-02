<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add Book</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css"> <!-- Fixed CSS path -->
    
</head>
<body>
    <div class="container">
        <h1>Add New Book</h1>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/books/add" method="post"> <!-- Fixed form action path -->
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="isbn">ISBN:</label>
                <input type="text" id="isbn" name="isbn" required>
            </div>
            <div class="form-group">
                <label for="publicationYear">Publication Year:</label>
                <input type="number" id="publicationYear" name="publicationYear" required>
            </div>
            <div class="form-group">
                <label for="authorId">Author:</label>
                <select id="authorId" name="authorId" required>
                    <option value="">Select Author</option>
                    <c:forEach items="${authors}" var="author">
                        <option value="${author.id}">${author.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn">Save Book</button>
            <a href="${pageContext.request.contextPath}/books" class="btn cancel">Cancel</a> <!-- Fixed cancel link -->
        </form>
    </div>
</body>
</html>
