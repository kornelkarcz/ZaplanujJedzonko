<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*,java.sql.*" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<!DOCTYPE html>
<html lang="en">

<head>

</head>


<body>
<%@include file="/header.jspf" %>
<sql:setDataSource var="db" driver="com.mysql.jdbc.Driver"
                   url="jdbc:mysql://localhost/scrumlab"
                   user="root" password="coderslab"/>

<sql:query dataSource="${db}" var="result">
    SELECT * from recipe where id=${param.recipeId};
</sql:query>

<form action="/app/recipe/edit" method="post">
    <input type="hidden" name="recipeId" value="${recipeId}">


    <c:forEach var="row" items="${result.rows}">
        <label>Nazwa przepisu
            <input type="text" name="name" value="${row.name}">
        </label>
        <br>
        <label>Opis przepisu

            <input type="text" name="description" value="${row.description}">
        </label>
        <br>
        <label> Przygotowanie (minuty)
            <input type="text" name="prepartime" value="${row.preparation_time}">
        </label>
        <br>
        <label>Składniki
            <textarea cols="70" rows="10"name="ingredients">${row.ingredients}</textarea>
        </label>
        <br>
        <label>Sposób przygotowania
            <textarea cols="70" rows="10" name="preparation">"${row.preparation}"</textarea>
        </label>
        <br>
        <input type="submit" value="Zapisz">

    </c:forEach>
</form>

<%@include file="/footer.jspf" %>
</body>
</html>


