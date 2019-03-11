<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../../header.jspf" %>

<section class="dashboard-section"> 
    <div class="row dashboard-nowrap">

        <%@include file="../../sidepanel.jsp" %>

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding"><h3 class="color-header text-uppercase">Lista Przepisów</h3></div>
                    <div class="col noPadding d-flex justify-content-end mb-2"><a href='<c:url value="/app/recipe/add"/>' class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Dodaj przepis</a></div>
                </div>
                <table class="table border-bottom schedules-content">
                    <thead>
                    <tr class="d-flex text-color-darker">
                        <th scope="col" class="col-1">ID</th>
                        <th scope="col" class="col-2">NAZWA</th>
                        <th scope="col" class="col-7">OPIS</th>
                        <th scope="col" class="col-2 center">AKCJE</th>
                    </tr>
                    </thead>
                    <tbody class="text-color-lighter">

                    <c:if test="${recipes.size()>0}">
                        <c:forEach begin="0" step="1" end="${recipes.size()-1}" var="number">

                            <tr class="d-flex">
                                <th scope="row" class="col-1">${recipes.get(number).getId()}</th>
                                <td class="col-2">
                                        ${recipes.get(number).getName()}
                                </td>
                                <td clas="col-7">
                                        ${recipes.get(number).getDescription()}
                                </td>
                                <td class="col-2 d-flex align-items-center justify-content-center flex-wrap">

                                    <a href='<c:url value="/app/RecipeDelFromListConfirm">
                                <c:param name = "recipeId" value = "${recipes.get(number).getId()}"/>
                                </c:url>' class="btn btn-danger rounded-0 text-light m-1">Usuń</a>

                                    <a href='<c:url value="/app/recipe/details">
                                <c:param name = "recipeId" value = "${recipes.get(number).getId()}"/>
                                </c:url>' class="btn btn-info rounded-0 text-light m-1">Szczegóły</a>

                                    <a href='<c:url value="/app/recipe/edit">
                                <c:param name = "recipeId" value = "${recipes.get(number).getId()}"/>
                                </c:url>' class="btn btn-warning rounded-0 text-light m-1">Edytuj</a>

                                </td>
                            </tr>

                        </c:forEach>
                    </c:if>

                    </tbody>
                </table>
            </div>
        </div>


    </div>
</section>

<%@include file="../../footer.jspf" %>

