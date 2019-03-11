<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../header.jspf" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <%@include file="../sidepanel.jsp" %>

        <div class="m-4 p-4 width-medium">
            <div class="dashboard-header m-4">
                <div class="dashboard-menu">
                    <div class="menu-item border-dashed">
                        <a href='<c:url value="/app/recipe/add"/>'>
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href='<c:url value="/app/plan/add"/>'>
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj plan</span>
                        </a>
                    </div>
                    <div class="menu-item border-dashed">
                        <a href='<c:url value="/app/recipe/plan/add"/>'>
                            <i class="far fa-plus-square icon-plus-square"></i>
                            <span class="title">dodaj przepis do planu</span>
                        </a>
                    </div>
                </div>

                <div class="dashboard-alerts">
                    <div class="alert-item alert-info">
                        <i class="fas icon-circle fa-info-circle"></i>
                        <span class="font-weight-bold">Liczba przepisów: ${recipeNumber}</span>
                    </div>
                    <div class="alert-item alert-light">
                        <i class="far icon-calendar fa-calendar-alt"></i>
                        <span class="font-weight-bold">Liczba planów: ${planNumber}</span>
                    </div>
                </div>
            </div>

            <c:if test="${not empty param.emptyplanorrecipe}">
                <span style="color: red">Aby dodać przepis do planu musisz mieć min. 1 przepis i 1 plan !</span>
            </c:if>

            <div class="m-4 p-4 border-dashed">
                <h2 class="dashboard-content-title">
                    <c:if test ="${not empty emptyPlan}">
                        <span>Dodaj jakieś przepisy do swoich planów</span>
                    </c:if>
                    <c:if test ="${not empty recentPlan}">
                        <span>Ostatnio dodany plan: &nbsp;&nbsp;</span>${recentPlan.get(0).getPlanName()}
                    </c:if>

                </h2>

                <table class="table">

                    <c:forEach items="${days}" var="day">

                        <thead>
                        <tr class="d-flex">
                            <th class="col-2">${day}</th>
                            <th class="col-8"></th>
                            <th class="col-2"></th>
                        </tr>
                        </thead>


                        <tbody>

                        <c:forEach items="${recentPlan}" var="meal">
                            <c:if test="${meal.dayName == day}">

                                <tr class="d-flex">
                                    <td class="col-2">${meal.mealName}</td>
                                    <td class="col-8">${meal.recipeName}</td>
                                    <td class="col-2">
                                        <button type="button" class="btn btn-primary rounded-0">Szczegóły</button>
                                    </td>
                                </tr>

                            </c:if>
                        </c:forEach>

                        </tbody>

                    </c:forEach>
                </table>

            </div>
        </div>
    </div>
</section>


<%@include file="../footer.jspf" %>

