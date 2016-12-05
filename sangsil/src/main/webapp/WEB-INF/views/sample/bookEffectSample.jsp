<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<%-- <c:import url="/include.mee?fileName=/admin/include/style"/> --%>
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
	both: ['/resources/js/plugIn/magagin/basic.css'],
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
			<div style="background-image:url(/resources/images/bookSample/00000001.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000002.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000003.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000004.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000005.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000006.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000007.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000008.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000009.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000010.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000011.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000012.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000013.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000014.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000015.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000016.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000017.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000018.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000019.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000020.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000021.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000022.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000023.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000024.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000025.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000026.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000027.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000028.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000029.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000030.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000031.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000032.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000033.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000034.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000035.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000036.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000037.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000038.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000039.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000040.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000041.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000042.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000043.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000044.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000045.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000046.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000047.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000048.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000049.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000050.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000051.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000052.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000053.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000054.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000055.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000056.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000057.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000058.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000059.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000060.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000061.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000062.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000063.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000064.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000065.jpg)"></div>
			<div style="background-image:url(/resources/images/bookSample/00000066.jpg)"></div>
		</div>
	</div>
</div>
	<!-- /#wrapper -->

</body>

</html>
