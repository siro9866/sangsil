<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){
		
	//초기값
	if("${not empty resultList}"){
		//코드권한 값세팅
		$("input:hidden[name='cd_type[]']").each(function(i){
			$("select[name='icd_type[]']").eq(i).val($("input:hidden[name='cd_type[]']").eq(i).val());
		});
		//사용여부 값세팅
		$("input:hidden[name='use_yn[]']").each(function(i){
			if($("input:hidden[name='use_yn[]']").eq(i).val() == "Y"){
				$("input[name='iuse_yn[]']").eq(i).attr("checked", true);
			}
		});
		
		//중복체크 값 설정
		//코드 중복확인 여부
		$("input:hidden[name='check_cdDup[]']").each(function(i){
			//버튼 색깔 변경 및 활성화
			$("input:hidden[name='check_cdDup[]']").eq(i).val("TRUE");
			//중복확인 버튼
			$("button[name='btn_check_cdDup[]']").eq(i).removeClass("btn-danger");
			$("button[name='btn_check_cdDup[]']").eq(i).addClass("btn-success");
			$("button[name='btn_check_cdDup[]']").eq(i).attr("disabled", true);
			
		});
		//저장버튼 비활성화
		$("#btnSave").attr("disabled", true);
	}	
	
	//내용수정시 이벤트
	$(document).on("change", "input[name='cd_id[]'], input[name='cd_nm[]'], select[name='icd_type[]'], input[name='cd_cmt[]'], input[name='disp_order[]'], input[name='iuse_yn[]']", function(){
		//수정이 발생한 라벨 표시
		if($(this).closest("tr").find("td:first-child input[name='save_state[]']").val() == "I"){
			//신규 추가된 ROW 내용 수정이벤트
			$(this).closest("tr").find("td:first-child span").text("등 록");
		}else{
			//저장된 ROW 수정이벤트
			$(this).closest("tr").find("td:first-child span").text("수 정");
			//업데이트 상태로 변경
			$(this).closest("tr").find("td:first-child input[name='save_state[]']").val("U");
		}
		$("#btnSave").removeAttr("disabled");
	});
	
	//코드 아이디 변경 확인
	$(document).on("change", "input[name='cd_id[]']", function(){
		//중복체크 false
		var f_selectedIndex = $("input[name='cd_id[]']").index(this);
		//버튼 색깔 변경 및 활성화
		$("input:hidden[name='check_cdDup[]']").eq(f_selectedIndex).val("FALSE");
		$("button[name='btn_check_cdDup[]']").eq(f_selectedIndex).removeClass("btn-success");
		$("button[name='btn_check_cdDup[]']").eq(f_selectedIndex).addClass("btn-danger");
		$("button[name='btn_check_cdDup[]']").eq(f_selectedIndex).removeAttr("disabled");
	});	
	
	//사용우무 변경 이벤트
	$(document).on("change", "input[name='iuse_yn[]']", function(){
		var f_selectedIndex = $("input[name='iuse_yn[]']").index(this);
		if($(this).is(":checked")){
			$("input:hidden[name='use_yn[]']").eq(f_selectedIndex).val("Y");
		}else{
			$("input:hidden[name='use_yn[]']").eq(f_selectedIndex).val("N");
		}
	});	
	
	//중복체크
	$(document).on("click", "button[name='btn_check_cdDup[]']", function(){
		var f_selectedIndex = $("button[name='btn_check_cdDup[]']").index(this);
		var f_cdId = $.trim($("input[name='cd_id[]']").eq(f_selectedIndex).val());
		if(f_cdId == ""){
			alert("코드를 입력해주세요.");
			$("input[name='cd_id[]']").eq(f_selectedIndex).focus();
			return false;
		}
 		$.ajax({
			type:"get",
			async:true,
			url:"/admin/cd/cdDupleChk.mee?cd_id="+f_cdId,
			dataType:"json",
			success:function(data){
				if(data.rCode == "0000"){
					if(data.result){
						alert("중복된 값이 있습니다.");
					}else{
						alert("사용가능합니다.");
						//버튼 색깔 변경 및 활성화
						$("input:hidden[name='check_cdDup[]']").eq(f_selectedIndex).val("TRUE");
						$("button[name='btn_check_cdDup[]']").eq(f_selectedIndex).removeClass("btn-danger");
						$("button[name='btn_check_cdDup[]']").eq(f_selectedIndex).addClass("btn-success");
						$("button[name='btn_check_cdDup[]']").eq(f_selectedIndex).attr("disabled", true);
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
	
	
	//목록 이벤트
	$(document).on("click", "#btnList", function(){
		$("form[name=frm]").attr("action", "/admin/cd/detail.mee");
		$("form[name=frm]").submit();
	});
	
	//등록 이벤트
	$(document).on("click", "#btnSave", function(){
		var saveStat = true;
		var goUrl = "/admin/cd/saveSub.mee";
		
		var f_check_cdDup = $("input:hidden[name='check_cdDup[]']");
		f_check_cdDup.each(function(i){
			if(f_check_cdDup.eq(i).val() == "FALSE"){
				alert("코드 중복확인을 해주세요.");
				$("input[name='cd_id[]']").eq(i).focus();
				saveStat = false;
				return false;
			}
		});	
		
		
		if(saveStat){
			
			//실제 전송할 데이터 생성
			var saveState = $("input:hidden[name='save_state[]']");
			var autoId = $("input:hidden[name='auto_id[]']");
			var cdId = $("input:text[name='cd_id[]']");
			var cdNm = $("input:text[name='cd_nm[]']");
			var cdCmt = $("input:text[name='cd_cmt[]']");
			var cdType = $("select[name='icd_type[]']");
			var dispOrder = $("input[name='disp_order[]']");
			var useYn = $("input:hidden[name='use_yn[]']");
			
			var frm_save_state = "";
			var frm_auto_id = "";
			var frm_cd_id = "";
			var frm_cd_nm = "";
			var frm_cd_cmt = "";
			var frm_cd_type = "";
			var frm_disp_order = "";
			var frm_use_yn = "";

			//폴데이터 생성
			cdId.each(function(i){
				//수정된 ROW 만 전송
				if(saveState.eq(i).val() == "N") return true;
				frm_save_state += saveState.eq(i).val();
				frm_auto_id += autoId.eq(i).val();
				frm_cd_id += $.trim(cdId.eq(i).val());
				frm_cd_nm += $.trim(cdNm.eq(i).val());
				frm_cd_cmt += $.trim(cdCmt.eq(i).val());
				frm_cd_type += cdType.eq(i).val();
				frm_disp_order += dispOrder.eq(i).val();
				frm_use_yn += useYn.eq(i).val();
				if(i < cdId.length - 1){
					frm_save_state += ",";
					frm_auto_id += ",";
					frm_cd_id += ",";
					frm_cd_nm += ",";
					frm_cd_cmt += ",";
					frm_cd_type += ",";
					frm_disp_order += ",";
					frm_use_yn += ",";
				}
			});
			
			$("#frm_save_state").val(frm_save_state);
			$("#frm_auto_id").val(frm_auto_id);
			$("#frm_cd_id").val(frm_cd_id);
			$("#frm_cd_nm").val(frm_cd_nm);
			$("#frm_cd_cmt").val(frm_cd_cmt);
			$("#frm_cd_type").val(frm_cd_type);
			$("#frm_disp_order").val(frm_disp_order);
			$("#frm_use_yn").val(frm_use_yn);			
			
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
						$("form[name=frm]").attr("action", "/admin/cd/detail.mee");
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
		}
	});	
	
	// 폼 데이터 점검
	beforSubmit = function(){
		
		if(!confirm("저장 하시겠습니까?")){
			return false;
		}
	};
	
	
	
	
	//추가버튼 이벤트
	$(document).on("click", "#addCode", function(){
		
		var oneRow = "";
		
		oneRow +='	<tr>		';
		oneRow +='		<td>		';
		oneRow +='			<span class="label label-warning"></span>		';
		oneRow +='			<button type="button" class="btn btn-default logic_minusBtn"><i class="fa fa-minus "></i>&nbsp;삭제</button>		';	
		oneRow +='			<input type="hidden" name="save_state[]" value="I"/>		';
		oneRow +='		</td>		';
		oneRow +='		<td>		';
		oneRow +='			<div class="input-group">		';
		oneRow +='				<input type="text" name="cd_id[]" class="form-control" placeholder="코드" value="${result.cd_id }"/>		';
		oneRow +='				<input type="hidden" name="check_cdDup[]" value="FALSE"/>		';
		oneRow +='				<span class="input-group-btn"><button class="btn btn-danger" type="button" name="btn_check_cdDup[]"><i class="fa fa-search"></i>&nbsp;중복확인</button></span>		';
		oneRow +='			</div>		';
		oneRow +='		</td>		';
		oneRow +='		<td><input type="text" name="cd_nm[]" class="form-control" placeholder="코드명" /></td>		';
		oneRow +='		<td><input type="text" name="cd_cmt[]" class="form-control" placeholder="비고" /></td>		';
		oneRow +='		<td>		';
		oneRow +='			<select name="icd_type[]" class="form-control">		';
		oneRow +='				<c:forEach items="${resultListAuth }" var="list">		';
		oneRow +='					<option value="${list.cd_id }">${list.cd_nm }</option>		';
		oneRow +='				</c:forEach>		';
		oneRow +='			</select>		';
		oneRow +='			<input type="hidden" name="cd_type[]" />		';
		oneRow +='		</td>		';
		oneRow +='		<td><input type="number" name="disp_order[]" class="form-control" placeholder="정렬순서"  value="1"/></td>		';
		oneRow +='		<td>		';
		oneRow +='			<div class="checkbox">		';
		oneRow +='				<label>		';
		oneRow +='					<input type="checkbox" name="iuse_yn[]" checked=checked>사용		';
		oneRow +='					<input type="hidden" name="use_yn[]" value="Y"/>		';
		oneRow +='				</label>		';
		oneRow +='			</div>		';
		oneRow +='		</td>		';
		oneRow +='	</tr>		';
		
		
		//하위코드 추가
		//테이블의 tr자식이 있으면 tr 뒤에 붙인다. 없으면 테이블 안에 tr을 붙인다.
		if($('#dataTable').contents().size()==0){
			$('#dataTable').append(oneRow);
		}else{
			$('#dataTable tr:last').after(oneRow);
		}
		
		
	});
	
	//삭제버튼클릭이벤트
	$(document).on("click", ".logic_minusBtn", function(){
		$(this).closest("tr").remove();
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

			<!-- S:상위코드정보 -->
				<div class="row">
					<div class="col-lg-12">
						<h4>상위코드</h4>
					</div>
				</div>			
				<div class="row">
					<div class="col-lg-12">
						<div class="table-responsive">
							<table class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<th class="col-lg-1">코드</th>
										<th>코드명</th>
										<th class="col-lg-1">코드권한</th>
									</tr>
								</thead>							
								<tbody>
									<tr>
										<td>${result.cd_id }</td>
										<td>${result.cd_nm }</td>
										<td>${result.cd_type_nm }</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.row -->
			<!-- E:상위코드정보 -->	
				
				
			<!-- S:하위코드정보 -->
				<div class="row">
					<div class="col-lg-12">
						<h4>하위코드</h4>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<form name="frm" id="frm">
							<input type="hidden" name="use_yn" id="use_yn"/>
							<input type="hidden" name="pageNum" id="pageNum" value="${paramDto.pageNum}" />
							<input type="hidden" name="searchValue" id="searchValue" value="${paramDto.searchValue}"/>
							<input type="hidden" name="auto_id" id="auto_id" value="${paramDto.auto_id}"/>
							<input type="hidden" name="cd_id" id="cd_id" value="${result.cd_id }"/>
							<input type="hidden" name="high_cd_id" id="high_cd_id" value="${result.cd_id }"/>

							<input type="hidden" name="frm_save_state" id="frm_save_state" />
							<input type="hidden" name="frm_auto_id" id="frm_auto_id" />
							<input type="hidden" name="frm_cd_id" id="frm_cd_id" />
							<input type="hidden" name="frm_cd_nm" id="frm_cd_nm" />
							<input type="hidden" name="frm_cd_cmt" id="frm_cd_cmt" />
							<input type="hidden" name="frm_cd_type" id="frm_cd_type" />
							<input type="hidden" name="frm_disp_order" id="frm_disp_order" />
							<input type="hidden" name="frm_use_yn" id="frm_use_yn" />
							
						</form>
													
						<div class="table-responsive">
							<table id="dataTable" class="table table-bordered table-hover table-striped">
								<thead>
									<tr>
										<th class="col-lg-1" id="addCode"><button type="button" class="btn btn-info"><i class="fa fa-plus "></i>&nbsp;추가</button></th>
										<th class="col-lg-1">코드</th>
										<th class="col-lg-2">코드명</th>
										<th class="col-lg-2">비고</th>
										<th class="col-lg-2">코드권한</th>
										<th class="col-lg-1">정렬순서</th>
										<th class="col-lg-1">사용여부</th>
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
													<td>
														<span class="label label-warning"></span>
														<input type="hidden" name="save_state[]" value="N"/>
													</td>
													<td>
														<div class="input-group">
															<input type="text" name="cd_id[]" class="form-control" placeholder="코드" value="${result.cd_id }"/>
															<input type="hidden" name="auto_id[]" value="${result.auto_id }"/>
															<input type="hidden" name="check_cdDup[]"/>
															<span class="input-group-btn"><button class="btn btn-danger" type="button" name="btn_check_cdDup[]"><i class="fa fa-search"></i>&nbsp;중복확인</button></span>
														</div>														
													</td>
													<td><input type="text" name="cd_nm[]" class="form-control" placeholder="코드명" value="${result.cd_nm }"/></td>
													<td><input type="text" name="cd_cmt[]" class="form-control" placeholder="비고" value="${result.cd_cmt }"/></td>
													<td>
														<select name="icd_type[]" class="form-control">
															<c:forEach items="${resultListAuth }" var="list">
																<option value="${list.cd_id }">${list.cd_nm }</option>
															</c:forEach>
														</select>
														<input type="hidden" name="cd_type[]" value="${result.cd_type }"/>
													</td>
													<td><input type="number" name="disp_order[]" class="form-control" placeholder="정렬순서"  value="${result.disp_order }"/></td>
													<td>
														<div class="checkbox">
															<label>
																<input type="checkbox" name="iuse_yn[]">사용
																<input type="hidden" name="use_yn[]" value="${result.use_yn }"/>
															</label>
														</div>
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
