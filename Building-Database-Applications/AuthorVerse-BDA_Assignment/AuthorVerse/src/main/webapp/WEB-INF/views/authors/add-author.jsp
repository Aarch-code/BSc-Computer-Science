<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Author</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
    <div class="container">
        <h1>Add New Author</h1>
        
        <!-- Form to add new author -->
        <form action="${pageContext.request.contextPath}/authors/add" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <button type="submit" class="btn">Save Author</button>
            <a href="${pageContext.request.contextPath}/authors" class="btn cancel">Cancel</a>
        </form>
    </div>
</body>
</html>