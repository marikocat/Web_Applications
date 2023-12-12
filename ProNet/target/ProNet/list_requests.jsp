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
            <table class="table">
                <thead class="thead-light">
                    <tr>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.request_number"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.first_name"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.last_name"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.email"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.phone_number"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.address"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.tariff_plan_cost"/></th>
                        <th scope="col"><fmt:message key="list_requests_jsp.table.th.options"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="userRequest" items="${requestScope.requests}">
                        <tr>
                            <th scope="row">${userRequest.id}</th>
                            <td>${userRequest.userDetails.getFirstName()}</td>
                            <td>${userRequest.userDetails.getLastName()}</td>
                            <td>${userRequest.userDetails.getEmail()}</td>
                            <td>${userRequest.userDetails.getPhoneNumber()}</td>
                            <td>${userRequest.userDetails.getAddress()}</td>
                            <td>${userRequest.planCost} UAH</td>
                            <td>
                            <div style="display:flex;justify-content:flex-start;">
                                <a class="mr-3" href="controller?command=fillRegisterForm&requestId=${userRequest.id}">
                                    <button type="button" class="btn btn-secondary"><fmt:message key="list_requests_jsp.table.td.button.register_user"/></button>
                                </a>
                                <form action="controller" method="post">
                                    <input name="command" value="deleteRequest" type="hidden">
                                    <input name="requestId" value="${userRequest.id}" type="hidden">
                                    <button class="btn btn-secondary" type="submit"><fmt:message key="list_requests_jsp.table.td.form.button.delete_user"/></button>
                                </form>
                            </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        <ml:footer/>
    </body>
</html>