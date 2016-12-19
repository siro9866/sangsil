<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<c:import url="/include.mee?fileName=/admin/include/doctype" />
<c:import url="/include.mee?fileName=/admin/include/style" />
<c:import url="/include.mee?fileName=/admin/include/script" />

<script type="text/javascript"
	src="/resources/js/plugIn/canvas/canvasjs.min.js"></script>

</head>

<body>

	<div id="wrapper">


		<!-- S:FILE:header.jsp -->
		<c:import
			url="/include.mee?fileName=/admin/include/header&depth7=menuL2" />
		<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">당첨번호별현황</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="/front/lotto/list.mee">Lotto</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> 당첨번호별현황</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<!--	S:검색조건 -->
				<div class="row">
					<form id="frm" name="frm" method="post">
						<div class="form-group input-group col-lg-12">
							<select id="searchValue" name="searchValue" class="input-sm col-lg-12">
								<option value="">-- 선택 --</option>
								<option value="cntAsc">많은순</option>
								<option value="cntDesc">적은순</option>
							</select>
						</div>
					</form>
				</div>
				<!--	S:검색조건 -->

				<div class="row">
					<div class="col-lg-12">
						<div id="chartContainer" style="height: 1000px; width: 100%;"></div>
					</div>
				</div>
				<!-- /.row -->

				<!--	S:버튼 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel pull-right">
							<button type="button" id="btnList" class="btn btn-primary">
								<i class="fa fa-file "></i>&nbsp;목록
							</button>
						</div>
					</div>
				</div>
				<!--	E:버튼 -->

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

		<!-- S:FILE:footer.jsp -->
		<c:import url="/include.mee?fileName=/admin/include/footer" />
		<!-- E:FILE:footer.jsp -->

	</div>
	<!-- /#wrapper -->


	<script>
		//검색어 입력후 엔터키 이벤트
		$("#searchValue").on("change", function(e) {
			$("#frm").attr("action", "/front/lotto/listDangNum.mee");
			$("#frm").submit();
		});

		$("#searchValue").val("${paramMap.searchValue}");
		
		var dataPointsArray_resultList_chart = new Array();
		<c:forEach items="${resultList}" var="item">
			var dataPointsA = {
				"y" : parseInt("${item.lotto_dang_num_cnt}"),
				"label" : "${item.lotto_dang_num}"
			}
			dataPointsArray_resultList_chart.push(dataPointsA);
		</c:forEach>

		window.onload = function() {
			var chart = new CanvasJS.Chart("chartContainer", {

				title : {
					text : ""
				},
				animationEnabled : true,
				axisX : {
					interval : 1,
					gridThickness : 0,
					labelFontSize : 10,
					labelFontStyle : "normal",
					labelFontWeight : "normal",
					labelFontFamily : "Lucida Sans Unicode"

				},
				axisY2 : {
					interlacedColor : "rgba(1,77,101,.2)",
					gridColor : "rgba(1,77,101,.1)"

				},

				data : [ {
					type : "bar",
					name : "companies",
					axisYType : "secondary",
					color : "#014D65",
					dataPoints : dataPointsArray_resultList_chart
				}

				]
			});

			chart.render();
		}
	</script>

</body>

</html>
