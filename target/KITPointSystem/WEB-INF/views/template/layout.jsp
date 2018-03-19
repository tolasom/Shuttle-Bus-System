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
<spring:url value="/resources/Bootstrap/js/bootstrap.min.js" var="BootJS"/>
      <script src="${BootJS}"></script>

      
      
<!-- Sweet alert -->
<spring:url value="/resources/Bootstrap/css/sweetalert.css" var="alertStyle"/>
      <link rel="stylesheet" href="${alertStyle}">
<spring:url value="/resources/Bootstrap/js/sweetalert.min.js" var="alertJS"/>
      <script src="${alertJS}"></script>



    
<!-- App CSS -->
<spring:url value="/resources/Bootstrap/css/app-blue.css" var="appBlue"/>
      <link rel="stylesheet" href="${appBlue}"> 
<spring:url value="/resources/Bootstrap/css/vendor.css" var="vendorCss"/>
      <link rel="stylesheet" href="${vendorCss}"> 
      
      


<!-- App JS -->      
<spring:url value="/resources/Bootstrap/js/app.js" var="AppJS"/>
<spring:url value="/resources/Bootstrap/js/vendor.js" var="VendorJS"/>
<script src="${AppJS}"></script>
<script src="${VendorJS}"></script>
      
<!-- Date Picker -->     
<spring:url value="/resources/Bootstrap/js/date/jquery.js" var="dateJS"/>
<spring:url value="/resources/Bootstrap/js/date/jquery-ui.js" var="dateJS2"/>
<spring:url value="/resources/Bootstrap/js/time/wickedpicker.js" var="TimeJS"/>
<script src="${dateJS}"></script>
<script src="${dateJS2}"></script>
<script src="${TimeJS}"></script>
<spring:url value="/resources/Bootstrap/css/date/jquery-ui.css" var="dateStyle"/>
<link rel="stylesheet" href="${dateStyle}">
<spring:url value="/resources/Bootstrap/css/time/wickedpicker.css" var="timeStyle"/>
<link rel="stylesheet" href="${timeStyle}">



<!-- Multiple Select -->     
<spring:url value="/resources/Bootstrap/js/multipleselect/select2.full.min.js" var="MultipleJS"/>
<spring:url value="/resources/Bootstrap/js/multipleselect/en.js" var="MultipleJS2"/>
<script src="${MultipleJS}"></script>
<script src="${MultipleJS2}"></script>
<spring:url value="/resources/Bootstrap/css/multipleselect/select2.min.css" var="MultipleCSS"/>
<link rel="stylesheet" href="${MultipleCSS}">


</head>



<body>


  <div class="main-wrapper">
            <div class="app" id="app">
            <tiles:insertAttribute name="header" />
			<tiles:insertAttribute name="sidebar" />
			<tiles:insertAttribute name="body" />
			
            </div>
  </div>

<script>
                    $(document).ready(function(){
                    	$(".js-example-basic-multiple").select2();
                    	var date_input=$('input[name="date"]');
                    	var time_input = $('input[name="time"]');
                        var options={
                          format: 'yyyy/mm/dd',
                          todayHighlight: true,
                          autoclose: true,
                        };
                        var options2 = { now: "08:00", //hh:mm 24 hour format only, defaults to current time
                        				 twentyFour: true, //Display 24 hour format, defaults to false 
                        				 upArrow: 'wickedpicker__controls__control-up', //The up arrow class selector to use, for custom CSS 
                        				 downArrow: 'wickedpicker__controls__control-down', //The down arrow class selector to use, for custom CSS 
                        				 close: 'wickedpicker__close', //The close class selector to use, for custom CSS 
                        				 hoverState: 'hover-state', //The hover state class to use, for custom CSS 
                        				 title: 'Pick a time', //The Wickedpicker's title, 
                        				 showSeconds: false, //Whether or not to show seconds, 
                        				 secondsInterval: 1, //Change interval for seconds, defaults to 1  , 
                        				 minutesInterval: 1, //Change interval for minutes, defaults to 1 
                        				 beforeShow: null, //A function to be called before the Wickedpicker is shown 
                        				 show: null, //A function to be called when the Wickedpicker is shown 
                        				 clearable: false, //Make the picker's input clearable (has clickable "x")  
                        };
                        time_input.wickedpicker(options2);
                        date_input.datepicker(options);
                        
                        
                        
                        
                        
                        
                        
                    });
</script>
</body>
</html>