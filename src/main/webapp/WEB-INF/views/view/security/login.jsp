<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KIT Point</title>
  <!-- Login CSS -->
<spring:url value="/resources/Bootstrap/css/style.css" var="loginStyle"/>
      <link rel="stylesheet" href="${loginStyle}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.js"></script>


<!-- Bootstrap Core JavaScript -->
    <spring:url value="/resources/Bootstrap/js/bootstrap.min.js" var="JSCORE"/>
    <script src="${JSCORE}"></script>
	

    	
<!-- Isolated Version of Bootstrap, not needed if your site already uses Bootstrap -->
<link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" />
<!-- Bootstrap Core CSS -->	
  <spring:url value="/resources/Bootstrap/css/bootstrap.min.css" var="bootatrapCore"/>
  <spring:url value="/resources/Bootstrap/css/sb-admin.css" var="CustomCSS"/>
  <spring:url value="/resources/Bootstrap/font-awesome/css/font-awesome.min.css" var="customfontCSS"/>
  
 		<link href="${bootatrapCore}" rel="stylesheet">
   		<!-- Custom CSS -->
    	<link href="${CustomCSS}" rel="stylesheet">
    	<!-- Morris Charts CSS -->
    	<link href="${morrisCSS }" rel="stylesheet">
    	<!-- Custom Fonts -->
   	    <link href="${customfontCSS }" rel="stylesheet" type="text/css">
   	    
   	    <!-- Sweet alert -->
<!-- Sweet alert -->
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
  	<h1>KIT Point Management</h1>
  
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
      <a href ="#" data-toggle="modal" data-target="#myModal">Forgot Password?</a>
      </div>
	<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

    </form>
  </div>
</div>


<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Find Your Account</h4>
        </div>
        
        <div class="modal-body">
     <form class="form-group" id="myForm2">
     <p><b>Please enter your email to search for your account.</b><span id ="toload"></span></p>
    <div>
      
      <div class="col-sm-7">
      <div style="display: inline-block;">
      <input type="text" class="form-control" id="eemail" maxlength="60" placeholder="Enter your email" required>
      
      </div>
      <br>
      <button id="bsubmit" type="submit" class="btn btn-default" style="display:none;">Submit</button>
      </div>
      
    </div>
  </form>
  <br>
        </div>
        <div class="modal-footer">	
          <button onClick="goTO()" class="btn btn-default">Submit</button>
          <button type="button" id="closing" class="btn btn-default" data-dismiss="modal">Close</button>
           
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
</script>
</html>

	