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

        <div class="alert alert-warning" role="alert">
            <h4 class="alert-heading"><fmt:message key="error_page_jsp.h4.warning"/></h4>
            <h6>${exception.message}</h6>
            <hr>
            <p class="mb-0"><fmt:message key="error_page_jsp.p.contact_us"/></p>
        </div>

        <ml:footer/>
    </body>
</html>