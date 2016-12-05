<%@ page language= "java" contentType ="text/html; charset=UTF-8" pageEncoding ="UTF-8"%>

<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<c:import url="/include.mee?fileName=/admin/include/doctype"/>
<c:import url="/include.mee?fileName=/admin/include/style"/>
<c:import url="/include.mee?fileName=/admin/include/script"/>

<!-- 카카오 로그인 스크립트  -->
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="//connect.facebook.net/en_US/all.js"></script>

<spring:eval expression="@config['KAKAO_LOGIN_APP_ID']" var="kakao_login_app_id" />
<spring:eval expression="@config['FACEBOOK_LOGIN_APP_ID']" var="facebook_login_app_id" />
<spring:eval expression="@config['LOGIN_GBN_00']" var="login_gbn_00" />		<!-- Mee  -->
<spring:eval expression="@config['LOGIN_GBN_01']" var="login_gbn_01" />		<!-- 네이버  -->
<spring:eval expression="@config['LOGIN_GBN_02']" var="login_gbn_02" />		<!-- 카카오  -->
<spring:eval expression="@config['LOGIN_GBN_03']" var="login_gbn_03" />		<!-- 구글  -->
<spring:eval expression="@config['LOGIN_GBN_04']" var="login_gbn_04" />		<!-- 페이스북  -->

<script>
var goMeeUrl = "/common/login/form_mee.mee";
var goNaverUrl = "/common/login/form_naver.mee";
var goKakaoUrl = "/common/login/loginCallback.mee";
var goGoogleUrl = "/common/login/form_google.mee";
var goFacebookUrl = "/common/login/loginCallback.mee";
var goTwitterUrl = "/common/login/form_twitter.mee";

