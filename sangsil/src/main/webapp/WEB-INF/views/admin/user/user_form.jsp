<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
	
	//초기값
	if("${result.user_id}" == ""){
		$("select option:eq(0)").attr("selected", "selected");
		$("input:radio[name=iuse_yn]:input[value=Y]").attr("checked", true);
		$("#disp_order").val(1);
	}else{
		$("#iuser_auth").val("${result.user_auth}");
		$("input:radio[name=iuse_yn]:input[value=${result.use_yn}]").attr("checked", true);
	}
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/user/list.mee");
		$("form[name=frm]").submit();
	});
	
	//등록 이벤트
	$("#btnSave").bind("click", function(){
		var goUrl = "";
		if($("#user_id").val().length >0){
			goUrl = "/admin/user/update.mee";
		}else{
			goUrl = "/admin/user/insert.mee";
		}
		
		$("#user_auth").val($("#iuser_auth option:selected").val());
		$("#use_yn").val($("input:radio[name=iuse_yn]:checked").val());
		
 		$.ajax({
			type:"post",
			async:true,
			url:goUrl,
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			beforeSend:beforSubmit,
			success:function(data){
				if(data.rCode == "0000"){
					alert("저장 되었습니다.");
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
		if(!confirm("저장 하시겠습니까?")){
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
								<i class="fa fa-dashboard"></i>  <a href="/">대시보드</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 회원관리
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<div class="row">
					<div class="col-lg-12">
						<form name="frm" id="frm">
							<input type="hidden" name="user_auth" id="user_auth"/>
							<input type="hidden" name="use_yn" id="use_yn"/>
							<input type="hidden" name="pageNum" id="pageNum" value="${paramDto.pageNum}" />
							<input type="hidden" name="searchValue" id="searchValue" value="${paramDto.searchValue}"/>
							<input type="hidden" name="user_id" id="user_id" value="${result.user_id}"/>
							
							<div class="form-group">
								<label for="user_nm">아이디</label>
								<input type="text" name="user_id" id="user_id" class="form-control" placeholder="아이디" value="${result.user_id }"/>
							</div>
							<div class="form-group">
								<label for="user_nm">이름</label>
								<input type="text" name="user_nm" id="user_nm" class="form-control" placeholder="이름" value="${result.user_nm }"/>
							</div>
							<div class="form-group">
								<label for="user_nm">프로필사진</label>
								<input type="text" name="user_img" id="user_img" class="form-control" placeholder="프로필사진" value="${result.user_img }"/>
							</div>
							<div class="form-group">
								<label for="user_nm">전화번호</label>
								<input type="text" name="tel1" id="tel1" class="form-control" placeholder="02" value="${result.tel1 }" />
								<input type="text" name="tel2" id="tel2" class="form-control" placeholder="1234" value="${result.tel2 }" />
								<input type="text" name="tel3" id="tel3" class="form-control" placeholder="5678" value="${result.tel3 }" />
							</div>
							<div class="form-group">
								<label for="user_nm">휴대폰번호</label>
								<input type="text" name="phone1" id="phone1" class="form-control" placeholder="010" value="${result.phone1 }" />
								<input type="text" name="phone2" id="phone2" class="form-control" placeholder="1234" value="${result.phone2 }" />
								<input type="text" name="phone3" id="phone3" class="form-control" placeholder="5678" value="${result.phone3 }" />
							</div>
							<div class="form-group">
								<label for="iuser_auth">권한</label>
								<select name="iuser_auth" id="iuser_auth" class="form-control">
									<c:forEach items="${cdList }" var="list">
										<option value="${list.cd_id }">${list.cd_nm }</option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group">
								<label for="iuse_yn">사용여부</label>
								<label class="radio-inline">
									<input type="radio" name="iuse_yn" id="iuse_yn1" value="Y">사용
								</label>
								<label class="radio-inline">
									<input type="radio" name="iuse_yn" id="iuse_yn2" value="N">미사용
								</label>
							</div>

						</form>
					</div>
				</div>
				<!-- /.row -->

				<!--	S:버튼 -->
				<div class="row">
					<div class="col-lg-12">
						<div class="pull-right">							
							<button type="button" id="btnList" class="btn btn-primary"><i class="fa fa-list "></i>&nbsp;목록</button> 
							<button type="button" id="btnSave" class="btn btn-primary"><i class="fa fa-save "></i>&nbsp;저장</button>
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
