<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script type="text/javascript" src="/resources/plugIn/SmartEditor/SE2.3.10.O11329/js/HuskyEZCreator.js" charset="utf-8"></script>

<script>
var listUrl = "/admin/board/listDev.mee";
var updateUrl = "/admin/board/updateDev.mee";
var insertUrl = "/admin/board/insertDev.mee";

$(function(){
	
	//SmartEditor
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "board_txt",
		sSkinURI: "/resources/plugIn/SmartEditor/SE2.3.10.O11329/SmartEditor2Skin.html",	
		fCreator: "createSEditor2"
	});	
	
	
	//초기값
	if("${result.board_id}" == ""){
		$("select option:eq(0)").attr("selected", "selected");
		$("#disp_order").val(1);
	}else{
		$("#iboard_cat").val("${result.board_cat}");
		$("#iboard_tag").val("${result.board_tag}");
	}
	
	
	//목록 이벤트
	$("#btnList").bind("click", function(){
		$("form[name=frm]").attr("action", listUrl);
		$("form[name=frm]").submit();
	});
	
	//등록 이벤트
	$("#btnSave").bind("click", function(){
		
		if(!confirm("등록하시겠습니까?")){
			return false;
		}
		
		var goUrl = "";
		if($("#board_id").val().length > 0){
			goUrl = updateUrl;
		}else{
			goUrl = insertUrl;
		}

		$("#use_yn").val($("input:radio[name=iuse_yn]:checked").val());
		
		//SmartEditor 에디터의 내용이 textarea에 적용된다.
		oEditors.getById["board_txt"].exec("UPDATE_CONTENTS_FIELD", []);
		//SmartEditor 에디터의 내용에 대한 값 검증은 이곳에서
		if ($("#board_txt").val() == "<p>&nbsp;</p>") {alert("내용을 입력하세요");	return false;}		
		
		$("#board_cat").val($("#iboard_cat option:selected").val());
		$("#board_tag").val($("#iboard_tag option:selected").val());
		$("#frm").attr("action", goUrl);
		$("#frm").submit();
		
		
//  		$.ajax({
// 			type:"post",
// 			async:true,
// 			url:goUrl,
// 			data:$("form[name=frm]").serialize(),
// 			dataType:"json",
// 			beforeSend:beforSubmit,
// 			success:function(data){
// 				if(data.rCode == "0000"){
// 					alert("저장 되었습니다.");
// 					$("form[name=frm]").attr("action", listUrl);
// 					$("form[name=frm]").submit();
// 				}else{
// 					alert(data.rMsg);
// 					return;
// 				}	
// 			},
// 			error:function(){
// 				alert("오류입니다.");
// 				return;						
// 			}
// 		});

		
		
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
<c:import url="/include.mee?fileName=/admin/include/header&depth1=menuL3&depth2=menuL1"/>
<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							개발게시판
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">게시판관리</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 등록
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->


				<div class="row">
					<div class="col-lg-12">
						<form name="frm" id="frm" method="post" enctype="multipart/form-data">
							<input type="hidden" name="board_cat" id="board_cat"/>
							<input type="hidden" name="board_tag" id="board_tag"/>
							<input type="hidden" name="use_yn" id="use_yn"/>
							<input type="hidden" name="pageNum" id="pageNum" value='<c:out value="${paramDto.pageNum}" />' />
							<input type="hidden" name="searchValue" id="searchValue" value='<c:out value="${paramDto.searchValue}" />'/>
							<input type="hidden" name="board_id" id="board_id" value='<c:out value="${result.board_id}" />'/>
							<input type="hidden" name="file_id" id="file_id" value='<c:out value="${result.file_id}" />'/>
							<input type="hidden" name="file_name" id="file_name" value='<c:out value="${result.file_name}" />'/>
							<input type="hidden" name="path_name" id="path_name" value='<c:out value="${result.path_name}" />'/>
							
							<div class="form-group">
								<label for="board_cat">카테고리</label>
								<select name="iboard_cat" id="iboard_cat" class="form-control">
									<c:forEach items="${catList }" var="list">
										<option value="${list.cd_id }"><c:out value="${list.cd_nm }" /></option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group">
								<label for="board_tag">태그</label>
								<select name="iboard_tag" id="iboard_tag" class="form-control">
									<c:forEach items="${tagList }" var="list">
										<option value="${list.cd_id }"><c:out value="${list.cd_nm }" /></option>
									</c:forEach>
								</select>
							</div>
							
							<div class="form-group">
								<label for="board_nm">제목</label>
								<input type="text" name="board_title" id="board_title" class="form-control" placeholder="제목" value='<c:out value="${result.board_title }" />'/>
							</div>
							<div class="form-group">
								<label for="board_nm">내용</label>
<%-- 								<input type="text" name="board_txt" id="board_txt" class="form-control" placeholder="내용" value='<c:out value="${result.board_txt }" />' /> --%>
									<textarea name="board_txt" id="board_txt" rows="8" cols="70" style="width:100%;">${result.board_txt }</textarea>
							</div>
							<div class="form-group">
								<label>첨부파일<br><c:out value="${result.originalFileName}" /></label>
								<input type="file" name="file" id="file" class="form-control" />
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
