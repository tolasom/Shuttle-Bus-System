<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id" content="934878702971-vhv1inbu6cg8m4cggh4itr8mfuuidoo9.apps.googleusercontent.com">
<title>Shuttle Bus Management</title>

<script src="https://apis.google.com/js/platform.js" async defer></script>
<spring:url value="/resources/Bootstrap/css/style.css" var="loginStyle"/>
      <link rel="stylesheet"  type="text/css" href="${loginStyle}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>


<!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/Bootstrap/js/bootstrap.min.js" var="JSCORE"/>
    <script src="${JSCORE}"></script>
<!-- Isolated Version of Bootstrap, not needed if your site already uses Bootstrap -->
<link rel="stylesheet"  type="text/css" href="https://formden.com/static/cdn/bootstrap-iso.css" />
<!-- Bootstrap Core CSS -->	
  <spring:url value="/resources/Bootstrap/css/bootstrap.min.css" var="bootatrapCore"/>
  <spring:url value="/resources/Bootstrap/css/sb-admin.css" var="CustomCSS"/>
  <spring:url value="/resources/Bootstrap/font-awesome/css/font-awesome.min.css" var="customfontCSS"/>
  
 		<link href="${bootatrapCore}" rel="stylesheet" type="text/css">
   		<!-- Custom CSS -->
    	<link href="${CustomCSS}" rel="stylesheet" type="text/css">
    	<!-- Morris Charts CSS -->
    	<link href="${morrisCSS }" rel="stylesheet" type="text/css">
    	<!-- Custom Fonts -->
   	    <link href="${customfontCSS }" rel="stylesheet" type="text/css">
   	    
   	    <!-- Sweet alert -->
<!-- Sweet alert -->
<spring:url value="/resources/Bootstrap/css/sweetalert.css" var="alertStyle"/>
<spring:url value="/resources/Bootstrap/js/sweetalert.min.js" var="alertJS"/>
   	    	 
</head>

<spring:url value="/resources/Bootstrap/css/sweetalert.css" var="alertStyle"/>
<spring:url value="/resources/Bootstrap/js/sweetalert.min.js" var="alertJS"/>
   	    	 
<script>

</script>
  </head>
<body>
<script src="${alertJS}"></script>
<link rel="stylesheet" href="${alertStyle}">
<h1 align="center">${message}</h1>
  <div class="login-page">
  <div class="form">
  	<h1>Shuttle Bus Management</h1>
  
    <form class="login-form" id="myForm" action="<c:url value='/login' />" method="post">
    	<c:if test="${not empty error}">
			<div class="error" style="color:red">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
     	<input type='text'placeholder="Username" name='username' id="username" required>
      <input type="password" placeholder="Password" name="password" required/>
      <input type="submit" class="a" value="Login" style="color:white;">
      <div style="font-size:10px;" class="gg">
      <a href ="signup" data-toggle="modal" data-target="#myModal">Forgot Password?</a>
      <a href ="signup">Sign Up</a>
      </div>
      <div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
	     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        
    </form>
    <form>
     
    </form>
    
  </div>
</div>

   <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
  <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#myForm").on("submit",function(e){
		e.preventDefault();
		var text = $("#username").val().trim();
		var formatemail = /[!#$%^&*()+\-=\[\]{};':"\\|,<>\/?]+/;
		if(formatemail.test(text))
			{
			swal("Oops!", "You cannot input special characters", "error")  
			return
			}
		$(this).unbind("submit").submit();
	});
	
	
	
	
	$("#myForm2").on("submit",function(e){
		e.preventDefault();
		$("#toload").addClass("loader");
			$.ajax({
				url:'forgetPasswordSubmit',
				type:'GET',
				data:{email:$("#eemail").val()},
				success: function(response){			     
					if(response.status=="999")
						{
						$("#toload").removeClass("loader");
						swal("We cannot find you!", "Please give a valid email!", "error")
						$('#closing').trigger('click');
						}
					
					else 
						{
						$("#toload").removeClass("loader");
						swal("Done!","We found you! Please check your email to reset new password!", "success")
						$('#closing').trigger('click');
						//alert("<div class="alert alert-success"><strong>Success!</strong> This alert box could indicate a successful or positive action.</div>")
						}
						
				},
				error: function(err){
					console.log(JSON.stringify(err));
				}
			});			
	});
	
	
	
	
});	


var token = $('#csrfToken').val();
var header = $('#csrfHeader').val();
axios.defaults.headers.common[header] = token;
function googleSignin(data){
        axios({
            method: 'post',
            url: '/login',
            data: data
            })
          .then(function (response) {
        	  var url = response.request.responseURL;
              if(!url.includes("login")){
            	  window.location.replace(url);
              }
              
            })
            .catch(function (error) {
              console.log(error);
 }); 
}
  function onSignIn(googleUser) {
	 	var profile = googleUser.getBasicProfile();
        console.log(profile.getEmail())
        var auth2 = gapi.auth2.getAuthInstance();
     	auth2.disconnect();
        axios.post('/check_signup', {
            email: profile.getEmail(),
            password: profile.getId(),
            username:profile.getName(),
        	  name:profile.getGivenName(),
        	  profile:profile.getImageUrl()
          })
          .then(function (response) {
            if(response.data.status){
            	console.log(response.data)
                data = 'username='+profile.getEmail()+"--google" + '&password='+profile.getId()
            }
            
          googleSignin(data);
          })
          .catch(function (error) {
            console.log(error);
          });
          
      };
       
              
            
    
</script>
</html>