$(function(){
	
	// Mee 로그인 
	$("#btnMeeLogin").on("click", function(){
		$.ajax({
			type:"post",
			async:true,
			url:goMeeUrl,
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			success:function(data){
				if(data.rCode == "0000"){
					location.href = data.mee_req_url
				}else{
					alert(data.rReason);
					return;
				}	
			},
			error:function(){
				alert("오류입니다.");
				return;						
			}
		});
	});
	
	//네이버 로그인 
	$("#btnNaverLogin").on("click", function(){
		$.ajax({
			type:"post",
			async:true,
			url:goNaverUrl,
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			success:function(data){
				if(data.rCode == "0000"){
					//alert("naver_req_url:"+data.naver_req_url);
					location.href = data.naver_req_url;
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
	
	//구글 로그인 
	$("#btGoogleLogin").on("click", function(){
		$.ajax({
			type:"post",
			async:true,
			url:goGoogleUrl,
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			success:function(data){
				if(data.rCode == "0000"){
					//alert("google_req_url:"+data.google_req_url);
					location.href = data.google_req_url;
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
	
	
	
	//카카오톡로그인 
	// 사용할 앱의 JavaScript 키를 설정해 주세요.
	var kakaoAouth;
	var kakaoProfile;
	Kakao.init('${kakao_login_app_id }');
	loginWithKakao = function() {
		// 로그인 창을 띄웁니다.
		Kakao.Auth.login({
			success: function(authObj) {
				//alert(JSON.stringify(authObj));
				kakaoAouth = JSON.stringify(authObj)
			      Kakao.API.request({
			          url: '/v1/user/me',
			          success: function(res) {
			            //alert(JSON.stringify(res));
			        	  kakaoProfile = JSON.stringify(res);
			        	  loginKakao();
			          },
			          fail: function(error) {
			            alert(JSON.stringify(error))
			          }
			        });
			},
			fail: function(err) {
				alert(JSON.stringify(err))
			}
		});
	}
	
	//카카오 API로 로그인/회원정보를 이용해 로그인 처
	loginKakao = function(){
		$("input[name=param_login_gbn]").val("${login_gbn_02 }");
		$("input[name=param_kakaoAouth]").val(kakaoAouth);
		$("input[name=param_kakaoProfile]").val(kakaoProfile);
		$("form[name=frm]").attr("action", goKakaoUrl);
		//$("form[name=frm]").attr('target', '_self');
		$("form[name=frm]").submit();
	}	
	
	
	showbPopup = function(data){
		$("#element_to_pop_up").bPopup({
			content:'iframe', //'ajax', 'iframe' or 'image'
			contentContainer:'.content',
			loadUrl: data //Uses jQuery.load()
			
			//fadeSpeed: 'slow', //can be a string ('slow'/'fast') or int
			//followSpeed: 1500, //can be a string ('slow'/'fast') or int
			//modalColor: 'greenYellow'
			
			//speed: 650,
			//transition: 'slideIn',
			//transitionClose: 'slideBack'
		});
	}
	
	//트위터 로그인 
	$("#btnTwitterLogin").on("click", function(){
		$.ajax({
			type:"post",
			async:true,
			url:goTwitterUrl,
			data:$("form[name=frm]").serialize(),
			dataType:"json",
			success:function(data){
				if(data.rCode == "0000"){
					alert("twitter_req_url:"+data.twitter_req_url);
					location.href = data.twitter_req_url;
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

<!-- 페이스북아이디로그인  -->
<script>

  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
//     alert("statusChangeCallback:\n"+JSON.stringify(response));
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
//       alert("OK");
      testAPI(response);
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
//       document.getElementById('status').innerHTML = 'Please log into this app.';
//       alert("Please log into this app.");
    	console.log('Please log into this app.');
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
//       document.getElementById('status').innerHTML = 'Please log into Facebook.';
//       alert("Please log into Facebook.");
    	console.log('Please log into Facebook.');
    }
  }

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
    
    //추가요청할 정보기재 
	FB.login(function(response) {
		statusChangeCallback(response); //반드시추가 *_*
	} , {scope: "email,user_birthday, user_photos"} );
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '${facebook_login_app_id }',
    cookie     : true,  // enable cookies to allow the server to access 
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.2' // use version 2.2
  });

  // Now that we've initialized the JavaScript SDK, we call 
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });

  };

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI(responseStats) {
    console.log('Welcome!  Fetching your information.... ');
    ///me/permissions
    FB.api('/me', {fields: 'email, name'}, function(responseMe) {
          if (responseMe && !responseMe.error) {
            /* handle the result */
//             alert("me"+"\n"+JSON.stringify(responseMe));
	       	    document.frm.param_login_gbn.value = "${login_gbn_04 }";
	       	    document.frm.param_facebooMe.value = JSON.stringify(responseMe);
	       	    document.frm.param_facebookStats.value = JSON.stringify(responseStats);
	       	    document.frm.action = goFacebookUrl;
	       	    document.frm.submit();
          }else{
        	alert("페이스북 로그인 오류입니다. 관리자에게 문의 바랍니다.\n" + responseMe.error);
          }
    });
  }
</script>


</head>

<body>
<div id="wrapper">
	<form name="frm" method="post">
		<input type="hidden" name="param_login_gbn" />
		
		<input type="hidden" name="param_kakaoAouth" />
		<input type="hidden" name="param_kakaoProfile" />
		
		<input type="hidden" name="param_facebooMe" />
		<input type="hidden" name="param_facebookStats" />
		
		
		<div>
		
			아이디 : <input type="text" name="p_id" /><br><br>
			패 스 : <input type="text" name="p_pw" /><br><br>
			<button type="button" id="btnMeeLogin">Mee 아이디로 로그인</button><br><br>
		
			<button type="button" id="btnNaverLogin" style="width:250px;height:50px;"><img alt="네이버아이디로로그인" src="/resources/images/common/login/loginIcon_naver.PNG" width="250px" height="50px"></button>
			<br><br>
			<a href="javascript:loginWithKakao()" id="custom-login-btn"><img src="http://mud-kage.kakao.co.kr/14/dn/btqbjxsO6vP/KPiGpdnsubSq3a0PHEGUK1/o.jpg" width="250px" height="50px"/></a>
			<br><br>
<!-- 			<button type="button" id="btGoogleLogin"><img alt="구글아이디로로그인" src="/resources/images/common/login/loginIcon_naver.PNG" width="250px" height="50px"></button> -->
			<!-- 로그인 버튼 참고 url:https://developers.facebook.com/docs/facebook-login/web/login-button -->
<!-- 			<div class="fb-login-button" id="btnFacebookLogin" data-max-rows="1" data-size="xlarge" data-show-faces="false" data-auto-logout-link="false"></div> -->
			<button type="button" id="btnFacebookLogin" style="width:250px;height:50px;" onclick="javascript:checkLoginState();"><img alt="페이스북아이디로로그인" src="/resources/images/common/login/loginIcon_facebook.png" width="250px" height="50px"></button>
			<br><br>
<!-- 			<button type="button" id="btnTwitterLogin" style="width:250px;height:50px;"><img alt="트위터아이디로로그인" src="/resources/images/common/login/loginIcon_twitter.png" width="250px" height="50px"></button> -->
			
		</div>
	</form>

<!-- 	<div id="element_to_pop_up"> -->
<!-- 		<a class="b-close">x<a/> -->
<!-- 		<div class="content"> -->
<!-- 		</div> -->
<!-- 	</div> -->

<!-- <div id="naver_id_login"></div> -->

</div>



</body>

</html>
