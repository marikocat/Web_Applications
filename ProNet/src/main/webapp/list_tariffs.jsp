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
        <c:if test="${requestScope.disabled == false}">
            <ml:header/>
        </c:if>

        <hr class="my-0">
        <h4 class="text-center my-1">${requestScope.tariffs.get(0).getService().name}</h4>
        <hr class="my-0">

        <form action="controller" class="form-inline mx-3 my-2">
            <c:choose>
                <c:when test="${requestScope.disabled == false}">
                    <input name="command" value="listTariffsByService" type="hidden" />
                </c:when>
                <c:otherwise>
                    <input name="command" value="listServices" type="hidden" />
                </c:otherwise>
            </c:choose>
            <input name="serviceId" value="${requestScope.tariffs.get(0).getService().id}" type="hidden" />
            <label><fmt:message key="list_tariffs_jsp.label.sort_by"/></label>
            <select name="sort" onchange="this.form.submit()" class="mx-2">
                <c:forEach begin="1" end="${requestScope.size}" var="s">
                    <option value="${s}" ${requestScope.sort == s ? 'selected' : ''}>
                        <c:choose>
                            <c:when test="${s == 1}"><fmt:message key="list_tariffs_jsp.option.name_ascending"/></c:when>
                            <c:when test="${s == 2}"><fmt:message key="list_tariffs_jsp.option.name_descending"/></c:when>
                            <c:when test="${s == 3}"><fmt:message key="list_tariffs_jsp.option.price"/></c:when>
                        </c:choose>
                    </option>
                </c:forEach>
            </select>
        </form>

        <c:forEach var="tariff" items="${requestScope.tariffs}">
            <div class="card mx-3 mb-2">
                <h5 class="card-header">${tariff.name}</h5>
                <div class="card-body">
                    <p class="card-text">${tariff.description}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">${tariff.cost} UAH</li>
                </ul>
                <div class="card-body">
                    <form action="controller" method="post">
                        <input name="command" value="addToCart" type="hidden">
                        <input name="tariffId" value="${tariff.id}" type="hidden">
                        <button class="btn btn-info" type="submit"><fmt:message key="list_tariffs_jsp.button.add_to_cart"/></button>
                    </form>
                </div>
            </div>
        </c:forEach>
        <br><br><br><br>
        <ml:footer/>
    </body>
</html>