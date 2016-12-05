<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
	$(function(){
		
		//검색어 키업 이벤트발생시 검색버튼 활성화 및 엔터키 이벤트 발생
		$("#searchValue").bind("keyup", function(e){
			//엔터키 클릭시 검색
			if (e.keyCode == 13){
				$("#pageNum").val("1");
				$("#frm").attr("action", "/admin/cd/list.mee");
				$("#frm").submit();
			}
		});
		
		
		//검색어 입력후 엔터키 이벤트
		$("#btnSearch").bind("click", function(e) {
			$("#pageNum").val("1");
			$("#frm").attr("action", "/admin/cd/list.mee");
			$("#frm").submit();
		});		
		
		//상세 이벤트
		$("#dataTable > tbody > tr").bind("click", function() {
			$("#auto_id").val($(this).attr("id"));
			$("form[name=frm]").attr("action", "/admin/cd/detail.mee");
			$("form[name=frm]").submit();
		});
		
		//등록 이벤트
		$("#btnReg").bind("click", function(){
			$("form[name=frm]").attr("action", "/admin/cd/form.mee");
			$("form[name=frm]").submit();
		});
	});
	
	//페이징
	function goPage(pageNo){
		$("#pageNum").val(pageNo);
		$("#searchValue").val('<c:out value="${paramDto.searchValue}" />');
		$("#frm").attr("action", "/admin/cd/list.mee");
		$("#frm").submit();
	};
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
								<i class="fa fa-dashboard"></i>  <a href="/">Dashboard</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 코드관리
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<!--	S:검색조건 -->
				<div class="row">
					<form id="frm" name="frm" method="post">
						<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
						<input type="hidden" name="auto_id" id="auto_id"/>					
					
						<div class="form-group input-group col-lg-12">
							<input type="text" name="searchValue" id="searchValue" class="input-sm col-lg-10" placeholder="검색어 입력" value='<c:out value="${paramDto.searchValue}" />'/>
							<button type="button" id="btnSearch" class="btn btn-sm btn-primary col-lg-2"><i class="fa fa-search"></i>&nbsp;검색</button>
						 </div>								
					</form>	 
				</div>
				<!--	S:검색조건 -->

				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table id="dataTable" class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<th class="col-lg-1">번호</th>
										<th class="col-lg-1">코드</th>
										<th class="col-lg-2">코드명</th>
										<th class="col-lg-1">비고</th>
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
											<tr><td colspan="7">등록된 내용이 없습니다.</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${resultList }" var="result">
												<tr id="${result.auto_id }">
													<td><c:out value="${result.rowNum }" /></td>
													<td><c:out value="${result.cd_id }" /></td>
													<td><c:out value="${result.cd_nm }" /></td>
													<td><c:out value="${result.cd_cmt }" /></td>
													<td><c:out value="${result.cd_type_nm }" /></td>
													<td><c:out value="${result.disp_order }" /></td>
													<td><c:out value="${result.use_yn }" /></td>
													<td><c:out value="${result.up_user }" /></td>
													<td>
														<fmt:parseDate var="up_date" value="${result.up_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
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

				<!--	S:페이징 -->
				<c:import url="/include.mee?fileName=/admin/include/paging"/>
				<!--	E:페이징 -->
				<!-- /.row -->
				
				<!--	S:버튼 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="pull-right">
							<button type="button" id="btnReg" class="btn btn-primary"><i class="fa fa-file "></i>&nbsp;등록</button>
						</div>
					</div>
				</div>
				<!--	E:버튼 -->
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
