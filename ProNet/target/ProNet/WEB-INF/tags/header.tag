<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
    <c:choose>
        <c:when test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
            <a class="navbar-brand" href="controller?command=showAdminPage">
                <img src="images/pro_logo.png" height="22" class="d-inline-block align-bottom" alt="logo">
                Net
            </a>
        </c:when>
        <c:otherwise>
            <a class="navbar-brand" href="index.jsp">
                <img src="images/pro_logo.png" height="22" class="d-inline-block align-bottom" alt="logo">
                Net
            </a>
        </c:otherwise>
    </c:choose>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-lable="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
            <c:choose>
                <c:when test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                    <a class="nav-link" href="controller?command=showAdminPage">Home<span class="sr-only">(current)</span></a>
                </c:when>
                <c:when test="${sessionScope.currentUser.role == 'SUBSCRIBER'}">
                    <a class="nav-link" href="controller?command=showSubscriberPage">Home<span class="sr-only">(current)</span></a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="index.jsp">Home<span class="sr-only">(current)</span></a>
                </c:otherwise>
            </c:choose>
            </li>

            <c:choose>
                <c:when test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listTariffsByPage&page=1">Tariffs</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listUsers&page=1&pageSize=3">Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listRequests">Requests</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listServices&serviceId=1&sort=3">All Services</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listTariffsByService&serviceId=1&sort=3">Telephone</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listTariffsByService&serviceId=2&sort=3">Internet</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listTariffsByService&serviceId=3&sort=3">Cable TV</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=listTariffsByService&serviceId=4&sort=3">IP-TV</a>
                    </li>
                </c:otherwise>
            </c:choose>

            <c:if test="${sessionScope.currentUser.role == 'SUBSCRIBER'}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Support
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="controller?command=showTariffPlan&userId=${sessionScope.currentUser.id}">My Tariff Plan</a>
                        <a class="dropdown-item" href="controller?command=showUserAccount&userId=${sessionScope.currentUser.id}">My Account</a>
                        <div class="dropdown-item"></div>
                        <a class="dropdown-item" href="subscriber_page.jsp">My Profile</a>
                    </div>
                </li>
            </c:if>
        </ul>
        <ul class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${sessionScope.currentUser != null}">
                    <c:choose>
                        <c:when test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
                            <a class="nav-link" href="controller?command=showRegisterForm">Register User</a>
                        </c:when>
                        <c:otherwise>
                            <a href="controller?command=showCart">
                                <img src="images/cart2.png" height="32" class="d-inline-block align-bottom" alt="Cart">
                            </a>
                            <c:if test="${sessionScope.cart != null}">
                                <span style="color:white;">${sessionScope.cart.size()}</span>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    <a class="nav-link" href="controller?command=logOut">Log Out</a>
                </c:when>
                <c:otherwise>
                    <a href="controller?command=showCart">
                        <img src="images/cart2.png" height="32" class="d-inline-block align-bottom" alt="Cart">
                    </a>
                    <c:if test="${sessionScope.cart != null}">
                        <span style="color:white;">${sessionScope.cart.size()}</span>
                    </c:if>
                    <a class="nav-link" href="controller?command=showLoginForm">Log In</a>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>