<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
	
	//초기값
	if("${result.favority_id}" == ""){
		$("select option:eq(0)").attr("selected", "selected");
		$("input:radio[name=iuse_yn]:input[value=Y]").attr("checked", true);
		$("#disp_order").val(1);
	}else{
		$("#icd_id").val("${result.cd_id}");
		$("input:radio[name=iuse_yn]:input[value=${result.use_yn}]").attr("checked", true);
	}
	
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", "/admin/favority/list.mee");
		$("form[name=frm]").submit();
	});
	
	//등록 이벤트
	$("#btnSave").bind("click", function(){
		var goUrl = "";
		if($("#favority_id").val().length >0){
			goUrl = "/admin/favority/update.mee";
		}else{
			goUrl = "/admin/favority/insert.mee";
		}
		
		$("#cd_id").val($("#icd_id option:selected").val());
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
<c:import url="/include.mee?fileName=/admin/include/header&depth1=menuL2"/>
<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							즐겨찾기
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">Dashboard</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 즐겨찾기
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<div class="row">
					<div class="col-lg-12">
						<form name="frm" id="frm">
							<input type="hidden" name="cd_id" id="cd_id"/>
							<input type="hidden" name="use_yn" id="use_yn"/>
							<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
							<input type="hidden" name="searchValue" id="searchValue" value='<c:out value="${paramDto.searchValue}" />'/>
							<input type="hidden" name="favority_id" id="favority_id" value='<c:out value="${result.favority_id}" />'/>
							
							<div class="form-group">
								<label for="category">카테고리</label>
								<select name="icategory" id="icd_id" class="form-control">
									<c:forEach items="${cdList }" var="list">
										<option value="${list.cd_id }"><c:out value="${list.cd_nm }" /></option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group">
								<label for="favority_nm">즐겨찾기 명</label>
								<input type="text" name="favority_nm" id="favority_nm" class="form-control" placeholder="즐겨찾기 명" value='<c:out value="${result.favority_nm }" />'/>
							</div>
							<div class="form-group">
								<label for="favority_nm">즐겨찾기 URL</label>
								<input type="text" name="favority_url" id="favority_url" class="form-control" placeholder="즐겨찾기 URL" value='<c:out value="${result.favority_url }" />' />
							</div>
							<div class="form-group">
								<label for="favority_img">로고 URL</label>
								<input type="text" name="favority_img" id="favority_img" class="form-control" placeholder="로고 URL" value='<c:out value="${result.favority_img }" />' />
							</div>							
							<div class="form-group">
								<label for="favority_cmt">비고</label>
								<textarea name="favority_cmt" id="favority_cmt" class="form-control" rows="3"><c:out value="${result.favority_cmt }" /></textarea>
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
