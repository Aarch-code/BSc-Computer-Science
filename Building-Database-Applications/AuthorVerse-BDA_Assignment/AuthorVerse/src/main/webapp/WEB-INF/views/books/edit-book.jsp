<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Book</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css"> <!-- Fixed CSS path -->
</head>
<body>
    <div class="container">
        <h1>Edit Book</h1>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/books/edit/${book.id}" method="post"> <!-- Fixed form action path -->
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${book.title}" required>
            </div>
            <div class="form-group">
                <label for="isbn">ISBN:</label>
                <input type="text" id="isbn" name="isbn" value="${book.isbn}" required>
            </div>
            <div class="form-group">
                <label for="publicationYear">Publication Year:</label>
                <input type="number" id="publicationYear" name="publicationYear" 
                       value="${book.publicationYear}" required>
            </div>
            <div class="form-group">
                <label for="authorId">Author:</label>
                <select id="authorId" name="authorId" required>
                    <option value="">Select Author</option>
                    <c:forEach items="${authors}" var="author">
                        <option value="${author.id}" 
                                ${author.id == book.author.id ? 'selected' : ''}>
                            ${author.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn">Update Book</button>
            <a href="${pageContext.request.contextPath}/books" class="btn cancel">Cancel</a> <!-- Fixed cancel link -->
        </form>
    </div>
</body>
</html>
