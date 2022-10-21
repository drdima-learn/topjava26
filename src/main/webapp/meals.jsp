<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="style.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border="1">
    <caption>Meals</caption>
    <tr>
        <th>dateTime</th>
        <th>description</th>
        <th>calories</th>
        <th></th>
        <th></th>
    </tr>


    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr class="${meal.excess ? "excess" : "not_excess"}">
            <td>${meal.dateTimeFormatted}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="?action=edit&id=${meal.id}">Edit</a></td>
            <td><a href="?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<a href="?action=add">Add</a>

</body>
</html>