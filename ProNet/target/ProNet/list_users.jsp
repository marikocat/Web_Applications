<%@ include file="/WEB-INF/include/head.jspf"%>

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
                        <th scope="col">ID</th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.login"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.password"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.role"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.first_name"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.last_name"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.email"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.phone_number"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.address"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.balance"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.update"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.delete"/></th>
                        <th scope="col"><fmt:message key="list_users_jsp.table.th.show_plan"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${requestScope.users}">
                        <tr>
                            <th scope="row">${user.id}</th>
                            <td>${user.login}</td>
                            <td>${user.password}</td>
                            <td>${user.role}</td>
                            <td>${user.userDetails.firstName}</td>
                            <td>${user.userDetails.lastName}</td>
                            <td>${user.userDetails.email}</td>
                            <td>${user.userDetails.phoneNumber}</td>
                            <td>${user.userDetails.address}</td>
                            <td>${user.account.balance} UAH</td>
                            <td>
                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#${user.id}"><fmt:message key="list_users_jsp.table.td.button.update_user"/></button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#${user.id}d"><fmt:message key="list_users_jsp.table.td.button.delete_user"/></button>
                            </td>
                            <c:if test="${user.role != 'ADMINISTRATOR'}">
                                <td>
                                    <a href="controller?command=showTariffPlan&userId=${user.id}">
                                        <button type="button" class="btn btn-info"><fmt:message key="list_users_jsp.table.td.button.show_tariff_plan"/></button>
                                    </a>
                                </td>
                            </c:if>
                        </tr>

                        <!-- Modal Update User -->
                        <div class="modal fade" id="${user.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModal1Label" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModal1Label">Update User</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <h4>Update User Form</h4>
                                        <small>Alter the fields to be changed.</small>
                                        <form action="controller" method="post">
                                            <input name="command" value="updateUser" type="hidden">
                                            <input name="userId" value="${user.id}" type="hidden">
                                            <input name="userRole" value="${user.role}" type="hidden">
                                            <input name="page" value="${page}" type="hidden">

                                            <label>LogIn Credentials:</label><br>
                                            <input name="login" placeholder="Login" autocomplete="off" value="${user.login}" required><br>
                                            <input name="password" placeholder="Password" type="password" id="password" value="${user.password}" required><br>
                                            <input name="confirmation" placeholder="Password confirmation" type="password" id="confirm_password" value="${user.password}" required><br>

                                            <c:if test="${user.role == 'SUBSCRIBER'}">
                                                <label>User Info:</label><br>
                                                <input name="firstName" placeholder="First Name" value="${user.userDetails.firstName}" required><br>
                                                <input name="lastName" placeholder="Last Name" value="${user.userDetails.lastName}" required><br>
                                                <input name="email" placeholder="Email" type="email" value="${user.userDetails.email}" required><br>
                                                <input name="phoneNumber" placeholder="Phone Number" value="${user.userDetails.phoneNumber}" type="tel"
                                                        pattern="[+][0-9]{2}[(][0-9]{3}[)][0-9]{7}" required><br>
                                                <small>Format: +38(123)5554466</small><br>
                                                <input name="address" placeholder="Address" value="${user.userDetails.address}" required><br>
                                                <small>Format: Example St. 15, Apt. 10</small><br>
                                            </c:if>

                                            <button class="btn btn-info" type="submit">Update</button>
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
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Modal Delete User -->
                        <div class="modal fade" id="${user.id}d" tabindex="-1" role="dialog" aria-labelledby="exampleModal2Label" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModal2Label">Delete User</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Are you sure you would like to delete the user?</p>
                                        <small>You will be able to delete the user only if there is no money on their account.</small>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <a href="controller?command=deleteUser&userId=${user.id}&balance=${user.account.balance}">
                                            <button type="button" class="btn btn-info">Delete</button>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="text-center mx-auto">
            <fmt:message key="list_users_jsp.page"/> ${page} <fmt:message key="list_users_jsp.of"/> ${pageCount}

            |

            <c:choose>
                <c:when test="${page - 1 > 0}">
                    <a href="controller?command=listUsers&page=${page-1}&pageSize=${pageSize}">
                        <button class="btn btn-info"><fmt:message key="list_users_jsp.anchor.button.previous"/></button>
                    </a>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-secondary" disabled><fmt:message key="list_users_jsp.anchor.button.previous"/></button>
                </c:otherwise>
            </c:choose>

            <c:forEach var="p" begin="${minPossiblePage}" end="${maxPossiblePage}">
                <c:choose>
                    <c:when test="${page == p}">
                        <button class="btn btn-secondary" disabled>${p}</button>
                    </c:when>
                    <c:otherwise>
                        <a href="controller?command=listUsers&page=${p}&pageSize=${pageSize}">
                            <button class="btn btn-info">${p}</button>
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${page + 1 <= pageCount}">
                    <a href="controller?command=listUsers&page=${page+1}&pageSize=${pageSize}">
                        <button class="btn btn-info"><fmt:message key="list_users_jsp.anchor.button.next"/></button>
                    </a>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-secondary" disabled><fmt:message key="list_users_jsp.anchor.button.next"/></button>
                </c:otherwise>
            </c:choose>

            |

            <form action="controller" style='display:inline;'>
                <input name="command" value="listUsers" type="hidden" />
                <select name="page">
                    <c:forEach begin="1" end="${pageCount}" var="p">
                        <option value="${p}" ${p == param.page ? 'selected' : ''}>${p}</option>
                    </c:forEach>
                </select>
                <input name="pageSize" value="${pageSize}" type="hidden" />
                <button class="btn btn-info" type="submit"><fmt:message key="list_users_jsp.form.button.go"/></button>
            </form>
        </div>

        <ml:footer/>
    </body>
</html>