<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/cd/list.mee");
		$("form[name=frm]").submit();
	});
	//수정 이벤트
	$("#btnUpd").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/cd/form.mee");
		$("form[name=frm]").submit();
	});
	
	//이전 다음  / 상세 이벤트
// 	$("#dataTable > tbody > tr").bind("click", function() {
// 		$("#cd_id").val($(this).attr("id"));
// 		$("form[name=frm]").attr("action", "/admin/cd/detail.mee");
// 		$("form[name=frm]").submit();
// 	});	
	
	//삭제 이벤트
	$("#btnDel").bind("click", function(){
		var goUrl = "/admin/cd/delete.mee";
		
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
					$("form[name=frm]").attr("action", "/admin/cd/list.mee");
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
		if(!confirm("하위코드까지 모두 삭제됩니다.\n삭제하시겠습니까?")){
			return false;
		}
	};
	
	/*	하위코드	*/
	//등록 이벤트
	$("#btnSubUpd").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/cd/formSub.mee");
		$("form[name=frm]").submit();
	});
	
});
</script>


</head>

<body>

	<div id="wrapper">


<!-- S:FILE:header.jsp -->
<c:import url="/include.mee?fileName=/admin/include/header&depth1=menuL10&depth2=menuL10_1"/>
<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							공통코드
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">Dashboard</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 공통코드
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<form id="frm" name="frm" method="post">
					<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
					<input type="hidden" name="auto_id" id="auto_id" value='<c:out value="${result.auto_id }" />'	/>					
					<input type="hidden" name="cd_id" id="cd_id" value='<c:out value="${result.cd_id }" />'	/>					
					<input type="hidden" name="searchValue" value='<c:out value="${paramDto.searchValue}" />'	/>
				</form>


				<!--	상세내용 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<tbody>
									<tr>
										<th class="col-lg-2">코드</th>
										<td><c:out value="${result.cd_id }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">코드명</th>
										<td><c:out value="${result.cd_nm }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">비고</th>
										<td><c:out value="${result.cd_cmt }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">코드권한</th>
										<td><c:out value="${result.cd_type_nm }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">정렬순서</th>
										<td><c:out value="${result.disp_order }" /></td>
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
				

<!--	하위코드 -->
				<div class="row">
					<div class="col-lg-12">
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">Dashboard</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 공통코드
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 하위코드
							</li>
						</ol>					
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table id="dataTable" class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<th class="col-lg-1">코드</th>
										<th class="col-lg-3">코드명</th>
										<th class="col-lg-3">비고</th>
										<th class="col-lg-1">코드권한</th>
										<th class="col-lg-1">정렬순서</th>
										<th class="col-lg-1">사용여부</th>
										<th class="col-lg-1">최근수정자</th>
										<th class="col-lg-1">최근수정일</th>
									</tr>
								</thead>							
								<tbody>
									<c:choose>
										<c:when test="${empty resultList }">
											<tr><td colspan="8">등록된 내용이 없습니다.</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${resultList }" var="list">
												<tr id="${list.auto_id }">
													<td><c:out value="${list.cd_id }" /></td>
													<td><c:out value="${list.cd_nm }" /></td>
													<td><c:out value="${list.cd_cmt }" /></td>
													<td><c:out value="${list.cd_type_nm }" /></td>
													<td><c:out value="${list.disp_order }" /></td>
													<td><c:out value="${list.use_yn }" /></td>
													<td><c:out value="${list.up_user }" /></td>
													<td>
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
				<!--	S:버튼 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="panel pull-right">
							<button type="button" id="btnSubUpd" class="btn btn-primary"><i class="fa fa-wrench "></i>&nbsp;수정</button>
						</div>
					</div>
				</div>
				<!--	E:버튼 -->				
<!--	하위코드 -->
				

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
