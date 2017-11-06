<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>

<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<spring:eval expression="@config['CD_ID_BAA01']" var="board_gbn_dev" />
<spring:eval expression="@config['CD_ID_BAA02']" var="board_gbn_normal" />
<spring:eval expression="@config['CD_ID_BAA03']" var="board_gbn_image" />

<spring:eval expression="@config['LOGIN_GBN_00']" var="login_gbn_00" />		<!-- Mee  -->
<spring:eval expression="@config['LOGIN_GBN_01']" var="login_gbn_01" />		<!-- 네이버  -->
<spring:eval expression="@config['LOGIN_GBN_02']" var="login_gbn_02" />		<!-- 카카오  -->
<spring:eval expression="@config['LOGIN_GBN_03']" var="login_gbn_03" />		<!-- 구글  -->
<spring:eval expression="@config['LOGIN_GBN_04']" var="login_gbn_04" />		<!-- 페이스북  -->

<spring:eval expression="@config['KAKAO_LOGIN_APP_ID']" var="kakao_login_app_id" />
<spring:eval expression="@config['FACEBOOK_LOGIN_APP_ID']" var="facebook_login_app_id" />
<script src="//connect.facebook.net/en_US/all.js"></script>

<script>

var UserAgent = navigator.userAgent;
var browserCheck="";
if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
{
	browserCheck = "MOBILE";
}
else
{
	browserCheck = "PC";
}

	//페이스북로그아웃 때문에 스크립트사용 
 window.fbAsyncInit = function() {
  FB.init({
    appId      : '${facebook_login_app_id }',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.2' // use version 2.2
  });
  };
	// Load the SDK asynchronously
	(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

	$(function(){
		
		//뎁스2단계 드롭다운 열기
		if($("#${depth2}") != ""){
			$("#${depth2}").parent("ul").addClass("in");
			$("#${depth2}").addClass("collapsed");
		}
		
		logout = function(){
// 			alert("세션: ${sessionScope.LOGIN_GBN} \n gbn1: ${login_gbn_01} \n gbn2: ${login_gbn_02}");
			if(confirm("로그아웃 하시겠습니까?")){
				if("${sessionScope.LOGIN_GBN}" == "${login_gbn_01}"){
					//네이버로그아웃 
					goLogOut();
				}else if("${sessionScope.LOGIN_GBN}" == "${login_gbn_02}"){
					//카카오로그아웃 
					  // 사용할 앱의 JavaScript 키를 설정해 주세요.
					  Kakao.init("${kakao_login_app_id }");
					  // 카카오 로그인 버튼을 생성합니다.
					  Kakao.Auth.logout(goLogOut);
				}else if("${sessionScope.LOGIN_GBN}" == "${login_gbn_04}"){
					FB.logout(function(response) {
						   // Person is now logged out
						goLogOut();
					});
				}else{
					goLogOut();
				}
			}
		}
		
		goLogOut = function(){
			location.href = "/common/logout/logout.mee";
		}
		
	});
</script>


<!-- S:FILE:header.jsp -->
		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">캬캬캬</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> <b class="caret"></b></a>
					<ul class="dropdown-menu message-dropdown">
						<li class="message-preview">
							<a href="#">
								<div class="media">
									<span class="pull-left">
										<img class="media-object" src="http://placehold.it/50x50" alt="">
									</span>
									<div class="media-body">
										<h5 class="media-heading"><strong>John Smith</strong>
										</h5>
										<p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
										<p>Lorem ipsum dolor sit amet, consectetur...</p>
									</div>
								</div>
							</a>
						</li>
						<li class="message-preview">
							<a href="#">
								<div class="media">
									<span class="pull-left">
										<img class="media-object" src="http://placehold.it/50x50" alt="">
									</span>
									<div class="media-body">
										<h5 class="media-heading"><strong>John Smith</strong>
										</h5>
										<p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
										<p>Lorem ipsum dolor sit amet, consectetur...</p>
									</div>
								</div>
							</a>
						</li>
						<li class="message-preview">
							<a href="#">
								<div class="media">
									<span class="pull-left">
										<img class="media-object" src="http://placehold.it/50x50" alt="">
									</span>
									<div class="media-body">
										<h5 class="media-heading"><strong>John Smith</strong>
										</h5>
										<p class="small text-muted"><i class="fa fa-clock-o"></i> Yesterday at 4:32 PM</p>
										<p>Lorem ipsum dolor sit amet, consectetur...</p>
									</div>
								</div>
							</a>
						</li>
						<li class="message-footer">
							<a href="#">Read All New Messages</a>
						</li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <b class="caret"></b></a>
					<ul class="dropdown-menu alert-dropdown">
						<li>
							<a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
						</li>
						<li>
							<a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
						</li>
						<li>
							<a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
						</li>
						<li>
							<a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
						</li>
						<li>
							<a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
						</li>
						<li>
							<a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
						</li>
						<li class="divider"></li>
						<li>
							<a href="#">View All</a>
						</li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li>
							<a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
						</li>
						<li>
							<a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
						</li>
						<li>
							<a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li>
							<a href="javascript:logout();"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
						</li>
					</ul>
				</li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul id="sideMenu" class="nav navbar-nav side-nav">
					<li id="menuL1">
						<a href="index.html"><i class="fa fa-fw fa-dashboard"></i> Dashboard</a>
					</li>
					<li id="menuL2">
						<a href="/admin/favority/list.mee"><i class="fa fa-fw fa-binoculars "></i> 즐겨찾기관리</a>
					</li>
					
					<!-- 게시판 -->
					<li id="menuL3">
						<a href="javascript:;" data-toggle="collapse" data-target="#menu_board"><i class="fa fa-fw fa-file"></i> 게시판관리<i class="fa fa-fw fa-caret-down"></i></a>
						<ul id="menu_board" class="collapse">
							<li id="menuL3_1">
								<a href="/admin/board/list.mee?board_gbn=${board_gbn_dev }">개발게시판관리</a>
							</li>
							<li id="menuL3_2">
								<a href="/admin/board/list.mee?board_gbn=${board_gbn_normal }">일반게시판관리</a>
							</li>
							<li id="menuL3_3">
								<a href="/admin/board/list.mee?board_gbn=${board_gbn_image }">이미지게시판관리</a>
							</li>
						</ul>
					</li>					
					
					<li id="menuL10">
						<a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> 설정 <i class="fa fa-fw fa-caret-down"></i></a>
						<ul id="demo" class="collapse">
							<li id="menuL10_1">
								<a href="/admin/cd/list.mee">코드관리</a>
							</li>
							<li id="menuL10_2">
								<a href="/admin/user/list.mee">회원관리</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="/sample.mee"><i class="fa fa-fw fa-dashboard"></i> SAMPLE PAGE</a>
					</li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>
<!-- E:FILE:header.jsp -->