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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
  <spring:url value="/resources/css/custom_booking.css" var="custom_booking" />
  <link href="${custom_booking}" rel="stylesheet"/>
  
  
  <!--  Scripts  -->
  <script src="https://momentjs.com/downloads/moment.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
  <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
  <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
  <spring:url value="/resources/js/customer_booking.js" var="customer_bookingjs"/>	
  <script src="${customer_bookingjs}" type="text/javascript"></script>

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
	
	.custom_link{
		font-size: 9px;
		color:#64B5F6;
	}
	#departure_date{
		    margin: 0 0 2px 0!important;
	}
	.modal-content .input-field .dropdown-content{
	    max-height: 180px!important;
	}
	#source_loc_id-error,#new_source_pickup_name-error,#new_dropoff_name-error,#select_dest_id-error
	,#source_name-error,#destination_name-error,#departure_time-error,#departure_date-error,#number_of_seat-error{
		color:red;
	}
	#source_name-error,#destination_name-error,#departure_time-error{
		color:red;
		    margin-top: -30px;
	}
	.select-wrapper span.caret {
	    margin: 15px 0!important;
    }
    .modal-content .input-field .select-wrapper input.select-dropdown {
	    margin: 0!important;
	}
    .confirm_success{
    	color:white;
    	background-color: green;
    }
    .confirm_error{
    	color:white;
    	background-color: red;
    }
    #confirm{
    	overflow-y: visible;
    	 width: 50%;
    }
	#confirm_booking_request,#cancel_confirm_model{
		width: 300px;
	}
	.content_confirm{
		padding: 0px!important;
	}
	.confirm_booking_request_model{
		width:100%
	}
	.cancel_booking_modal{
		width:100%;
	}
	.select-wrapper input.select-dropdown{
		-webkit-user-select:none;
		-moz-user-select:none;
		-ms-user-select:none;
		-o-user-select:none; // latest Opera versions support -webkit-
		user-select:none;
		}
  </style>
</head>
<body>
  
<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
  <li class="booking_history1 hide-on-med-and-up"><a href="booking_history">History</a></li>
  <li><a href="#!">Profile</a></li>
  <li><a onclick="formSubmit()">Logout</a></li>
</ul>
<nav>
  <div class="nav-wrapper">
    <a href="customer_home" class="brand-logo">Logo</a>
      <ul class="right">
      <li><a class="dropdown-button" href="#!" data-activates="dropdown1"><span id="fullname"></span><i class="material-icons right">arrow_drop_down</i></a></li>
    </ul>
  </div>
</nav>
<form id="form_book_now" class="col s12">
	<div class="row container">
		<h5 class="center">Shuttle Bus Booking</h5>
		<div class="input-field col s12 m6">
		    <select id="source_name" name="source_name" required></select>
		  </div>
		    <div class="input-field col s12 m6">
		    <select id="destination_name" name="destination_name" required>
		    <option disabled selected>Destination Location</option>
		    </select>
		  </div>
		  <div class="input-field col s12 m6">
		    <select id="departure_time" name="departure_time" required>
		    </select>
		  </div>
		    <div class="input-field col s12 m6">
		    	<div class="input-field s6 flatpickr">
					<input type="text" placeholder="Select Departure Date" id="departure_date" name="departure_date" data-input class="input flatpickr-input active" required> 					
				</div>
				<a id="custom_location" class="custom_link right" href="request_booking"><span style="color:red;">*Cannot find date or time you want?</span></a>
		  </div>
		   <div class="input-field col s12">
		       <input id="number_of_seat" name="number_of_seat" type="text" class="validate" required> 
		       <label for="last_name">Number of Ticket</label>
		  </div> 
		  <div class="input-field col s12">
		    <button id="book_now" class="btn" type="submit" name="action">Book Now</button>
		  </div>  
	</div>
</form>

<div id="get_booking_request" class="row container"></div> 
<div id="all_booking_history" class="row container hide-on-small-only"></div> 
<!-- Ask for confirm booking request -->
<div id="cancel_confirm_model" class="modal">
    <div class="modal-content container center">
	    <h5 class="center">Confirm</h5>
		<p> Do you want to cancel your shuttle bus booking now?
	</div>
	<div class="modal-footer">
		<!-- <span id="get_req_book_footer"></span> -->
		<span id="get_cancel_booking_footer"></span>
	    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
	 </div>
</div>

<!-- Confirm Modal -->
<div id="confirm" class="modal">
    <div class="modal-content center content_confirm">
      <p id="confirm_text"></p>
    </div>
</div>
<!-- Ask for confirm booking request -->
<div id="confirm_booking_request" class="modal">
    <div class="modal-content container center">
	    <h5 class="center">Confirm</h5>
		<p> Do you want to book ticket now?
	</div>
	<div class="modal-footer">
		<span id="get_req_book_footer"></span>
	    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
	 </div>
</div>
<!-- Ask for phone number while booking -->
<div id="confirm_phone_number_modal" class="modal">
	<div class="modal-content container">
	   <h5 class="center">Please tell us your phone number in Cambodia!</h5>
	   <div class="input-field col s6">
	       <input placeholder="Enter your phone number for ctacting between you and driver!" id="user_confirm_phone_number" name="user_confirm_phone_number" type="text" class="validate">
	   </div>
	</div>
	<div class="modal-footer">
	    <a id="confirm_phone_number_btn" class="modal-action modal-close waves-effect waves-green btn-flat">Confirm</a>
	    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Skip</a>
	</div>
</div>
<!-- Custom Source Pick Up Location -->
<div id="coustom_source_pl" class="modal">
	<form id="form_coustom_source_pl">
	    <div class="modal-content container">
	      <h5 class="center">Add Your Custom Pick Up location</h5>
	      <div class="input-field col s12 m6">
			    <select id="source_loc_id" name="source_loc_id" required></select>
		  </div>
		  <div class="input-field col s6">
	          <input placeholder="Where you want us to pick up?" id="new_source_pickup_name" name="new_source_pickup_name" type="text" class="validate">
	        </div>
	    </div>
	    <div class="modal-footer">
	    	<button type="submit" class="modal-action waves-effect waves-green btn-flat">Confirm</button>
	      	<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
	    </div>
	</form>
</div>
<!-- Custom Drop-Off Location -->
<div id="coustom_dropoff" class="modal">
	<form id="form_coustom_dropoff">
	    <div class="modal-content container">
	      <h5 class="center">Add Your Custom drop-off location</h5>
	      <div class="input-field col s12 m6">
			    <select id="select_dest_id" name="select_dest_id" required></select>
		  </div>
		  <div class="input-field col s6">
	          <input placeholder="Where you want us to drop-off?" id="new_dropoff_name" name="new_dropoff_name" type="text" class="validate">
	        </div>
	    </div>
	    <div class="modal-footer">
	    	<button type="submit" class="modal-action waves-effect waves-green btn-flat">Confirm</button>
	      	<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
	    </div>
	</form>
</div>

	<form action="logout" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
  </body>
  
  <script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
</script>
</html>