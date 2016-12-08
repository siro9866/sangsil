<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
var listUrl = "/admin/board/list.mee";
var detailUrl = "/admin/board/detail.mee";
var formlUrl = "/admin/board/form.mee";
var deletelUrl = "/admin/board/delete.mee";

$(function(){
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", listUrl);
		$("form[name=frm]").submit();
	});
	//수정 이벤트
	$("#btnUpd").bind("click", function(){
		$("form[name=frm]").attr("action", formlUrl);
		$("form[name=frm]").submit();
	});
	
	//이전 다음  / 상세 이벤트
	$("#dataTable > tbody > tr").bind("click", function() {
		$("#board_id").val($(this).attr("id"));
		$("form[name=frm]").attr("action", detailUrl);
		$("form[name=frm]").submit();
	});	
	
	//삭제 이벤트
	$("#btnDel").bind("click", function(){
		var goUrl = deletelUrl;
		
 		$.ajax({
			type:"post",
			async:true,
			url:goUrl,
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			beforeSend:beforSubmit,
			success:function(data){
				if(data.rCode == "0000"){
					alert("삭제 되었습니다.");
					$("form[name=frm]").attr("action", listUrl);
					$("form[name=frm]").submit();
				}else{
					alert(data.rMsg);
					return;
				}	
			},
			error:function(){
				alert("오류입니다.");
				return;						
			}
		});
	});	
	
	// 폼 데이터 점검
	beforSubmit = function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	};		

});
</script>





</head>

<body>

	<div id="wrapper">


<!-- S:FILE:header.jsp -->
<c:import url="/include.mee?fileName=/admin/include/header&depth1=menuL3&depth2=menuL1"/>
<!-- E:FILE:header.jsp -->

<!-- 게시판구분 -->
<spring:eval expression="@config['CD_ID_BAA02']" var="prop_board_gbn" />

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							일반게시판
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">게시판관리</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 상세
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<form id="frm" name="frm" method="post">
					<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
					<input type="hidden" name="board_gbn" value="${prop_board_gbn }" />
					<input type="hidden" name="board_id" id="board_id" value='<c:out value="${result.board_id }" />'	/>					
					<input type="hidden" name="searchValue" value='<c:out value="${paramDto.searchValue}" />'	/>
					<input type="hidden" name="file_id" id="file_id" value='<c:out value="${result.file_id}" />'/>
					<input type="hidden" name="file_name" id="file_name" value='<c:out value="${result.file_name}" />'/>
					<input type="hidden" name="path_name" id="path_name" value='<c:out value="${result.path_name}" />'/>
				</form>


				<!--	상세내용 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<tbody>
									<tr>
										<th class="col-lg-2">순번</th>
										<td><c:out value="${result.board_id }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">게시판</th>
										<td><c:out value="${result.board_gbnNM }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">카테고리</th>
										<td><c:out value="${result.board_catNM }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">태그</th>
										<td><c:out value="${result.board_tagNM }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">제목</th>
										<td><c:out value="${result.board_title }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">내용</th>
										<td>${result.board_txt }</td>
									</tr>
									<tr>
										<th class="col-lg-2">첨부파일</th>
										<td>
											<c:choose>
												<c:when test="${empty fileList }">
													첨부파일이 없습니다
												</c:when>
												<c:otherwise>
												
													<c:forEach items="${fileList }" var="result">
														<a href="/fileDownload.mee?path=${result.path_name}&fileName=${result.file_name}&originalFileName=${result.originalFileName}"><c:out value="${result.originalFileName }" />(<c:out value="${result.file_size }" /> byte)</a> <br>
													</c:forEach>
													
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									<tr>
										<th class="col-lg-2">정렬순서</th>
										<td><c:out value="${result.disp_order }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">방문횟수</th>
										<td><c:out value="${result.hit_cnt }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">사용여부</th>
										<td><c:out value="${result.use_yn }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">등록일시</th>
										<td><c:out value="${result.in_date }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">등록자</th>
										<td><c:out value="${result.in_user }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">등록자 IP</th>
										<td><c:out value="${result.in_ip }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">수정일시</th>
										<td><c:out value="${result.up_date }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">수정자</th>
										<td><c:out value="${result.up_user }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">수정자 IP</th>
										<td><c:out value="${result.up_ip }" /></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.row -->

				<!--	S:버튼 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel pull-right">
							<button type="button" id="btnList" class="btn btn-primary"><i class="fa fa-file "></i>&nbsp;목록</button>
							<button type="button" id="btnUpd" class="btn btn-primary"><i class="fa fa-wrench "></i>&nbsp;수정</button>
							<button type="button" id="btnDel" class="btn btn-primary"><i class="fa fa-trash "></i>&nbsp;삭제</button>
						</div>
					</div>
				</div>
				<!--	E:버튼 -->
				

				<!--	이전다음글 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table id="dataTable" class="table table-bordered table-hover table-striped">
								<tbody>
									<c:choose>
										<c:when test="${empty resultList }">
											<tr><td colspan="3">등록된 내용이 없습니다.</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${resultList }" var="list">
												<tr id="${list.board_id }" <c:if test="${result.board_id eq list.board_id}"> class="warning"</c:if>	>
													<td class="col-lg-10"><c:out value="${list.board_title }" /></td>
													<td class="col-lg-2">
														<fmt:parseDate var="up_date" value="${list.up_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
														<fmt:formatDate value="${up_date }" pattern="yyyy.MM.dd"/>															
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>								
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.row -->
				

			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

<!-- S:FILE:footer.jsp -->
		<c:import url="/include.mee?fileName=/admin/include/footer"/>
<!-- E:FILE:footer.jsp -->

	</div>
	<!-- /#wrapper -->

</body>

</html>
