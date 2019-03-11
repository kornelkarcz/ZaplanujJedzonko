<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../../../header.jspf" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <%@include file="../../../sidepanel.jsp" %>

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">DODAJ PRZEPIS DO PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="javascript:document.formularz.submit();" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <form name="formularz" action="/app/recipe/plan/add" method="post">

                        <div class="form-group row">
                            <label for="1" class="col-sm-2 label-size col-form-label">
                                Wybierz plan
                            </label>
                            <div class="col-sm-3">
                                <select class="form-control" id="1" name="chosenPlan">
                                    <c:forEach items="${userPlans}" var="plan">
                                        <option value="${plan.id}">${plan.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="2" class="col-sm-2 label-size col-form-label">
                                Nazwa posiłku
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" value="" id="2" name="mealName" placeholder="np.: śniadanie, kolacja, itp.">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="3" class="col-sm-2 label-size col-form-label">
                                Numer posiłku
                            </label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control" value="" id="3" name="mealOrder" placeholder="np.: 1, 2, 3 itd.">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="4" class="col-sm-2 label-size col-form-label">
                                Przepis
                            </label>
                            <div class="col-sm-4">
                                <select class="form-control" id="4" name="chosenRecipe">
                                    <c:forEach items="${userRecipes}" var="recipe">
                                        <option value="${recipe.id}">${recipe.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="5" class="col-sm-2 label-size col-form-label">
                                Dzień
                            </label>
                            <div class="col-sm-2">
                                <select class="form-control" id="5" name="chosenDay">
                                    <c:forEach items="${daysList}" var="day">
                                        <option value="${day[0]}">${day[1]}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <c:if test ="${not empty message}">
                            <h3 style="color: red">${message}</h3>
                        </c:if>
                        <c:if test ="${not empty ok}">
                            <h3 style="color: green">${ok}</h3>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="../../../footer.jspf" %>

