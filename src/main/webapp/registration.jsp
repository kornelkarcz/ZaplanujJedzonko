<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="/header.jspf" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding"><h3 class="color-header text-uppercase">Lista Przepisów</h3></div>
                </div>
                <table class="table border-bottom schedules-content">
                    <thead>
                    <tr class="d-flex text-color-darker">
                        <th scope="col" class="col-1">ID</th>
                        <th scope="col" class="col-2">NAZWA</th>
                        <th scope="col" class="col-7">OPIS</th>
                    </tr>
                    </thead>
                    <tbody class="text-color-lighter">

                    <c:forEach begin="0" step="1" end="${recipes.size()-1}" var="number">

                        <tr class="d-flex">
                            <th scope="row" class="col-1">${recipes.get(number).getId()}</th>
                            <td class="col-2">
                                    ${recipes.get(number).getName()}
                            </td>
                            <td clas="col-7">
                                    ${recipes.get(number).getDescription()}
                            </td>
                        </tr>

                    </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>


    </div>
</section>

<%@include file="/footer.jspf" %>

