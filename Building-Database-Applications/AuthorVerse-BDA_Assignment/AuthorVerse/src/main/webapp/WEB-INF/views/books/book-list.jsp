<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css"> <!-- Fixed CSS path -->
</head>
<body>
    <div class="container">
        <h1>
            <c:choose>
                <c:when test="${not empty author}">
                    Books by ${author.name}
                </c:when>
                <c:otherwise>
                    All Books
                </c:otherwise>
            </c:choose>
        </h1>

        <c:if test="${not empty successMessage}">
            <div class="alert success">${successMessage}</div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert error">${errorMessage}</div>
        </c:if>

        <a href="${pageContext.request.contextPath}/books/add" class="btn">Add New Book</a> <!-- Fixed Add Book link -->

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>ISBN</th>
                    <th>Year</th>
                    <th>Author</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.isbn}</td>
                        <td>${book.publicationYear}</td>
                        <td>${book.author.name}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/books/edit/${book.id}" class="btn edit">Edit</a> <!-- Fixed Edit Book link -->
                            <a href="${pageContext.request.contextPath}/books/delete/${book.id}" class="btn delete">Delete</a> <!-- Fixed Delete Book link -->
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/" class="btn">Back to Home</a> <!-- Fixed Back to Home link -->
    </div>
</body>
</html>
