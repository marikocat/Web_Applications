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
        <h4 class="mx-3 my-2"><fmt:message key="request_page_jsp.h4.request_form"/></h4>
        <form class="mx-3" action="controller" method="post">
            <input name="command" value="createRequest" type="hidden">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="firstName"><fmt:message key="request_page_jsp.form.label.first_name"/></label>
                    <input name="firstName" class="form-control" id="firstName" placeholder="<fmt:message key='request_page_jsp.form.label.first_name'/>" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="lastName"><fmt:message key="request_page_jsp.form.label.last_name"/></label>
                    <input name="lastName" class="form-control" id="lastName" placeholder="<fmt:message key='request_page_jsp.form.label.last_name'/>" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="email"><fmt:message key="request_page_jsp.form.label.email"/></label>
                    <input name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="<fmt:message key='request_page_jsp.form.label.email'/>" type="email" required>
                    <small id="emailHelp" class="form-text text-muted"><fmt:message key="request_page_jsp.form.small.format"/> email@example.com</small>
                </div>
                <div class="form-group col-md-6">
                    <label for="phoneNumber"><fmt:message key="request_page_jsp.form.label.phone_number"/></label>
                    <input name="phoneNumber" class="form-control" id="phoneNumber"  aria-describedby="phoneHelp" placeholder="<fmt:message key='request_page_jsp.form.label.phone_number'/>" type="tel"
                            pattern="[+][0-9]{2}[(][0-9]{3}[)][0-9]{7}" required>
                    <small id="phoneHelp" class="form-text text-muted"><fmt:message key="request_page_jsp.form.small.format"/> +11(222)3334455</small>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="street"><fmt:message key="request_page_jsp.form.label.street"/></label>
                    <input name="street" class="form-control" id="street" placeholder="<fmt:message key='request_page_jsp.form.label.street'/>" required>
                </div>
                <div class="form-group col-md-3">
                    <label for="building"><fmt:message key="request_page_jsp.form.label.building"/></label>
                    <input name="building" class="form-control" id="building" placeholder="<fmt:message key='request_page_jsp.form.label.building'/>" required>
                </div>
                <div class="form-group col-md-3">
                    <label for="apartment"><fmt:message key="request_page_jsp.form.label.apartment"/></label>
                    <input name="apartment" class="form-control" id="apartment" placeholder="<fmt:message key='request_page_jsp.form.label.apartment'/>">
                </div>
            </div>
            <button class="btn btn-info" type="submit"><fmt:message key="request_page_jsp.form.button.send_request"/></button>
        </form>
        <ml:footer/>
    </body>
</html>