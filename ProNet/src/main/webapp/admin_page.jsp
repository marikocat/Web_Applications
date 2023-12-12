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

        <h4 class="mx-3 mt-2"><fmt:message key="admin_page_jsp.h4.admin_page"/></h4>
        <hr class="mx-3">
        <h5 class="mx-3 mt-2"><fmt:message key="admin_page_jsp.h5.options"/></h5>
        <ul class="list-group mx-3">
            <a href="controller?command=showRegisterForm"><li class="list-group-item list-group-item-info"><fmt:message key="admin_page_jsp.ul.anchor.register_user"/></li></a>
            <a href="controller?command=listTariffsByPage&page=1"><li class="list-group-item list-group-item-secondary"><fmt:message key="admin_page_jsp.ul.anchor.list_tariffs"/></li></a>
            <a href="controller?command=listUsers&page=1&pageSize=3"><li class="list-group-item list-group-item-info"><fmt:message key="admin_page_jsp.ul.anchor.list_users"/></li></a>
            <a href="controller?command=listRequests"><li class="list-group-item list-group-item-secondary"><fmt:message key="admin_page_jsp.ul.anchor.list_requests"/></li></a>
        </ul>

        <ml:footer/>
    </body>
</html>