<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<li><a href="index">Strona domowa</a></li>
		<li><a href="templateList">Lista szablonow</a></li>

		<li><a href="addTemplate">Dodaj szablon</a></li>
		<li><a href="help">Pomoc</a></li>
		<!-- li><a href="<%out.print(bsrl);%>/uploadFile" >Dodaj plik Excel</a></li-->
        <li><a
               href="login">Zaloguj</a>  </li>

        </li>
	</ul>
</nav>
<!--[if IE ]>
</div>
<![endif]-->