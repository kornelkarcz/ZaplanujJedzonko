<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="../../header.jspf" %>

<section class="dashboard-section">
    <div class="row dashboard-nowrap">

        <%@include file="../../sidepanel.jsp" %>

        <div class="m-4 p-3 width-medium">
            <div class="dashboard-content border-dashed p-3 m-4 view-height">
                <div class="row border-bottom border-3 p-1 m-1">
                    <div class="col noPadding">
                        <h3 class="color-header text-uppercase">EDYCJA PLANU</h3>
                    </div>
                    <div class="col d-flex justify-content-end mb-2 noPadding">
                        <a href="javascript:document.formularz.submit();" class="btn btn-success rounded-0 pt-0 pb-0 pr-4 pl-4">Zapisz</a>
                    </div>
                </div>

                <div class="schedules-content">
                    <form action="/app/plan/edit" method="post" name="formularz">
                        <div class="form-group row">
                            <label for="planName" class="col-sm-2 label-size col-form-label">
                                Nazwa planu
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="planName" placeholder="Nazwa planu" name="planName" value="${planFromDB.name}">
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="planDescription" class="col-sm-2 label-size col-form-label">
                                Opis planu
                            </label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="5" id="planDescription" placeholder="Opis planu"
                                          name="planDescription">${planFromDB.description}</textarea>
                            </div>
                            <input name="planId" hidden value="${planFromDB.id}">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="../../footer.jspf" %>
>