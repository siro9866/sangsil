<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<script>
$(function(){

	
	//등록 이벤트
	$("#btnSave").bind("click", function(){
		if($("#user_nm").val().trim() == ""){
			alert("닉네임을 입력해 주세요");
			$("#user_nm").val("");
			$("#user_nm").focus();
			return;
		}
		if($("#user_nm").val().trim().length < 2){
			alert("닉네임을 2자 이상 입력해 주세요.");
			$("#user_nm").focus();
			return;
		}
		
		var goUrl = "/common/login/update_user_nm.mee"
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
					$("form[name=frm]").attr("action", "/");
					$("form[name=frm]").submit();
				}else{
					alert(data.rMsg);
					$("#user_nm").val("");
					$("#user_nm").focus();
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


<style>
	#pgWrap{width:300px; margin-left: 100px; border: solid 2px white;}
</style>


</head>

<body>
	<div id="pgWrap">
		<form name="frm">
			<label for="user_nm">닉네임</label>
			<label class="radio-inline">
				<input type="text" name="user_nm" id="user_nm" maxlength="10" placeholder="닉네임" />
			</label>
			<button type="button" id="btnSave" class="btn btn-primary"><i class="fa fa-save "></i>&nbsp;저장</button>
		</form>
	</div>
</body>

</html>
