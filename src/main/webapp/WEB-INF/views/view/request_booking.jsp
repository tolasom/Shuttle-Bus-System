<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>KIT Admin</title>
  <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
      
  <!-- CSS  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
  <spring:url value="/resources/css/custom_booking.css" var="custom_booking" />
  <link href="${custom_booking}" rel="stylesheet"/>
  
  
  <!--  Scripts  -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script> 
  <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
  <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
  <spring:url value="/resources/js/request_booking.js" var="request_bookingjs"/>	
  <script src="${request_bookingjs}" type="text/javascript"></script>

  <style type="text/css">
	.input-field {
	    margin-top: 0rem;
	}
	h5 {
	    font-size: 1.64rem;
	    line-height: 110%;
	    margin: 2.5rem 0 1.5rem 0;
	}
	#book_now{
	    width: 100%;
   		height: 100%;
	}
	@media only screen and (min-width: 993px){
	.container {
		    width: 60%;
		}
	}
	nav .brand-logo {
    	left: 15%;
    }	
    .btn , .btn:hover, .btn-large:hover, .btn:focus, .btn-large:focus, .btn-floating:focus{
	    background-color: #ee6e73;
	}
	
	@media screen and (min-width: 401px) {
		.booking_history1{
				display:none;
			}
		
	}
	@media screen and (max-width: 400px) {
		.booking_history{
			display:none;
		}
	}
  </style>
</head>
<body>
  
<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
  <li class="booking_history1"><a href="booking_history">History</a></li>
  <li class="custom_booking"><a href="request_booking">Request</a></li>
  <li><a href="#!">Profile</a></li>
  <li><a href="#!">Logout</a></li>
</ul>
<nav>
  <div class="nav-wrapper">
    <a href="signup" class="brand-logo">Logo</a>
      <ul class="right">
      <li><a class="dropdown-button" href="#!" data-activates="dropdown1"><span id="fullname"></span><i class="material-icons right">arrow_drop_down</i></a></li>
    </ul>
  </div>
</nav>
<form class="col s12">
	<div class="row container">
		<h5 class="center">Bus Booking Request</h5>
		<div class="input-field col s12 m6">
		    <select id="source_name"></select>
		    <label>Source</label>
		  </div>
		    <div class="input-field col s12 m6">
		    <select id="destination_name">
		    </select>
		    <label>Destination</label>
		  </div>
		  <div class="input-field col s12 m6">
		    	<div class="input-field s6 flatpickr">
					<input type="text" placeholder="Select Date" id="departure_time" data-input class="input flatpickr-input active"> 					
				</div>
		  </div>
		    <div class="input-field col s12 m6">
		    	<div class="input-field s6 flatpickr">
					<input type="text" placeholder="Select Date" id="departure_date" data-input class="input flatpickr-input active"> 					
				</div>
		  </div>
		   <div class="input-field col s12">
		       <input id="number_of_seat" type="text" class="validate">
		       <label for="last_name">Number of Ticket</label>
		  </div> 
		  <div class="input-field col s12">
		    <button id="book_now" class="btn" type="submit" name="action">Request Now</button>
		  </div>
	</div>
	</form>
  </body>
</html>