<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
	
	//코드 중복확인 여부
	var cdDupleChk = false;
	
	//초기값
	if("${result.cd_id}" == ""){
		$("select option:eq(0)").attr("selected", "selected");
		$("input:radio[name=iuse_yn]:input[value=Y]").attr("checked", true);
		$("#disp_order").val(1);
		$("#cdDupleChk").removeClass("btn-success");
		$("#cdDupleChk").addClass("btn-danger");
		$("#cdDupleChk").removeAttr("disabled");		
	}else{
		$("#icd_type").val("${result.cd_type}");
		$("input:radio[name=iuse_yn]:input[value=${result.use_yn}]").attr("checked", true);
		$("#cdDupleChk").removeClass("btn-danger");
		$("#cdDupleChk").addClass("btn-success");
		$("#cdDupleChk").attr("disabled", true);
	}
	
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/cd/list.mee");
		$("form[name=frm]").submit();
	});
	
	//등록 이벤트
	$("#btnSave").bind("click", function(){
		var goUrl = "";
		if($("#cd_id").val().length >0){
			goUrl = "/admin/cd/update.mee";
		}else{
			//등록시 코드 중복확인 체크
			if(!cdDupleChk){
				alert("코드 중복 확인을 해주십시오!");
				return false;
			}else{
				//실제 저장될 폼에 세팅
				$("#cd_id").val($("#icd_id").val());
			}
			goUrl = "/admin/cd/insert.mee";
		}
		
		$("#cd_type").val($("#icd_type option:selected").val());
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
		if(!confirm("저장 하시겠습니까?")){
			return false;
		}
	};
	
	
	//코드 아이디 변경되면 중복체크 false
	$("#icd_id").bind("change", function(){
		cdDupleChk = false;
		$("#cdDupleChk").removeClass("btn-success");
		$("#cdDupleChk").addClass("btn-danger");
		$("#cdDupleChk").removeAttr("disabled");		
	});
	//중복체크
	$("#cdDupleChk").bind("click", function(){
		if($("#icd_id").val() == ""){
			alert("코드를 입력해주세요.");
			return false;
		}
 		$.ajax({
			type:"get",
			async:true,
			url:"/admin/cd/cdDupleChk.mee?cd_id="+$("#icd_id").val(),
			dataType:"json",
			success:function(data){
				if(data.rCode == "0000"){
					if(data.result){
						alert("중복된 값이 있습니다.");
					}else{
						alert("사용가능합니다.");
						cdDupleChk = true;
						$("#cdDupleChk").removeClass("btn-danger");
						$("#cdDupleChk").addClass("btn-success");
						$("#cdDupleChk").attr("disabled", true);
					}
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


				<div class="row">
					<div class="col-lg-12">
						<form name="frm" id="frm">
							<input type="hidden" name="use_yn" id="use_yn"/>
							<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
							<input type="hidden" name="searchValue" id="searchValue" value='<c:out value="${paramDto.searchValue}" />'/>
							<input type="hidden" name="cd_id" id="cd_id" value='<c:out value="${result.cd_id}" />'/>
							<input type="hidden" name="cd_type" id="cd_type" />
							
							<div class="form-group">
								<label for="cd_id">코드</label>
									<c:choose>
										<c:when test="${empty result.cd_id }">
											<div class="input-group">
												<input type="text" name="icd_id" id="icd_id" class="form-control" placeholder="코드" />
												<span class="input-group-btn"><button class="btn" type="button" id="cdDupleChk"><i class="fa fa-search"></i>&nbsp;중복확인</button></span>
											</div>
										</c:when>
										<c:otherwise>
											<input type="text" class="form-control" disabled value='<c:out value="${result.cd_id }" />'/>
										</c:otherwise>
									</c:choose>
							</div>
							
							<div class="form-group">
								<label for="cd_nm">코드명</label>
								<input type="text" name="cd_nm" id="cd_nm" class="form-control" placeholder="코드명" value='<c:out value="${result.cd_nm }" />'/>
							</div>
							<div class="form-group">
								<label for="cd_cmt">비고</label>
								<textarea name="cd_cmt" id="cd_cmt" class="form-control" rows="3"><c:out value="${result.cd_cmt }" /></textarea>
							</div>
							<div class="form-group">
								<label for="cd_cmt">코드권한</label>
								<select name="icd_type" id="icd_type" class="form-control">
									<c:forEach items="${resultListAuth }" var="list">
										<option value="${list.cd_id }"><c:out value="${list.cd_nm }" /></option>
									</c:forEach>
								</select>								
							</div>
							<div class="form-group">
								<label for="disp_order">정렬순서</label>
								<input type="number" name="disp_order" id="disp_order" class="form-control" placeholder="정렬순서"  value='<c:out value="${result.disp_order }" />'/>
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
