<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>

$(function(){
// 	$("#wrapper").hi
});

</script>





</head>

<body>

	<div id="wrapper">

	<c:out value="${requestXml }" />
	
	<c:forEach items="${responseAddressBook.user }" var="result">
		${ result.name} || ${ result.phone} || ${ result.address} <br><br>
<%-- 		<c:forEach items="${result.emails }" var="result_email"> --%>
<%-- 			${ result_email.id} || ${ result_email.emailAddr}<br> --%>
<%-- 		</c:forEach> --%>
	</c:forEach>
	
	

	</div>

</body>

</html>
