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

    <body onload="load('controller?command=listTariffsByService&serviceId=${requestScope.serviceId}&header=disabled&sort=${requestScope.sort}');">
        <ml:header/>

        <ul class="nav nav-pills nav-fill my-3">
            <c:forEach var="service" items="${requestScope.services}">
            <li class="nav-item">
                <button onclick="info('controller?command=listTariffsByService&serviceId=${service.id}&header=disabled&sort=${requestScope.sort}');" type="button" class="btn btn-dark btn-block">
                    <c:choose>
                        <c:when test="${service.id == 1}"><fmt:message key="list_services_jsp.button.telephone"/></c:when>
                        <c:when test="${service.id == 2}"><fmt:message key="list_services_jsp.button.internet"/></c:when>
                        <c:when test="${service.id == 3}"><fmt:message key="list_services_jsp.button.cable_tv"/></c:when>
                        <c:when test="${service.id == 4}"><fmt:message key="list_services_jsp.button.ip_tv"/></c:when>
                    </c:choose>
                </button>
            </li>
            </c:forEach>
        </ul>

        <div id="infodiv"/>

        <script>
            function load(name) {
                if (name == '')
                    return;

                var ajax = new XMLHttpRequest();
                ajax.onreadystatechange = function() {
                    if (ajax.readyState == 4 && ajax.status == 200) {
                        $('#infodiv').html(ajax.responseText);
                    }
                }

                ajax.open('GET', name, true);
                ajax.send();
            }

            function info(name) {
                if (name == '')
                    return;

                var ajax = new XMLHttpRequest();
                ajax.onreadystatechange = function() {
                    if (ajax.readyState == 4 && ajax.status == 200) {
                        $('#infodiv').html(ajax.responseText);
                    }
                }

                ajax.open('GET', name, true);
                ajax.send();
            }
        </script>
    </body>
</html>