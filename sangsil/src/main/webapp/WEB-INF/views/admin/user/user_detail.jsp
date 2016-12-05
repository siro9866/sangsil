<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/user/list.mee");
		$("form[name=frm]").submit();
	});
	//수정 이벤트
	$("#btnUpd").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/user/form.mee");
		$("form[name=frm]").submit();
	});
	
	//이전 다음  / 상세 이벤트
	$("#dataTable > tbody > tr").bind("click", function() {
		$("#user_id").val($(this).attr("id"));
		$("form[name=frm]").attr("action", "/admin/user/detail.mee");
		$("form[name=frm]").submit();
	});	
	
	//삭제 이벤트
	$("#btnDel").bind("click", function(){
		var goUrl = "/admin/user/delete.mee";
		
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
					$("form[name=frm]").attr("action", "/admin/user/list.mee");
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
<c:import url="/include.mee?fileName=/admin/include/header&depth1=menuL10&depth2=menuL10_2"/>
<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							회원관리
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashuser"></i>  <a href="/">대시보드</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 회원관리
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<form id="frm" name="frm" method="post">
					<input type="hidden" name="pageNum" id="pageNum" value='${paramDto.pageNum}' />
					<input type="hidden" name="user_id" id="user_id" value='${result.user_id }'	/>					
					<input type="hidden" name="searchValue" value='${paramDto.searchValue}'	/>
				</form>


				<!--	상세내용 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<tbody>
									<tr>
										<th class="col-lg-2">아이디</th>
										<td>${result.user_id }</td>
									</tr>
									<tr>
										<th class="col-lg-2">이름</th>
										<td>${result.user_nm }</td>
									</tr>
									<tr>
										<th class="col-lg-2">프로필사진</th>
										<td>${result.user_img }</td>
									</tr>
									<tr>
										<th class="col-lg-2">전화번호</th>
										<td>${result.tel1 } - ${result.tel2 } - ${result.tel3 }</td>
									</tr>
									<tr>
										<th class="col-lg-2">휴대폰번호</th>
										<td>${result.phone1 } - ${result.phone2 } - ${result.phone3 }</td>
									</tr>
									<tr>
										<th class="col-lg-2">로그인횟수</th>
										<td>${result.login_cnt }</td>
									</tr>
									<tr>
										<th class="col-lg-2">최근로그인</th>
										<td>
											<fmt:parseDate var="login_date" value="${result.login_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
											<fmt:formatDate value="${login_date }" pattern="yyyy.MM.dd"/>										
										</td>
									</tr>
									<tr>
										<th class="col-lg-2">권한</th>
										<td>${result.user_authNm }</td>
									</tr>
									<tr>
										<th class="col-lg-2">사용여부</th>
										<td>${result.use_yn }</td>
									</tr>
									<tr>
										<th class="col-lg-2">등록일시</th>
										<td>
											<fmt:parseDate var="in_date" value="${result.in_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
											<fmt:formatDate value="${in_date }" pattern="yyyy.MM.dd"/>	
										</td>
									</tr>
									<tr>
										<th class="col-lg-2">등록자</th>
										<td>${result.in_user }</td>
									</tr>
									<tr>
										<th class="col-lg-2">등록자 IP</th>
										<td>${result.in_ip }</td>
									</tr>
									<tr>
										<th class="col-lg-2">수정일시</th>
										<td>
											<fmt:parseDate var="up_date" value="${result.up_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
											<fmt:formatDate value="${up_date }" pattern="yyyy.MM.dd"/>	
										</td>
									</tr>
									<tr>
										<th class="col-lg-2">수정자</th>
										<td>${result.up_user }</td>
									</tr>
									<tr>
										<th class="col-lg-2">수정자 IP</th>
										<td>${result.up_ip }</td>
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
												<tr id="${list.user_id }" <c:if test="${result.user_id eq list.user_id}"> class="warning"</c:if>	>
													<td class="col-lg-2">${list.user_id }</td>
													<td class="col-lg-2">${list.user_nm }</td>
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
