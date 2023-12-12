<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<footer class="page-footer fixed-bottom font-small bg-dark pt-2 text-center text-light">
    <form class="mb-0" action="changeLocale.jsp" method="post">
        <label class="text-info"><fmt:message key="settings_jsp.label.set_locale"/>:</label>
        <select name="locale">
            <c:forEach items="${applicationScope.locales}" var="locale">
                <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
                <option value="${locale.key}" ${selected}>${locale.value}</option>
            </c:forEach>
        </select>
        <input class="btn btn-outline-info btn-sm" type="submit" value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">
    </form>

    <span>Copyright &#169; ProNet</span>
</footer>