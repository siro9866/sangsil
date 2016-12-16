<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<style>
.lotto-bg1{background-color:green;}
.lotto-bg2{background-color:orange;}
.lotto-bg3{background-color:blue;}
.lotto-bg4{background-color:red;}
.lotto-bg5{background-color:violet;}
.lotto-bg6{background-color:brown;}
</style>

</head>

<body>

	<div id="wrapper">


<!-- S:FILE:header.jsp -->
<c:import url="/include.mee?fileName=/admin/include/header&depth7=menuL2"/>
<!-- E:FILE:header.jsp -->


		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							로또당첨결과
						</h1>
						<ol class="breadcrumb">
							<li>
								<i class="fa fa-dashboard"></i>  <a href="index.html">Lotto</a>
							</li>
							<li class="active">
								<i class="fa fa-table"></i> 당첨결과
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<!--	S:검색조건 -->
				<div class="row">
					<form id="frm" name="frm" method="post">
						<input type="hidden" name="pageNum" id="pageNum" value='${paramMap.pageNum}' />
						<input type="hidden" name="favority_id" id="favority_id"/>					
					
						<div class="form-group input-group col-lg-12">
							<input type="text" name="lotto_dang_num1" id="lotto_dang_num1" class="input-sm col-lg-1 lotto-bg1" placeholder="1" maxlength="2" numberOnly="true" value='${paramMap.lotto_dang_num1}'/>
							<input type="text" name="lotto_dang_num2" id="lotto_dang_num2" class="input-sm col-lg-1 lotto-bg2" placeholder="2" maxlength="2" numberOnly="true" value='${paramMap.lotto_dang_num2}'/>
							<input type="text" name="lotto_dang_num3" id="lotto_dang_num3" class="input-sm col-lg-1 lotto-bg3" placeholder="3" maxlength="2" numberOnly="true" value='${paramMap.lotto_dang_num3}'/>
							<input type="text" name="lotto_dang_num4" id="lotto_dang_num4" class="input-sm col-lg-1 lotto-bg4" placeholder="4" maxlength="2" numberOnly="true" value='${paramMap.lotto_dang_num4}'/>
							<input type="text" name="lotto_dang_num5" id="lotto_dang_num5" class="input-sm col-lg-1 lotto-bg5" placeholder="5" maxlength="2" numberOnly="true" value='${paramMap.lotto_dang_num5}'/>
							<input type="text" name="lotto_dang_num6" id="lotto_dang_num6" class="input-sm col-lg-1 lotto-bg6" placeholder="6" maxlength="2" numberOnly="true" value='${paramMap.lotto_dang_num6}'/>
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
										<th class="col-lg-1">등수</th>
										<th class="col-lg-1">회차</th>
										<th class="col-lg-1">추첨일</th>
										<th class="col-lg-6">추첨번호</th>
										<th class="col-lg-2">1등수</th>
										<th class="col-lg-1">1등상금</th>
										<th class="col-lg-2">2등수</th>
										<th class="col-lg-1">2등상금</th>
										<th class="col-lg-2">3등수</th>
										<th class="col-lg-1">3등상금</th>
										<th class="col-lg-2">4등수</th>
										<th class="col-lg-1">4등상금</th>
										<th class="col-lg-2">5등수</th>
										<th class="col-lg-1">5등상금</th>
									</tr>
								</thead>
								<tbody>
								
									<c:choose>
										<c:when test="${empty resultList }">
											<tr><td colspan="14">당첨 내용이 없습니다.</td></tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${resultList }" var="result">
												<tr id="${result.lotto_id }">
													<td>
														<c:choose>
															<c:when test="${result.lotto_dang_cnt  eq 6}">1등</c:when>
															<c:when test="${result.lotto_dang_cnt  eq 5}">3등</c:when>
															<c:when test="${result.lotto_dang_cnt  eq 4}">4등</c:when>
															<c:when test="${result.lotto_dang_cnt  eq 3}">5등</c:when>
														</c:choose>
													</td>
													<td>${result.lotto_cnt }</td>
													<td>
														<fmt:parseDate var="lotto_date" value="${result.lotto_date }" pattern="yyyy-MM-dd HH:mm:ss.S"/>
														<fmt:formatDate value="${lotto_date }" pattern="yyyy.MM.dd"/>														
													</td>
													<td>
														
														<c:choose>
															<c:when test="${paramMap.lotto_dang_num1 eq result.lotto_dang_num1}">
																<span class="lotto-bg1">${result.lotto_dang_num1 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num2 eq result.lotto_dang_num1}">
																<span class="lotto-bg2">${result.lotto_dang_num1 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num3 eq result.lotto_dang_num1}">
																<span class="lotto-bg3">${result.lotto_dang_num1 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num4 eq result.lotto_dang_num1}">
																<span class="lotto-bg4">${result.lotto_dang_num1 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num5 eq result.lotto_dang_num1}">
																<span class="lotto-bg5">${result.lotto_dang_num1 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num6 eq result.lotto_dang_num1}">
																<span class="lotto-bg6">${result.lotto_dang_num1 }</span>, 
															</c:when>
															<c:otherwise>${result.lotto_dang_num1 },</c:otherwise>
														</c:choose>
														
														<c:choose>
															<c:when test="${paramMap.lotto_dang_num1 eq result.lotto_dang_num2}">
																<span class="lotto-bg1">${result.lotto_dang_num2 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num2 eq result.lotto_dang_num2}">
																<span class="lotto-bg2">${result.lotto_dang_num2 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num3 eq result.lotto_dang_num2}">
																<span class="lotto-bg3">${result.lotto_dang_num2 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num4 eq result.lotto_dang_num2}">
																<span class="lotto-bg4">${result.lotto_dang_num2 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num5 eq result.lotto_dang_num2}">
																<span class="lotto-bg5">${result.lotto_dang_num2 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num6 eq result.lotto_dang_num2}">
																<span class="lotto-bg6">${result.lotto_dang_num2 }</span>, 
															</c:when>
															<c:otherwise>${result.lotto_dang_num2 },</c:otherwise>
														</c:choose>
														
														<c:choose>
															<c:when test="${paramMap.lotto_dang_num1 eq result.lotto_dang_num3}">
																<span class="lotto-bg1">${result.lotto_dang_num3 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num2 eq result.lotto_dang_num3}">
																<span class="lotto-bg2">${result.lotto_dang_num3 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num3 eq result.lotto_dang_num3}">
																<span class="lotto-bg3">${result.lotto_dang_num3 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num4 eq result.lotto_dang_num3}">
																<span class="lotto-bg4">${result.lotto_dang_num3 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num5 eq result.lotto_dang_num3}">
																<span class="lotto-bg5">${result.lotto_dang_num3 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num6 eq result.lotto_dang_num3}">
																<span class="lotto-bg6">${result.lotto_dang_num3 }</span>, 
															</c:when>
															<c:otherwise>${result.lotto_dang_num3 },</c:otherwise>
														</c:choose>
														
														<c:choose>
															<c:when test="${paramMap.lotto_dang_num1 eq result.lotto_dang_num4}">
																<span class="lotto-bg1">${result.lotto_dang_num4 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num2 eq result.lotto_dang_num4}">
																<span class="lotto-bg2">${result.lotto_dang_num4 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num3 eq result.lotto_dang_num4}">
																<span class="lotto-bg3">${result.lotto_dang_num4 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num4 eq result.lotto_dang_num4}">
																<span class="lotto-bg4">${result.lotto_dang_num4 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num5 eq result.lotto_dang_num4}">
																<span class="lotto-bg5">${result.lotto_dang_num4 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num6 eq result.lotto_dang_num4}">
																<span class="lotto-bg6">${result.lotto_dang_num4 }</span>, 
															</c:when>
															<c:otherwise>${result.lotto_dang_num4 },</c:otherwise>
														</c:choose>
														
														<c:choose>
															<c:when test="${paramMap.lotto_dang_num1 eq result.lotto_dang_num5}">
																<span class="lotto-bg1">${result.lotto_dang_num5 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num2 eq result.lotto_dang_num5}">
																<span class="lotto-bg2">${result.lotto_dang_num5 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num3 eq result.lotto_dang_num5}">
																<span class="lotto-bg3">${result.lotto_dang_num5 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num4 eq result.lotto_dang_num5}">
																<span class="lotto-bg4">${result.lotto_dang_num5 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num5 eq result.lotto_dang_num5}">
																<span class="lotto-bg5">${result.lotto_dang_num5 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num6 eq result.lotto_dang_num5}">
																<span class="lotto-bg6">${result.lotto_dang_num5 }</span>, 
															</c:when>
															<c:otherwise>${result.lotto_dang_num5 },</c:otherwise>
														</c:choose>
														
														<c:choose>
															<c:when test="${paramMap.lotto_dang_num1 eq result.lotto_dang_num6}">
																<span class="lotto-bg1">${result.lotto_dang_num6 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num2 eq result.lotto_dang_num6}">
																<span class="lotto-bg2">${result.lotto_dang_num6 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num3 eq result.lotto_dang_num6}">
																<span class="lotto-bg3">${result.lotto_dang_num6 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num4 eq result.lotto_dang_num6}">
																<span class="lotto-bg4">${result.lotto_dang_num6 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num5 eq result.lotto_dang_num6}">
																<span class="lotto-bg5">${result.lotto_dang_num6 }</span>, 
															</c:when>
															<c:when test="${paramMap.lotto_dang_num6 eq result.lotto_dang_num6}">
																<span class="lotto-bg6">${result.lotto_dang_num6 }</span>, 
															</c:when>
															<c:otherwise>${result.lotto_dang_num6 },</c:otherwise>
														</c:choose>


														<br><span style="color:gray">${result.lotto_dang_num9 }</span> 
													</td>
													<td><fmt:formatNumber value="${result.lotto_dang1_cnt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang1_amt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang2_cnt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang2_amt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang3_cnt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang3_amt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang4_cnt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang4_amt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang5_cnt}" groupingUsed="true"/></td>
													<td><fmt:formatNumber value="${result.lotto_dang5_amt}" groupingUsed="true"/></td>
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
						<div class="panel pull-right">
							<button type="button" id="btnList" class="btn btn-primary"><i class="fa fa-file "></i>&nbsp;목록</button>
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


<script>

	//검색어 입력후 엔터키 이벤트
	$("#btnSearch").bind("click", function(e) {
		$("#pageNum").val("1");
		$("#frm").attr("action", "/front/lotto/listDang.mee");
		$("#frm").submit();
	});		

	$("#btnList").bind("click", function(e) {
		$("#pageNum").val("1");
		$("#frm").attr("action", "/front/lotto/list.mee");
		$("#frm").submit();
	});		
	
	// 포커스시 해당 내용 삭제
	$("input").on("focus", function(){
		$(this).val("");
	});
	
	// 숫자만 입력
	$(document).on("keyup", "input:text[numberOnly]", function() {$(this).val( $(this).val().replace(/[^0-9]/gi,"") );});
	
	
	$("#searchValue1").val("12");
	$("#searchValue2").val("14");
	$("#searchValue3").val("21");
	$("#searchValue4").val("30");
	$("#searchValue5").val("39");
	$("#searchValue6").val("43");
		
	//페이징
	function goPage(pageNo){
		$("#pageNum").val(pageNo);
		$("#frm").attr("action", "/front/lotto/listDang.mee");
		$("#frm").submit();
	};
</script>

</body>

</html>
