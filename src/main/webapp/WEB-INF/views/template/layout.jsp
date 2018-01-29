<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:insertAttribute name="title" /></title>
<!--  jQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<!--  jQuery Validation -->
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

	
    
	
	

<!-- Bootstrap Date-Picker Plugin -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

<!-- Sweet alert -->
<spring:url value="/resources/Bootstrap/css/sweetalert.css" var="alertStyle"/>
      <link rel="stylesheet" href="${alertStyle}">
<spring:url value="/resources/Bootstrap/js/sweetalert.min.js" var="alertJS"/>
      <script src="${alertJS}"></script>
<!-- Sweet alert -->
<spring:url value="/resources/Bootstrap/css/sweetalert-form.css" var="alertForm"/>
      <link rel="stylesheet" href="${alertForm}">
<spring:url value="/resources/Bootstrap/js/sweetalert-form.js" var="alertFormJS"/>
      <script src="${alertFormJS}"></script>
<spring:url value="/resources/Bootstrap/js/toPDF.js" var="toPDF"/>
      <script src="${toPDF}"></script>	
         
          
<!-- Checkbox CSS -->
<spring:url value="/resources/Bootstrap/css/checkbox/build.css" var="checkboxStyle"/>
      <link rel="stylesheet" href="${checkboxStyle}"> 
<spring:url value="/resources/Bootstrap/css/select.css" var="select"/>
      <link rel="stylesheet" href="${select}"> 
      
<!-- Date Picker -->     
<spring:url value="/resources/Bootstrap/js/date/jquery.js" var="dateJS"/>
<spring:url value="/resources/Bootstrap/js/date/jquery-ui.js" var="dateJS2"/>
<script src="${dateJS}"></script>
<script src="${dateJS2}"></script>
<spring:url value="/resources/Bootstrap/css/date/jquery-ui.css" var="dateStyle"/>
<link rel="stylesheet" href="${dateStyle}">

<!-- Multiple Select -->     
<spring:url value="/resources/Bootstrap/js/multipleselect/select2.full.min.js" var="MultipleJS"/>
<spring:url value="/resources/Bootstrap/js/multipleselect/en.js" var="MultipleJS2"/>
<script src="${MultipleJS}"></script>
<script src="${MultipleJS2}"></script>
<spring:url value="/resources/Bootstrap/css/multipleselect/select2.min.css" var="MultipleCSS"/>
<link rel="stylesheet" href="${MultipleCSS}">

<!-- Task Card -->
<spring:url value="/resources/Bootstrap/css/taskcard.css" var="TaskCard"/>
<link rel="stylesheet" href="${TaskCard}">

<!-- Reporting  -->
<spring:url value="/resources/Bootstrap/js/reporting.js" var="ReportingJS"/>
<script src="${ReportingJS}"></script>
<spring:url value="/resources/Bootstrap/css/reporting.css" var="ReportingCSS"/>
<link rel="stylesheet" href="${ReportingCSS}">
<spring:url value="/resources/Bootstrap/css/projectTable.css" var="projectTableReport"/>
<link rel="stylesheet" href="${projectTableReport}">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.5/jspdf.debug.js"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/MrRio/jsPDF/master/dist/jspdf.min.js"></script>
<script type="text/javascript" src="https://cdn.rawgit.com/niklasvh/html2canvas/0.5.0-alpha2/dist/html2canvas.js"></script>
<script>
	var p = [];
</script>

</head>



<body>
 <div id="wrapper">
 		  <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="sidebar" />
		</nav>
		<div id="page-wrapper">
			<div class="contianer-fluid">
			<tiles:insertAttribute name="body" />
			</div>
		</div>
    
  </div>

<script>
                    $(document).ready(function(){
                    	$(".js-example-basic-multiple").select2();
                    	var date_input=$('input[name="date"]');
                        var options={
                          format: 'mm/dd/yyyy',
                          todayHighlight: true,
                          autoclose: true,
                        };
                        date_input.datepicker(options);
                        
                        
                        
                    });
</script>
</body>
</html>