<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<style>
	
	body{
		color:white;
		width: 100%;
		height: 100%;
		margin: auto;
		background-color: black;
	}
	
	#wrapper ul{margin: 30px;	padding:5px;	border: 5px dotted red;}
	#wrapper ul li{list-style-type: circle; margin-bottom: 10px;}
	
</style>

</head>

<body>


	<div id="wrapper">
		<ul>
			<li id="/sample/youtube.mee">유투브 재생</li>
			<li id="stream.jsp">카페24스트리밍</li>
			<li id="/sample/bookEffect.mee">책효과</li>
			<li id="/sample/zipAddress.mee">우편번호</li>
			<li id="/sample/bookEffectSample.mee">책효과_샘플</li>
		</ul>
		
		<ul>
			<li id="/sample/jaxbcontext.mee">jaxbcontext을 이용한 통신</li>
			<li id="/sample/jqueryUI.mee">jQuery UI sample</li>
			<li id="/sample/paramMap.mee?searchValue=&param2=bbb&param3=ccc&param4=ddd">paramMap</li>
		</ul>
		
		<ul>
			<li id="/sample/newException.mee?loginId=&userPw=">Exception 발생</li>
		</ul>

		
	</div>

<script>

	$("ul li").on("click", function(){
		window.open($(this).attr("id"));
	});

</script>

</body>

</html>
