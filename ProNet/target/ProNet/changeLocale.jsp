<%@ include file="/WEB-INF/include/head.jspf"%>

<%-- set the locale --%>
<fmt:setLocale value="${param.locale}" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="resources"/>

<%-- set current locale to session --%>
<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<%-- goto back to the settings--%>
<c:choose>
    <c:when test="${sessionScope.currentUser.role == 'ADMINISTRATOR'}">
        <jsp:forward page="controller?command=showAdminPage"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="index.jsp"/>
    </c:otherwise>
</c:choose>