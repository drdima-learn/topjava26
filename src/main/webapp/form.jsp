<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Add Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add Meal</h2>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
<form method="post">
    <input type="hidden" value="${meal.id}">
    DateTime <input name="dateTime" type="datetime-local" value="${meal.dateTime}"/><br>
    Description <input name="description" type="text" value="${meal.description}"/><br>
    Calories <input name="calories" type="number" value="${meal.calories}"/><br>
    <input type="submit">
</form>
</body>
</html>