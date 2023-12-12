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

        <h4 class="mx-3 my-2"><fmt:message key="register_page_jsp.h4.register_form"/></h4>
        <form class="mx-3" action="controller" method="post">
            <input name="command" value="createUser" type="hidden">

            <div class="form-group">
                <label for="login"><fmt:message key="register_page_jsp.form.label.login"/></label>
                <input name="login" class="form-control" id="login" placeholder="<fmt:message key='register_page_jsp.form.label.login'/>" autocomplete="off" autofocus required>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="password"><fmt:message key="register_page_jsp.form.label.password"/></label>
                    <input name="password" class="form-control" id="password" placeholder="<fmt:message key='register_page_jsp.form.label.password'/>" type="password" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="confirm_password"><fmt:message key="register_page_jsp.form.label.password_confirmation"/></label>
                    <input name="confirmation" class="form-control" id="confirm_password" placeholder="<fmt:message key='register_page_jsp.form.label.password_confirmation'/>" type="password" required>
                </div>
            </div>
            <hr style="background:#17a2b8">
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="firstName"><fmt:message key="register_page_jsp.form.label.first_name"/></label>
                    <input name="firstName" class="form-control" id="firstName" placeholder="<fmt:message key='register_page_jsp.form.label.first_name'/>" value="${requestScope.userRequest.userDetails.firstName}" required>
                </div>
                <div class="form-group col-md-6">
                    <label for="lastName"><fmt:message key="register_page_jsp.form.label.last_name"/></label>
                    <input name="lastName" class="form-control" id="lastName" placeholder="<fmt:message key='register_page_jsp.form.label.last_name'/>" value="${requestScope.userRequest.userDetails.lastName}" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group col-md-6">
                    <label for="email"><fmt:message key="register_page_jsp.form.label.email"/></label>
                    <input name="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="<fmt:message key='register_page_jsp.form.label.email'/>" type="email" value="${requestScope.userRequest.userDetails.email}" required>
                    <small id="emailHelp" class="form-text text-muted"><fmt:message key="register_page_jsp.form.small.format"/> email@example.com</small>
                </div>
                <div class="form-group col-md-6">
                    <label for="phoneNumber"><fmt:message key="register_page_jsp.form.label.phone_number"/></label>
                    <input name="phoneNumber" class="form-control" id="phoneNumber" aria-describedby="phoneHelp" placeholder="<fmt:message key='register_page_jsp.form.label.phone_number'/>" type="tel" value="${requestScope.userRequest.userDetails.phoneNumber}"
                            pattern="[+][0-9]{2}[(][0-9]{3}[)][0-9]{7}" required>
                    <small id="phoneHelp" class="form-text text-muted"><fmt:message key="register_page_jsp.form.small.format"/> +11(222)3334455</small>
                </div>
            </div>
            <div class="form-group">
                <label for="address"><fmt:message key="register_page_jsp.form.label.address"/></label>
                <input name="address" class="form-control" id="address" aria-describedby="addressHelp" placeholder="<fmt:message key='register_page_jsp.form.label.address'/>" value="${requestScope.userRequest.userDetails.address}" required>
                <small id="addressHelp" class="form-text text-muted"><fmt:message key="register_page_jsp.form.small.format"/>  Example St. 10, Apt. 10</small>
            </div>

            <input name="requestId" type="hidden" value="${requestScope.userRequest.id}">
            <input name="planCost" type="hidden" value="${requestScope.userRequest.planCost}">
            <button class="btn btn-info" type="submit"><fmt:message key="register_page_jsp.form.button.register"/></button>
        </form>
        <script>
            var password = document.getElementById("password"),
                confirm_password = document.getElementById("confirm_password");

            function validatePassword(){
              if(password.value != confirm_password.value) {
                confirm_password.setCustomValidity("Passwords Don't Match");
              } else {
                confirm_password.setCustomValidity('');
              }
            }

            password.onchange = validatePassword;
            confirm_password.onkeyup = validatePassword;
        </script>

        <ml:footer/>
    </body>
</html>