<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<%-- <c:import url="/include.mee?fileName=/admin/include/style"/> --%>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

<style>
#toggle {
  width: 100px;
  height: 100px;
  background: #ccc;
}
</style>

<c:import url="/include.mee?fileName=/admin/include/script"/>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


</head>

<body>
<ul id="sortable">
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
  <li>Item 4</li>
  <li>Item 5</li>
</ul>

<p>Click anywhere to toggle the box.</p>
<div id="toggle"></div>



<script>
$( document ).click(function() {
  $( "#toggle" ).toggle( "blind" );
});
$("#sortable").sortable();
</script>

</body>

</html>
