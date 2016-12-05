<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script type="text/javascript" src="/resources/js/plugIn/magagin/modernizr.2.5.3.min.js"></script>

<!-- 
책 효과
http://www.turnjs.com
 -->

<script>

function loadApp() {

	// Create the flipbook
	$('.flipbook').turn({
			// Width
			width:$( window ).width(),
			// Height
			height:$( window ).height(),
			// Elevation
			elevation: 50,
			// Enable gradients
			gradients: true,
			// Auto center this flipbook
			autoCenter: true
	});
	
	$(".flipbook-viewport .flipbook").css("left", - $( window ).width()/2);
	$(".flipbook-viewport .flipbook").css("top", - $( window ).height()/2);
	
}

// Load the HTML4 version if there's not CSS transform

yepnope({
	test : Modernizr.csstransforms,
	yep: ['/resources/js/plugIn/magagin/turn.js'],
	nope: ['/resources/js/plugIn/magagin/turn.html4.min.js'],
	both: ['css/basic.css'],
	complete: loadApp
});

</script>


<style>
/* Basic sample */

body{
	overflow:hidden;
	background-color:#fcfcfc;
	margin:0;
	padding:0;
}

.flipbook-viewport{
	overflow:hidden;
	width:100%;
	height:100%;
}

.flipbook-viewport .container{
	position:absolute;
	top:50%;
	left:50%;
	margin:auto;
}

.flipbook-viewport .flipbook{
	width:100%;
	height:100%;
/* 	left:-461px; */
/* 	top:-300px; */
}

.flipbook-viewport .page{
	width:50%;
	height:100%;
	background-color:white;
	background-repeat:no-repeat;
	background-size:100% 100%;
}

.flipbook .page{
	-webkit-box-shadow:0 0 20px rgba(0,0,0,0.2);
	-moz-box-shadow:0 0 20px rgba(0,0,0,0.2);
	-ms-box-shadow:0 0 20px rgba(0,0,0,0.2);
	-o-box-shadow:0 0 20px rgba(0,0,0,0.2);
	box-shadow:0 0 20px rgba(0,0,0,0.2);
}

.flipbook-viewport .page img{
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-khtml-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	margin:0;
}

.flipbook-viewport .shadow{
	-webkit-transition: -webkit-box-shadow 0.5s;
	-moz-transition: -moz-box-shadow 0.5s;
	-o-transition: -webkit-box-shadow 0.5s;
	-ms-transition: -ms-box-shadow 0.5s;

	-webkit-box-shadow:0 0 20px #ccc;
	-moz-box-shadow:0 0 20px #ccc;
	-o-box-shadow:0 0 20px #ccc;
	-ms-box-shadow:0 0 20px #ccc;
	box-shadow:0 0 20px #ccc;
}

</style>


</head>

<body>

<div class="flipbook-viewport">
	<div class="container">
		<div class="flipbook">
			<div style="background-image:url(/resources/images/sample/magagin/1.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/2.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/3.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/4.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/5.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/6.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/7.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/8.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/9.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/10.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/11.jpg)"></div>
			<div style="background-image:url(/resources/images/sample/magagin/12.jpg)"></div>
		</div>
	</div>
</div>
	<!-- /#wrapper -->

</body>

</html>
