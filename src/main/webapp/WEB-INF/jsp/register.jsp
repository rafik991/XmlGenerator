<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<jsp:include page="head.jsp"></jsp:include>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<div id="content">
		<div id="mainContent">
		<%@ include file="login.jspf"%></div>
		<jsp:include page="sidebar.jsp"></jsp:include>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>