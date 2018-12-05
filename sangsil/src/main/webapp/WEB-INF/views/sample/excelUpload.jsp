<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>
<c:import url="/include.mee?fileName=/sample/include/excel/doctype" />
<c:import url="/include.mee?fileName=/sample/include/excel/style" />
<c:import url="/include.mee?fileName=/sample/include/excel/script" />

</head>

<body>

	<div id="wrapper">


		<!-- S:FILE:header.jsp -->
		<c:import
			url="/include.mee?fileName=/sample/include/excel/header&depth7=menuL2" />
		<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div>

				<!-- Page Heading -->
				<div>
					<div>
						<h1>Tables</h1>
						<ol>
							<li><p>Date: <input type="text" id="datepicker"></p></li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div>
					<div>
						<div>

							<form id="excelUploadForm" name="excelUploadForm"
								enctype="multipart/form-data" method="post"
								action="/sample/excelUploadAjax.mee">
								<div>
									<div>첨부파일은 한개만 등록 가능합니다.</div>

									<dl>
										<dt>첨부 파일</dt>
										<dd>
											<input id="excelFile" type="file" name="excelFile" />
										</dd>
									</dl>
								</div>

								<div>
									<button type="button" id="addExcelImpoartBtn" onclick="check()">
										<span>추가</span>
									</button>
								</div>
							</form>

				<!-- 업로드 내용 -->
				<div class="row text-center pad-top table_size_scroll_01" id="pop_excel_upload_form">

						</div>
					</div>
					
				<!--	S:버튼 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel pull-right">
							<button type="button" id="btnReg" class="btn btn-primary"><i class="fa fa-file"></i>&nbsp;등록</button>
						</div>
					</div>
				</div>
				<!--	E:버튼 -->
					
				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

		<!-- S:FILE:footer.jsp -->
		<c:import url="/include.mee?fileName=/sample/include/excel/footer" />
		<!-- E:FILE:footer.jsp -->

	</div>
	<!-- /#wrapper -->

	<!-- excel 내용확인 -->
	<div role="dialog" aria-hidden="true"></div>

	<script>
	$.datepicker.setDefaults({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		yearSuffix: '년'
	});
	
	$( function() {
		$( "#datepicker" ).datepicker();
	} );


	
	
	function checkFileType(filePath) {
		var fileFormat = filePath.split(".");
		if (fileFormat.indexOf("xlsx") > -1) {
			return true;
		} else {
			return false;
		}
	}

	function check() {
		var file = $("#excelFile").val();
		if (file == "" || file == null) {
			alert("파일을 선택해주세요.");
			return false;
		} else if (!checkFileType(file)) {
			alert("엑셀 파일만 업로드 가능합니다.");
			return false;
		}

		if (confirm("업로드 하시겠습니까?")) {
			var formData = new FormData($("#excelUploadForm")[0]);
			$.ajax({
				type : 'post',
				url : '/sample/excelUploadAjax.mee',
				data : formData,
				processData : false,
				contentType : false,
				success : function(data) {
					$('#pop_excel_upload_form').html(data).modal();
				},
				error : function(error) {
					alert("파일 업로드에 실패하였습니다.");
					console.log(error);
					console.log(error.status);
				}
			});
		}
	}
	
	function closeExcelPop() {
		$('#pop_excel_upload_form').modal('hide');
	}
	
	
	
	$("#btnReg").on("click", function(event) {
		event.preventDefault();
		
		if(confirm("등록 하시겠습니까?")) {
			var data = $("tbody tr").map(function() {
				var obj = {};
				$(this).find('input').each(function() {
					obj[this.name] = $(this).val();
				});
				return obj;
			}).get();
			
			console.log(JSON.stringify(data));
			
			$.ajax({
				type : 'post',
				url : '/sample/uploadSheetInsert.mee',
				data : JSON.stringify(data),
				dataType:"json",
				contentType:"application/json; charset=UTF-8",
				success:function(responseData){
					var data = responseData.resultJson;
					if(data.rCode == "${STATUS_S_CODE}"){
						alert("저장 되었습니다.");
						$('#pop_excel_upload_form').empty();
					}else{
						alert(data.rMsg);
						return;
					}	
				},
				error: function (jqXHR, exception) {
					alert("["+jqXHR.status+"]오류입니다.\n"+exception);
					var msg = '';
					if (jqXHR.status === 0) {
						msg = 'Not connect.\n Verify Network.';
					} else if (jqXHR.status == 404) {
						msg = 'Requested page not found. [404]';
					} else if (jqXHR.status == 500) {
						msg = 'Internal Server Error [500].';
					} else if (exception === 'parsererror') {
						msg = 'Requested JSON parse failed.';
					} else if (exception === 'timeout') {
						msg = 'Time out error.';
					} else if (exception === 'abort') {
						msg = 'Ajax request aborted.';
					} else {
						msg = 'Uncaught Error.\n' + jqXHR.responseText;
					}
					console.log(msg)
					return;
				}
			});
		}
	});
	
	
</script>

</body>

</html>
