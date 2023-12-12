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
        <%-- --%>
        <form action="controller" method="post">
            <input name="command" value="logIn" type="hidden">
            <div class="text-center mx-auto mt-3" style="width: 50%">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroup-sizing-default"><fmt:message key="login_page_jsp.form.span.login"/></span>
                    </div>
                    <input name="login" type="text" class="form-control" aria-label="Login" aria-describedby="inputGroup-sizing-default">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="inputGroup-sizing-default"><fmt:message key="login_page_jsp.form.span.password"/></span>
                    </div>
                    <input name="password" type="password" class="form-control" aria-label="Password" aria-describedby="inputGroup-sizing-default">
                </div>
                <input value="<fmt:message key='login_page_jsp.form.input.log_in'/>" type="submit" class="btn btn-info btn-block">
            </div>
        </form>
        <c:if test="${requestScope.message != null}">
            <div class="alert alert-warning text-center mt-5" role="alert">
                <h6>${requestScope.message}</h6>
            </div>
        </c:if>
        <ml:footer/>
    </body>
</html>
