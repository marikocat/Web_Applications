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

        <h4 class="mx-3 mt-2"><fmt:message key="subscriber_page_jsp.h4.user_profile"/></h4>
        <hr class="mx-3">
        <ul class="list-group mx-3">
            <li class="list-group-item list-group-item-info"><fmt:message key="subscriber_page_jsp.li.login"/> ${sessionScope.currentUser.login}</li>
            <li class="list-group-item list-group-item-info"><fmt:message key="subscriber_page_jsp.li.password"/> ${sessionScope.currentUser.password}</li>
            <li class="list-group-item"><fmt:message key="subscriber_page_jsp.li.first_name"/> ${sessionScope.currentUser.userDetails.getFirstName()}</li>
            <li class="list-group-item"><fmt:message key="subscriber_page_jsp.li.last_name"/> ${sessionScope.currentUser.userDetails.getLastName()}</li>
            <li class="list-group-item"><fmt:message key="subscriber_page_jsp.li.email"/> ${sessionScope.currentUser.userDetails.getEmail()}</li>
            <li class="list-group-item"><fmt:message key="subscriber_page_jsp.li.phone_number"/> ${sessionScope.currentUser.userDetails.getPhoneNumber()}</li>
            <li class="list-group-item"><fmt:message key="subscriber_page_jsp.li.address"/> ${sessionScope.currentUser.userDetails.getAddress()}</li>
        </ul>

        <ml:footer/>
    </body>
</html>