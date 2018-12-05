<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp"%>


<div class="row">
	<div class="col-lg-12">
		<div class="table-responsive">
			<table id="dataTable" class="table table-bordered table-hover table-striped">
				<thead>
					<tr>
						<th class="col-lg-1">A</th>
						<th class="col-lg-1">B</th>
						<th class="col-lg-1">C</th>
						<th class="col-lg-1">D</th>
						<th class="col-lg-1">E</th>
						<th class="col-lg-1">F</th>
					</tr>
				</thead>
				<tbody>
				
					<c:choose>
						<c:when test="${empty resultList }">
							<tr><td colspan="6">등록된 내용이 없습니다.</td></tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${resultList }" var="result">
								<tr>
									<td><input type="hidden" name="board_tag" value="${result.A }" />${result.A }</td>
									<td><input type="hidden" name="board_title" value="${result.B }" />${result.B }</td>
									<td><input type="hidden" name="board_txt" value="${result.C }" />${result.C }</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
				</tbody>
			</table>
		</div>
	</div>
</div>
