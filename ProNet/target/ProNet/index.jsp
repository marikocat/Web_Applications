<%@ page import="javax.servlet.jsp.jstl.core.Config"%>
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

        <div class="jumbotron mx-3 my-3">
            <h1 class="display-4"><fmt:message key="index-jsp.h1.header"/></h1>
            <p class="lead"><fmt:message key="index_jsp.p.we_do_our_best"/></p>
            <hr class="my-4">
            <p><fmt:message key="index_jsp.p.get_a_discount"/></p>
            <ul>
                <li>20% - <fmt:message key="index_jsp.li.for_2"/></li>
                <li>30% - <fmt:message key="index_jsp.li.for_3"/></li>
                <li>40% - <fmt:message key="index_jsp.li.for_4"/></li>
            </ul>
            <p class="lead">
                <a class="btn btn-info btn-lg" href="controller?command=listServices&serviceId=1&sort=3" role="button"><fmt:message key="index_jsp.anchor.learn_more"/></a>
            </p>
        </div>

        <div class="mx-3 my-3 font-italic">
            <hr>
            <mlct:display name="${sessionScope.currentUser.userDetails.firstName}"/>
            <hr>
        </div>

        <ml:footer/>
    </body>
</html>
