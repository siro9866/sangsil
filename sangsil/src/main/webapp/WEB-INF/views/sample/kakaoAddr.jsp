<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<style>
	
	body{
		color:white;
		width: 100%;
		height: 100%;
		margin: auto;
		background-color: black;
	}
	
	#wrapper ul{margin: 30px;	padding:5px;	border: 5px dotted red;}
	#wrapper ul li{list-style-type: circle; margin-bottom: 10px;}
	
</style>

</head>

<body>


	<div id="wrapper">
		
		<form name="frm" method="post">
			<input type="text" name="query">
			<input type="text" name="page" value="1">
			<input type="text" name="size" value="5">
		</form>
		<button type="button" id="goSearch">전송</button><br><br>
		
		<div>
			<table>
				<thead></thead>
				<tbody id="dataSet">
				</tbody>
			</table>
		
		</div>
		
	</div>

<script>

	$("#goSearch").on("click", function(){
		$.ajax({
			type:"post",
			url:"/kakaoAddrQuery.mee",
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			success:function(responseData){
				var data = responseData.resultJson;
				var resultHtml = "";
				console.log(data);
				if(data.rCode == "0000"){
					// 메타
					//	검색어에 검색된 문서수
					data.meta.total_count;
					//	total_count 중에 노출가능 문서수
					data.meta.pageable_count;
					
					// 현재 페이지가 마지막 페이지인지 여부. 값이 false이면 page를 증가시켜 다음 페이지를 요청할 수 있음.
					data.meta.is_end;
					
					var documents = data.documents;
					
					var zipCode = "";
					var addressName = "";
					
					documents.forEach(function(document, i) {
						if(document.address_type == "REGION"){
							zipCode = document.address.zip_code;
							addressName = document.address_name
						}else{
							zipCode = document.road_address.zone_no;
							addressName = document.address_name;
							addressName += "(" + document.road_address.region_3depth_name;
							if(document.road_address.building_name == ""){
								addressName += ")";
							}else{
								addressName += ", " + document.road_address.building_name + ")";
							}
						}
						
						resultHtml += "<tr>";
						resultHtml += "<td>"+zipCode+"</td>";
						resultHtml += "<td>"+addressName+"</td>";
						resultHtml += "</tr>";
					});
// 					alert(data.rCode + "\n" + data.rMsg + "\n" + data.errorType + "\n" + data.message);
				}else{
					
					alert(data.rCode + "\n" + data.rMsg + "\n" + data.errorType + "\n" + data.message);
				}
				
				$("#dataSet").html(resultHtml);
				
			},
			error : function(jqXHR, status, error) {
				alert("시스템 오류입니다. \n 담당자에게 문의 하십시요.\n("+ error+")");
			}
		});
	});

</script>

</body>

</html>
