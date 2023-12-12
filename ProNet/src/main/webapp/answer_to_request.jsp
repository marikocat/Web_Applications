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
        <div class="jumbotron jumbotron-fluid mx-3 mt-2">
            <div class="container">
                <h1 class="display-4"><fmt:message key="answer_to_request_jsp.h1.interest"/></h1>
                <p class="lead"><fmt:message key="answer_to_request_jsp.p.request_placed"/></p>
                <ul>
                    <li><fmt:message key="answer_to_request_jsp.li.message1"/></li>
                    <li><fmt:message key="answer_to_request_jsp.li.message2"/></li>
                </ul>
            </div>
        </div>

        <ul class="list-group mx-3">
            <li class="list-group-item list-group-item-info"><fmt:message key="answer_to_request_jsp.li.personal_info"/></li>
            <li class="list-group-item list-group-item-light"><fmt:message key="answer_to_request_jsp.li.first_name"/> ${sessionScope.userRequest.userDetails.getFirstName()}</li>
            <li class="list-group-item list-group-item-light"><fmt:message key="answer_to_request_jsp.li.last_name"/> ${sessionScope.userRequest.userDetails.getLastName()}</li>
            <li class="list-group-item list-group-item-light"><fmt:message key="answer_to_request_jsp.li.email"/> ${sessionScope.userRequest.userDetails.getEmail()}</li>
            <li class="list-group-item list-group-item-light"><fmt:message key="answer_to_request_jsp.li.phone_number"/> ${sessionScope.userRequest.userDetails.getPhoneNumber()}</li>
            <li class="list-group-item list-group-item-light"><fmt:message key="answer_to_request_jsp.li.address"/> ${sessionScope.userRequest.userDetails.getAddress()}</li>
            <li class="list-group-item list-group-item-light"><fmt:message key="answer_to_request_jsp.li.tariff_plan_cost"/> ${sessionScope.userRequest.planCost} UAH</li>
        </ul>
    </body>
</html>