<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
<%--    <h3><a href="index.html">Home</a></h3>--%>
<%--    <hr>--%>
<%--    <h2>${param.action == 'create' ? 'Create meal' : 'Edit meal'}</h2>--%>
    <h2>
        <c:choose>
            <c:when test="${action=='create'}">
                <spring:message code="mealForm.createMeal"/>
            </c:when>
            <c:otherwise>
                <spring:message code="mealForm.editMeal"/>
            </c:otherwise>
        </c:choose>
    </h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form:form method="post" action="update" modelAttribute="meal">
        <form:input type="hidden" path="id"/>
        <dl>
            <dt><spring:message code="mealForm.dateTime"/>:</dt>
<%--            <dd><input type="datetime-local" id="dateTime" name="dateTime" value="${meal.dateTime}" /></dd>--%>
            <dd><form:input type="datetime-local" path="dateTime" /></dd>
<%--            <dd><form:input path="dateTime" required="required" /></dd>--%>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/>:</dt>
            <dd><form:input path="description" type="text" size="40" required="required" /></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/>:</dt>
            <dd><form:input type="number" path="calories" required="required" /></dd>
        </dl>
        <button type="submit"><spring:message code="mealForm.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="mealForm.cancel"/></button>
    </form:form>
</section>
</body>
</html>
