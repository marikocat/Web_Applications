<%@ include file="/WEB-INF/include/head.jspf"%>

<!doctype html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>ProNet</title>
    </head>
    <body>
        <ml:header/>
        <h4 class="mx-3 mt-2"><fmt:message key="tariff_plan_jsp.h4.tariff_plan"/></h4>
        <c:if test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
            <p class="mx-3">(<fmt:message key="tariff_plan_jsp.p.user_id"/> ${requestScope.user.id}, <fmt:message key="tariff_plan_jsp.p.user_login"/> ${requestScope.user.login})</p>
        </c:if>
        <c:choose>
            <c:when test="${requestScope.tariffPlan != null}">
                <h6 class="mx-3 mt-2"><fmt:message key="tariff_plan_jsp.h6.tariff_plan_details"/></h6>
                <div class="mx-3">
                    <table class="table">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.start_date"/></th>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.end_date"/></th>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.plan_cost"/></th>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.plan_status"/></th>
                            </tr>
                        </thead>
                        <tr>
                            <td scope="row">${requestScope.tariffPlan.startDate}</td>
                            <td>${requestScope.tariffPlan.endDate}</td>
                            <td>${requestScope.tariffPlan.cost} UAH</td>
                            <th>
                                <c:choose>
                                    <c:when test="${requestScope.tariffPlan.status == 'BLOCKED'}">
                                        <fmt:message key="tariff_plan_jsp.table.th.blocked"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="tariff_plan_jsp.table.th.active"/>
                                    </c:otherwise>
                                </c:choose>
                            </th>
                        </tr>
                    </table>
                </div>
                <c:if test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                    <div class="mx-3">
                        <form action="controller" method="post">
                            <input name="command" value="changeTariffPlanStatus" type="hidden">
                            <input name="planId" value="${requestScope.tariffPlan.id}" type="hidden">
                            <input name="userId" value="${requestScope.tariffPlan.userId}" type="hidden">
                            <select name="status">
                                <option disabled selected value=""><fmt:message key="tariff_plan_jsp.select.option.select_status"/></option>
                                <c:choose>
                                    <c:when test="${requestScope.tariffPlan.status == 'BLOCKED'}">
                                        <option value="UNBLOCKED"><fmt:message key="tariff_plan_jsp.select.option.unblocked"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="BLOCKED"><fmt:message key="tariff_plan_jsp.select.option.blocked"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                            <button class="btn btn-info" type="submit"><fmt:message key="tariff_plan_jsp.button.change_status"/></button>
                        </form>
                    </div>
                </c:if>
                <h6 class="mx-3 mt-2"><fmt:message key="tariff_plan_jsp.h6.services"/></h6>
                <div class="mx-3">
                    <table class="table">
                        <thead class="thead-light">
                            <tr>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.service"/></th>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.tariff"/></th>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.details"/></th>
                                <th scope="col"><fmt:message key="tariff_plan_jsp.table.th.cost"/></th>
                            </tr>
                        </thead>
                        <c:forEach var="tariff" items="${requestScope.tariffs}">
                            <tr>
                                <th scope="row">${tariff.getService().name}</th>
                                <td>${tariff.name}</td>
                                <td>${tariff.description}</td>
                                <td>${tariff.cost} UAH</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <c:if test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                    <div class="mx-3">
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#changePlanModal"><fmt:message key="tariff_plan_jsp.button.change_plan"/></button>
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#deletePlanModal"><fmt:message key="tariff_plan_jsp.button.delete_plan"/></button>
                    </div>

                    <!-- Change Plan Modal -->
                    <div class="modal fade" id="changePlanModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalLabel"><fmt:message key="tariff_plan_jsp.modal.h5.plan_replacement"/></h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <h6><fmt:message key="tariff_plan_jsp.modal.h6.choose_services"/></h6>
                                    <small><fmt:message key="tariff_plan_jsp.modal.small.plan_will_be_replaced"/></small>
                                    <form action="controller" method="post">
                                        <input name="command" value="createTariffPlan" type="hidden">
                                        <input name="userId" value="${requestScope.tariffPlan.userId}" type="hidden">
                                        <c:forEach var="tariffs" items="${requestScope.allTariffs}">
                                            <div class="form-group mt-3">
                                                <label for="${tariffs.get(0).getService().id}">${tariffs.get(0).getService().name}</label>
                                                <select class="form-control" name="${tariffs.get(0).getService().name}" id="${tariffs.get(0).getService().id}">
                                                    <option disabled selected value=""><fmt:message key="tariff_plan_jsp.modal.select.option.select_tariff"/></option>
                                                    <c:forEach var="tariff" items="${tariffs}">
                                                        <option value="${tariff.id}">${tariff.name} - ${tariff.cost} UAH</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </c:forEach>
                                        <button class="btn btn-info" type="submit"><fmt:message key="tariff_plan_jsp.button.change_plan"/></button>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="tariff_plan_jsp.modal.button.close"/></button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Delete Plan Modal -->
                    <div class="modal fade" id="deletePlanModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalLabel"><fmt:message key="tariff_plan_jsp.modal.h5.plan_deletion"/></h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p><fmt:message key="tariff_plan_jsp.modal.p.sure_to_delete"/></p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="tariff_plan_jsp.modal.button.close"/></button>
                                    <form action="controller" method="post">
                                        <input name="command" value="deleteTariffPlan" type="hidden">
                                        <input name="userId" value="${requestScope.tariffPlan.userId}" type="hidden">
                                        <input name="planId" value="${requestScope.tariffPlan.id}" type="hidden">
                                        <button class="btn btn-info" type="submit"><fmt:message key="tariff_plan_jsp.modal.form.button.delete"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${sessionScope.currentUser.role == 'SUBSCRIBER'}">
                    <a class="mx-3" href="controller?command=downloadFile&userId=${requestScope.tariffPlan.userId}" download="tariff_plan_${requestScope.tariffPlan.id}.pdf">
                        <button class="btn btn-info"><fmt:message key="tariff_plan_jsp.modal.button.download_pdf"/></button>
                    </a>
                </c:if>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                        <p class="mx-3"><fmt:message key="tariff_plan_jsp.p.plan_does_not_exist"/></p>
                        <button type="button" class="btn btn-info mx-3" data-toggle="modal" data-target="#createPlanModal"><fmt:message key="tariff_plan_jsp.button.add_plan"/></button>

                        <!-- Create Plan Modal -->
                        <div class="modal fade" id="createPlanModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modalLabel"><fmt:message key="tariff_plan_jsp.modal.h5.plan_creation"/></h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <h6><fmt:message key="tariff_plan_jsp.modal.h6.choose_services"/></h6>
                                        <form action="controller" method="post">
                                            <input name="command" value="createTariffPlan" type="hidden">
                                            <input name="userId" value="${requestScope.userId}" type="hidden">

                                            <c:forEach var="tariffs" items="${requestScope.allTariffs}">
                                                <div class="form-group mt-3">
                                                    <label for="${tariffs.get(0).getService().id}">${tariffs.get(0).getService().name}</label><br>
                                                    <select class="form-control" name="${tariffs.get(0).getService().name}" id="${tariffs.get(0).getService().id}">
                                                        <option disabled selected value=""><fmt:message key="tariff_plan_jsp.modal.select.option.select_tariff"/></option>
                                                        <c:forEach var="tariff" items="${tariffs}">
                                                            <option value="${tariff.id}">${tariff.name} - ${tariff.cost} UAH</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </c:forEach>
                                            <button class="btn btn-info" type="submit"><fmt:message key="tariff_plan_jsp.modal.button.create_plan"/></button>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="tariff_plan_jsp.modal.button.close"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="mx-3">
                            <span><fmt:message key="tariff_plan_jsp.span.choose_tariff_plan"/> </span>
                            <a href="controller?command=listServices&serviceId=1&sort=3"><fmt:message key="tariff_plan_jsp.anchor.all_services"/></a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <ml:footer/>
    </body>
</html>