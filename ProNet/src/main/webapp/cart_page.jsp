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
        <h5 class="mx-3 my-2"><fmt:message key="cart_jsp.h5.header"/></h5>
        <div class="mx-3">
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col"><fmt:message key="cart_jsp.th.tariff_name"/></th>
                    <th scope="col"><fmt:message key="cart_jsp.th.tariff_price"/></th>
                    <th scope="col"><fmt:message key="cart_jsp.th.delete"/></th>
                </tr>
            </thead>
            <c:forEach var="item" items="${sessionScope.cart}">
                <tr>
                    <td scope="row">${item.name}</td>
                    <td>${item.cost} UAH</td>
                    <td>
                        <form action="controller" method="post">
                            <input name="command" value="removeFromCart" type="hidden">
                            <input name="tariffId" value="${item.id}" type="hidden">
                            <input value="<fmt:message key='cart_jsp.input.delete_from_cart'/>" type="submit">
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <thead class="thead-light">
                <tr>
                    <th scope="col"><fmt:message key="cart_jsp.th.total_price"/></td>
                    <th scope="col">${sessionScope.totalCost} UAH</td>
                    <th scope="col"></td>
                </tr>
            </thead>
        </table>
        </div>
        <h6 class="mx-3"><fmt:message key="cart_jsp.h6.discount"/></h6>
        <!-- Button trigger modal -->
        <c:choose>
            <c:when test="${sessionScope.cart != null}">
                <div class="progress mx-3 my-2">
                  <div class="progress-bar bg-info" role="progressbar" style="width: ${cart.size()*25}%" aria-valuenow="${cart.size()*25}" aria-valuemin="0" aria-valuemax="100">${cart.size()>1?cart.size()*10:0}%</div>
                </div>
                <button type="button" class="btn btn-info ml-3 my-2" data-toggle="modal" data-target="#addPlanModal">
                    <fmt:message key="cart_jsp.button.connect_tariff_plan"/>
                </button>
            </c:when>
            <c:otherwise>
                <div class="progress mx-3 my-2">
                  <div class="progress-bar bg-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                </div>
                <button type="button" class="btn btn-info disabled ml-3 my-2" data-toggle="modal" data-target="#emptyCartModal">
                    <fmt:message key="cart_jsp.button.connect_tariff_plan"/>
                </button>
            </c:otherwise>
        </c:choose>

        <br>
        <a class="mx-3" href="controller?command=listServices&serviceId=1&sort=3"><fmt:message key="cart_jsp.anchor.continue_shopping"/></a>
        <br>


        <!-- Add Plan Modal -->
        <div class="modal fade" id="addPlanModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalTitle"><fmt:message key="cart_jsp.modal.h5.connect_new_plan"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <c:choose>
                        <c:when test="${sessionScope.currentUser != null}">
                            <div class="modal-body">
                                <h6><b><fmt:message key="cart_jsp.modal.h6.note"/></b></h6>
                                <ul>
                                    <li><b><fmt:message key="cart_jsp.modal.li.note1"/></b></li>
                                    <li><fmt:message key="cart_jsp.modal.li.note2"/></li>
                                    <li><fmt:message key="cart_jsp.modal.li.note3"/></li>
                                </ul>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="cart_jsp.modal.button.close"/></button>
                                <form action="controller" method="post">
                                    <input name="command" value="createTariffPlan" type="hidden">
                                    <input name="userId" value="${sessionScope.currentUser.id}" type="hidden">
                                    <button class="btn btn-info" type="submit"><fmt:message key="cart_jsp.modal.button.connect"/></button>
                                </form>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="modal-body">
                                <h6><b><fmt:message key="cart_jsp.modal.h6.note"/></b></h6>
                                <ul>
                                    <li><fmt:message key="cart_jsp.modal.li.note4"/></li>
                                    <li><fmt:message key="cart_jsp.modal.li.note5"/></li>
                                    <li><b><fmt:message key="cart_jsp.modal.li.note6"/></b></li>
                                </ul>
                                <br>
                                <hr>
                                <p><fmt:message key="cart_jsp.modal.p.log_in_first"/></p>
                                <a href="controller?command=showLoginForm">
                                    <button type="button" class="btn btn-dark"><fmt:message key="cart_jsp.modal.button.log_in"/></button>
                                </a>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="cart_jsp.modal.button.close"/></button>
                                <a href="controller?command=showRequestForm">
                                    <button type="button" class="btn btn-info"><fmt:message key="cart_jsp.modal.button.make_request"/></button>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Empty Cart Modal -->
        <div class="modal fade" id="emptyCartModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="cart_jsp.modal.h5.empty_cart"/></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p><fmt:message key="cart_jsp.modal.p.cart_is_empty"/></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="cart_jsp.modal.button.close"/></button>
                    </div>
                </div>
            </div>
        </div>
        <ml:footer/>
    </body>
</html>