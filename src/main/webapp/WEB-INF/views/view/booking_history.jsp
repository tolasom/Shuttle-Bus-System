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
 
  
  
  <!--  Scripts  -->
  <script src="https://momentjs.com/downloads/moment.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script> 
  <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
  <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>


  <style type="text/css">

	h5 {
	    line-height: 110%;
	    margin: 2rem 0 1.5rem 0;
	}

	.container {
		    width: 80%;
		}
	nav .brand-logo {
    	left: 15%;
    }	
    /*
    
	
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
	
	.collapsible-header {
	    display: block;
	    cursor: pointer;
	    min-height: 3rem;
	    line-height: 3rem;
	    padding: 0 1rem;
	    background-color: #fff;
	    border-bottom: 1px solid #ddd;
	}
	#list_booking_history{
	    margin: 0rem 0 1rem 0 !important;
   		box-shadow: none;
	}*/
	.cancel_booking_modal{
		width:100%;
	}
	.btn , .btn:hover, .btn-large:hover, .btn:focus, .btn-large:focus, .btn-floating:focus{
	    background-color: #ee6e73;
	}
	#cancel_confirm_model{
		width: 300px;
	}
	
  </style>
</head>
<body>
  
<!-- Dropdown Structure -->
<ul id="dropdown1" class="dropdown-content">
  <li class="booking_history1"><a href="booking_history">History</a></li>
  <li><a href="#!">Profile</a></li>
  <li><a href="#!">Logout</a></li>
</ul>
<nav>
  <div class="nav-wrapper">
    <a href="customer_home" class="brand-logo">Logo</a>
      <ul class="right">
      <li><a class="dropdown-button" href="#!" data-activates="dropdown1"><span id="fullname"></span><i class="material-icons right">arrow_drop_down</i></a></li>
    </ul>
  </div>
</nav>
<div class="container">
<h5 class="center">Booking History</h5>
 <ul id="list_booking_history" class="collapsible" data-collapsible="accordion"></ul>
</div>
<!-- Confirm Modal -->
<div id="confirm" class="modal">
    <div class="modal-content center content_confirm">
      <p id="confirm_text"></p>
    </div>
</div>
<!-- Ask for confirm booking request -->
<div id="cancel_confirm_model" class="modal">
    <div class="modal-content container center">
	    <h5 class="center">Confirm</h5>
		<p> Do you want to cancel your shuttle bus booking now?
	</div>
	<div class="modal-footer">
		<span id="get_req_book_footer"></span>
	    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancel</a>
	 </div>
