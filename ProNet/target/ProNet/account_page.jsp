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
        <h4 class="mx-3 mt-2"><fmt:message key="account_page_jsp.h4.account"/></h4>
        <div class="card bg-light mx-3 my-2" style="max-width: 18rem;">
          <div class="card-header"><fmt:message key="account_page_jsp.card.balance"/></div>
          <div class="card-body">
            <h5 class="card-title">${requestScope.account.balance} UAH</h5>
          </div>
        </div>

        <form action="controller" method="post">
            <input name="command" value="refillUserAccount" type="hidden">
            <input name="userId" value="${requestScope.account.userId}" type="hidden">
            <label class="mx-3" for="sumInput"><fmt:message key="account_page_jsp.form.label.enter_amount"/></label>
            <div class="input-group mb-2 mx-3" id="sumInput" style="max-width: 18rem;">
                <div class="input-group-prepend">
                    <span class="input-group-text">UAH</span>
                </div>
                <input class="form-control" name="sum" type="number" step="1" min="0" max="10000" aria-label="Amount">
                <div class="input-group-append">
                    <span class="input-group-text">.00</span>
                </div>
            </div>
            <input class="btn btn-info mx-3" value="<fmt:message key='account_page_jsp.form.input.replenish'/>" type="submit">
        </form>
        <ml:footer/>
    </body>
</html>