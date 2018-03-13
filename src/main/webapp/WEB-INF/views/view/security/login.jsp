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
      <link rel="stylesheet" href="${loginStyle}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>



<spring:url value="/resources/Bootstrap/css/sweetalert.css" var="alertStyle"/>
<spring:url value="/resources/Bootstrap/js/sweetalert.min.js" var="alertJS"/>
   	    	 
<script>
  
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
});	
</script>
  </head>
<body onload='document.loginForm.username.focus()'>
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
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
        <input type="hidden" id="csrfToken" value="${_csrf.token}"/>
    <input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
    </form>

    <form id="googleLogin" action="<c:url value='/login' />" method="post">
      
      <input type='hidden' name='gusername' id="gusername" required>
      <input type="hidden" name="gpassword" id="gpassword" required/>
      <input type="hidden" name="${_csrf.parameterName}"
        value="${_csrf.token}" />
    </form>
  </div>
</div>

  </form>
        </div>
     
      </div>
      
    </div>
  </div>	
</body>
<script type="text/javascript">
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


goTO = function(){
$('#bsubmit').trigger('click');
}
var token = $('#csrfToken').val();
var header = $('#csrfHeader').val();
axios.defaults.headers.common[header] = token;
  
  function onSignIn(googleUser) {
        // Useful data for your client-side scripts:
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
        
        
        axios.post('/check_signup', {
            email: profile.getEmail(),
            password: profile.getId(),
            username:profile.getName(),
        	  name:profile.getGivenName(),
        	  profile:profile.getImageUrl()
          })
          .then(function (response) {
            if(response.data.status)
            /*$('#gusername').val(profile.getEmail()+'--'+"google")
            $('#gpassword').val(profile.getId())
            //$('#googleLogin').submit();*/
          signin = 'username='+profile.getEmail()+"--google" + '&password='+profile.getId()
          axios({
            method: 'post',
            url: '/login',
            data: signin
            })
          .then(function (response) {
              console.log(response);
            })
            .catch(function (error) {
              console.log(error);
            });  
              console.log(response)
            })
          .catch(function (error) {
            console.log(error);
          });
          
      };
</script>
</html>


 