</div>
<script>
  $(document).ready(function() {
	  // ======== User Information ==========
		 $.ajax({
				async: false,
				cache: false,
				type: "GET",
				url: "user_info",
				contentType: "application/json",
				timeout: 100000,
				success: function(data) {
					document.getElementById('fullname').innerHTML=data.username;
				},
				error: function(e) {
					console.log("ERROR: ", e);
				},
				done: function(e) {
					console.log("DONE");
				}
		});
	
	  
	// ======== History Booking ==========
		 $.ajax({
				async: false,
				cache: false,
				type: "GET",
				url: "customer_booking_history",
				contentType: "application/json",
				timeout: 100000,
				success: function(data) {
					console.log("departure_time_info");
					console.log(data);
					
					var bh_form='';
					if(data.length>0){
						for(i=0;i<data.length;i++){
							var dept_date_time=convert_date(data[i].dept_date)+' '+data[i].dept_time;
							console.log("dept_date_time: "+dept_date_time)
							if(data[i].notification=="Cancelled"){
								bh_form+='<li><div class="collapsible-header"><i class="material-icons">cancel</i>&nbsp&nbsp'
									+ convert_date(data[i].dept_date)+';  '+convert_time(data[i].dept_time)+'</div>'
							        +'<div class="collapsible-body">'
							        +'<table><tr><th>Departure Date</th><td><b>:</b>&nbsp&nbsp'+convert_date(data[i].dept_date)+' </td></tr>'
							        +'<tr><th>Departure Time</th><td><b>:</b>&nbsp&nbsp'+convert_time(data[i].dept_time)+'</td></tr>'
							        +'<tr><th>Source</th><td><b>:</b>&nbsp&nbsp'+data[i].scource+'</td></tr>'
							        +'<tr><th>Pickup Location</th><td><b>:</b>&nbsp&nbsp'+data[i].pick_up+'</td></tr>'
							        +'<tr><th>Destination</th><td><b>:</b>&nbsp&nbsp'+data[i].destination +'</td></tr>'
							        +'<tr><th>Drop-off Location</th><td><b>:</b>&nbsp&nbsp'+data[i].drop_off+'</td></tr>'
							        +'<tr><th>Number of Ticket</th><td><b>:</b>&nbsp&nbsp'+data[i].number_of_ticket +'</td></tr>'
							        +'<tr><th>Bus Model</th><td><b>:</b>&nbsp&nbsp'+data[i].bus_model +'</td></tr>'
							        +'<tr><th>Plate Number</th><td><b>:</b>&nbsp&nbsp'+data[i].plate_number +'</td></tr>'
							        if(data[i].diver_name=="no_driver"){
									    bh_form+='<tr><th>Driver\'s Name</th><td><b>:</b>&nbsp&nbsp To be decided</td></tr>'
									 }else{
									    bh_form+='<tr><th>Driver\'s Name</th><td><b>:</b>&nbsp&nbsp'+ data[i].diver_name+'</td></tr>'
									    		+'<tr><th>Driver\'s Phone Number</th><td><b>:</b>&nbsp&nbsp'+data[i].diver_phone_number +'</td></tr>'
									 } 
							}else{
								if(compared_tomorrow(dept_date_time)){
									bh_form+='<li><div class="collapsible-header"><i class="material-icons">schedule</i>&nbsp&nbsp'
										+ convert_date(data[i].dept_date)+';  '+convert_time(data[i].dept_time)+'</div>'
								        +'<div class="collapsible-body">'
								        +'<table><tr><th>Departure Date</th><td><b>:</b>&nbsp&nbsp'+convert_date(data[i].dept_date)+' </td></tr>'
								        +'<tr><th>Departure Time</th><td><b>:</b>&nbsp&nbsp'+convert_time(data[i].dept_time)+'</td></tr>'
								        +'<tr><th>Source</th><td><b>:</b>&nbsp&nbsp'+data[i].scource+'</td></tr>'
								        +'<tr><th>Pickup Location</th><td><b>:</b>&nbsp&nbsp'+data[i].pick_up+'</td></tr>'
								        +'<tr><th>Destination</th><td><b>:</b>&nbsp&nbsp'+data[i].destination +'</td></tr>'
								        +'<tr><th>Drop-off Location</th><td><b>:</b>&nbsp&nbsp'+data[i].drop_off+'</td></tr>'
								        +'<tr><th>Number of Ticket</th><td><b>:</b>&nbsp&nbsp'+data[i].number_of_ticket +'</td></tr>'
								        +'<tr><th>Bus Model</th><td><b>:</b>&nbsp&nbsp'+data[i].bus_model +'</td></tr>'
								        +'<tr><th>Plate Number</th><td><b>:</b>&nbsp&nbsp'+data[i].plate_number +'</td></tr>'
								        if(data[i].diver_name=="no_driver"){
										    bh_form+='<tr><th>Driver\'s Name</th><td><b>:</b>&nbsp&nbsp To be decided</td></tr>'
										 }else{
										    bh_form+='<tr><th>Driver\'s Name</th><td><b>:</b>&nbsp&nbsp'+ data[i].diver_name+'</td></tr>'
										    		+'<tr><th>Driver\'s Phone Number</th><td><b>:</b>&nbsp&nbsp'+data[i].diver_phone_number +'</td></tr>'
										 } 
									bh_form+='<tr><th colspan="2"><a href="#!" class="btn cancel_booking_modal" booking="'+data[i].id +'">Cancel</a></th></tr>'
								}else{
									bh_form+='<li><div class="collapsible-header"><i class="material-icons">history</i>&nbsp&nbsp'
										+ convert_date(data[i].dept_date)+';  '+convert_time(data[i].dept_time)+'</div>'
								        +'<div class="collapsible-body">'
								        +'<table><tr><th>Departure Date</th><td><b>:</b>&nbsp&nbsp'+convert_date(data[i].dept_date)+' </td></tr>'
								        +'<tr><th>Departure Time</th><td><b>:</b>&nbsp&nbsp'+convert_time(data[i].dept_time)+'</td></tr>'
								        +'<tr><th>Source</th><td><b>:</b>&nbsp&nbsp'+data[i].scource+'</td></tr>'
								        +'<tr><th>Pickup Location</th><td><b>:</b>&nbsp&nbsp'+data[i].pick_up+'</td></tr>'
								        +'<tr><th>Destination</th><td><b>:</b>&nbsp&nbsp'+data[i].destination +'</td></tr>'
								        +'<tr><th>Drop-off Location</th><td><b>:</b>&nbsp&nbsp'+data[i].drop_off+'</td></tr>'
								        +'<tr><th>Number of Ticket</th><td><b>:</b>&nbsp&nbsp'+data[i].number_of_ticket +'</td></tr>'
								        +'<tr><th>Bus Model</th><td><b>:</b>&nbsp&nbsp'+data[i].bus_model +'</td></tr>'
								        +'<tr><th>Plate Number</th><td><b>:</b>&nbsp&nbsp'+data[i].plate_number +'</td></tr>'
								        if(data[i].diver_name=="no_driver"){
										    bh_form+='<tr><th>Driver\'s Name</th><td><b>:</b>&nbsp&nbsp To be decided</td></tr>'
										 }else{
										    bh_form+='<tr><th>Driver\'s Name</th><td><b>:</b>&nbsp&nbsp'+ data[i].diver_name+'</td></tr>'
										    		+'<tr><th>Driver\'s Phone Number</th><td><b>:</b>&nbsp&nbsp'+data[i].diver_phone_number +'</td></tr>'
										 }  
								}
							}
							
						   
							       
							        
							bh_form+='</table></div></li>';
						}
					}
					document.getElementById('list_booking_history').innerHTML=bh_form;
				},
				error: function(e) {
					console.log("ERROR: ", e);
				},
				done: function(e) {
					console.log("DONE");
				}
		});
	
		//Cancel Booking
		$('.cancel_booking_modal').click(function(){
				var id=$(this).attr("booking") ;
				console.log(id);
				$('#cancel_confirm_model').modal();
				$('#cancel_confirm_model').modal('open');
				var form='<button id="confirm_cancel_booking_btn" booking="'+id
							+'" class="modal-action waves-effect waves-green btn-flat">Confirm</button>';
				document.getElementById('get_req_book_footer').innerHTML=form;
				$('#confirm_cancel_booking_btn').click(function(){
					var id1=$(this).attr("booking") ;
					console.log(id1);
					$.ajax({
						async: false,
						cache: false,
						type: "GET",
						url: "cancel_booking_ticket",
						data :{'id':id1},
						timeout: 100000,
						success: function(data) {
							
							var form=''
							if(data=="success"){
								form+="You have cancelled";
								$("#confirm" ).addClass( "confirm_success" );
							}else if(data=="no_record"){
								form+="Sorry, there is no schedule";
								$("#confirm" ).addClass( "confirm_error" );
							}else{
								form+="Sorry, there is internal problem";
								$("#confirm" ).addClass( "confirm_error" );
							}
							console.log(data);
							
							document.getElementById('confirm_text').innerHTML=form;
							$('#cancel_confirm_model').modal('close');
							$('#confirm').modal({
							    complete: function() {
										window.location.replace("booking_history");
								} 
							});
							$('#confirm').modal('open');
							
						},
						error: function(e) {
							console.log("ERROR: ", e);
						},
						done: function(e) {
							console.log("DONE");
						}
				});

				})
			})
	
	
		 function compared_tomorrow(dept_date_time){
			 var today = moment().format('Y-M-D, HH:mm:ss');
			 var date = new Date(today)
			 date.setDate(date.getDate() + 1);
			  var tomorrow = date.getFullYear()+ "-" + (date.getMonth()+1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
			 console.log("tomorrow: "+tomorrow)
			 console.log('dept_date_time: '+dept_date_time)
			 if(new Date(dept_date_time) >new Date(tomorrow)){
	   			 return true;
	   		 }else{
	   			 return false;
	   		 }
		 }
	
		// ======== Convert Time 13:00:00 to 01:00 PM ==========
		 function convert_time(time_input){
			  console.log(time_input);
			  var h=time_input.slice(0, 2);
			  var m=time_input.slice(3, 5);
			  var format=null;
			  if(h>=12){
			    h-=12;
			    format='PM';
			    if(h<10){
			      h='0'+h;
			    }
			  }else{
			    format='AM';
			  }
			  return time=h+':'+m+' '+format;
			}
		// ======== Convert Time 2018-02-01 00:00:00 to 2018-02-01 ==========
		 function convert_date(date_input){
			  console.log(date_input);
			  return date_input.slice(0, 10);;
		}
		
		
	  });
  </script>
 
  </body>
</html>