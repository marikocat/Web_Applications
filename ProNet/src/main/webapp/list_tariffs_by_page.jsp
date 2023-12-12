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

        <div class="mx-3 my-2">
            <form action="controller" style='display:inline;'>
                <input name="command" value="listTariffsByPage" type="hidden" />
                <select name="page">
                    <c:forEach begin="1" end="${pageCount}" var="p">
                        <option value="${p}" ${p == param.page ? 'selected' : ''}>
                            <c:choose>
                                <c:when test="${p == 1}"><fmt:message key="list_services_jsp.button.telephone"/></c:when>
                                <c:when test="${p == 2}"><fmt:message key="list_services_jsp.button.internet"/></c:when>
                                <c:when test="${p == 3}"><fmt:message key="list_services_jsp.button.cable_tv"/></c:when>
                                <c:when test="${p == 4}"><fmt:message key="list_services_jsp.button.ip_tv"/></c:when>
                            </c:choose>
                        </option>
                    </c:forEach>
                </select>
                <button class="btn btn-info" type="submit"><fmt:message key="list_tariffs_by_page_jsp.form.button.go"/></button>
            </form>
        </div>

        <hr class="mx-3">
        <h4 class="mx-3">${requestScope.tariffs.get(0).getService().name}</h4>
        <hr class="mx-3">

        <div class="mx-3 my-2">
            <c:forEach var="tariff" items="${requestScope.tariffs}">
                <form action="controller" style='display:inline;'>
                    <input name="command" value="editTariff" type="hidden">
                    <input name="tariffId" value="${tariff.id}" type="hidden">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="">${tariff.name}</span>
                        </div>
                        <input name="description" type="text" class="form-control" value="${tariff.description}">
                        <div class="input-group-append">
                            <input name="cost" type="text" class="form-control" value="${tariff.cost}">
                            <input type="submit" class="btn btn-outline-secondary" value="<fmt:message key='list_tariffs_by_page_jsp.form.input.edit'/>">
                        </div>
                    </div>
                </form>
            </c:forEach>
        </div>
        <div class="text-center mx-auto my-2">
            <fmt:message key="list_tariffs_by_page_jsp.page"/> ${page} <fmt:message key="list_tariffs_by_page_jsp.of"/> ${pageCount}

            |

            <c:choose>
                <c:when test="${page - 1 > 0}">
                    <a href="controller?command=listTariffsByPage&page=${page-1}">
                        <button class="btn btn-info"><fmt:message key="list_tariffs_by_page_jsp.anchor.button.previous"/></button>
                    </a>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-secondary" disabled><fmt:message key="list_tariffs_by_page_jsp.anchor.button.previous"/></button>
                </c:otherwise>
            </c:choose>

            <c:forEach var="p" begin="${minPossiblePage}" end="${maxPossiblePage}">
                <c:choose>
                    <c:when test="${page == p}">
                        <button class="btn btn-secondary" disabled>${p}</button>
                    </c:when>
                    <c:otherwise>
                        <a href="controller?command=listTariffsByPage&page=${p}">
                            <button class="btn btn-info">${p}</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${page + 1 <= pageCount}">
                    <a href="controller?command=listTariffsByPage&page=${page+1}">
                        <button class="btn btn-info"><fmt:message key="list_tariffs_by_page_jsp.anchor.button.next"/></button>
                    </a>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-secondary" disabled><fmt:message key="list_tariffs_by_page_jsp.anchor.button.next"/></button>
                </c:otherwise>
            </c:choose>

            |
        </div>
        <ml:footer/>
    </body>
</html>