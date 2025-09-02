<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <div class="container">
        <h1>Authors</h1>
        
        <div class="actions">
            <a href="/authors/add" class="btn">Add New Author</a>
            <a href="/authors/with-books" class="btn">View Authors with Books</a>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${authors}" var="author">
                    <tr>
                        <td>${author.id}</td>
                        <td>${author.name}</td>
                        <td>${author.email}</td>
                        <td class="action-buttons">
                            <a href="/authors/edit/${author.id}" class="btn edit">Edit</a>
                            <a href="/authors/delete/${author.id}" class="btn delete">Delete</a>
                            <a href="/books/by-author/${author.id}" class="btn view">View Books</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <div class="footer">
            <a href="/" class="btn">Back to Home</a>
        </div>
    </div>
</body>
</html>