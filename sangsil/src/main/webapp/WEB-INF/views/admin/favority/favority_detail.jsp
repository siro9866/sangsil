<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/favority/list.mee");
		$("form[name=frm]").submit();
	});
	//수정 이벤트
	$("#btnUpd").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/favority/form.mee");
		$("form[name=frm]").submit();
	});
	
	//이전 다음  / 상세 이벤트
	$("#dataTable > tbody > tr").bind("click", function() {
		$("#favority_id").val($(this).attr("id"));
		$("form[name=frm]").attr("action", "/admin/favority/detail.mee");
		$("form[name=frm]").submit();
	});	
	
	//삭제 이벤트
	$("#btnDel").bind("click", function(){
		var goUrl = "/admin/favority/delete.mee";
		
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
					$("form[name=frm]").attr("action", "/admin/favority/list.mee");
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
	
	$("#tdUrl").on("click", function(){
		window.open($(this).html());
	});
	
});
</script>





</head>

<body>

	<div id="wrapper">


<!-- S:FILE:header.jsp -->
<c:import url="/include.mee?fileName=/admin/include/header&depth1=menuL2"/>
<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Tables
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">Dashboard</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> Tables
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<form id="frm" name="frm" method="post">
					<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
					<input type="hidden" name="favority_id" id="favority_id" value='<c:out value="${result.favority_id }" />'	/>					
					<input type="hidden" name="searchValue" value='<c:out value="${paramDto.searchValue}" />'	/>
				</form>


				<!--	상세내용 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<tbody>
									<tr>
										<th class="col-lg-2">순번</th>
										<td><c:out value="${result.favority_id }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">카테고리</th>
										<td><c:out value="${result.cd_nm }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">사이트이미지</th>
										<td><c:out value="${result.favority_img }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">사이트명</th>
										<td><c:out value="${result.favority_nm }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">사이트URL</th>
										<td id="tdUrl"><c:out value="${result.favority_url }" /></td>
									</tr>
									<tr>
										<th class="col-lg-2">비고</th>
										<td><c:out value="${result.favority_cmt }" /></td>
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
										<th class="col-lg-2">사용유무</th>
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
												<tr id="${list.favority_id }" <c:if test="${result.favority_id eq list.favority_id}"> class="warning"</c:if>	>
<%-- 													<td class="col-lg-2"><c:out value="${list.favority_id }" /></td> --%>
													<td class="col-lg-10"><c:out value="${list.favority_nm }" /></td>
													<td class="col-lg-2">
														<fmt:parseDate var="in_date" value="${list.in_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
														<fmt:formatDate value="${in_date }" pattern="yyyy.MM.dd"/>															
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
