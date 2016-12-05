<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script type="text/javascript">
$(function(){
	alert("${resultJson.rMsg}");
	$(location).attr("href", "${resultJson.goUrl}");
});
</script>	
</head>

<body>
</body>
</html>
