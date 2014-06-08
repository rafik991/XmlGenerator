<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <h1>Xml generator</h1>
</header>
<!--[if IE ]>
<div class="ie" >
<![endif]-->


<nav class="okragle-wszystkie-rogi">
    <ul>
        <%
            String bsrl = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort();
            String[] s = request.getRequestURI().split("/");
            bsrl += "/" + s[1];
        %>
        <li><a href="/mvc/app/index">Home page</a></li>
        <li><a href="/mvc/app/templateList">Template list</a></li>

        <li><a href="/mvc/app/addTemplate">Add template</a></li>
        <li><a href="/mvc/app/help">Help</a></li>
        <li>
            <c:choose>
            <c:when test="${not empty pageContext.request.userPrincipal}">
                Welcome <c:out value="${pageContext.request.userPrincipal.name}"/>!
                <a href="<c:url value="/mvc/auth/sign_out" />">Logout</a>
            </c:when>
            <c:otherwise>
            <a
                    href="/mvc/auth/login">Zaloguj</a></li>

        </c:otherwise>
        </c:choose>

        </li>
    </ul>
</nav>
<!--[if IE ]>
</div>
<![endif]-->