$(document).ready(function() {
	 $('select').material_select();
	 $('#destination_name').material_select();
	 $("#source_loc_id[required]," +
	 		"#select_dest_id[required]," +
	 		"#destination_name[required]," +
	 		"#source_name[required]," +
	 		
	 		"#departure_time[required]").css({display: "inline", height: 0, padding: 0, width: 0});
	 		
	 $(".flatpickr input").flatpickr({
			mode: "single",
			minDate: new Date().fp_incr(1),
			dateFormat: "Y-m-d",
			disableMobile: "true"
	});
	 
	 // ======== User Information ==========
	 var phone;
	 $.ajax({
			async: false,
			cache: false,
			type: "GET",
			url: "user_info",
			contentType: "application/json",
			timeout: 100000,
			success: function(data) {
				phone=data.phone_number;
				document.getElementById('fullname').innerHTML=data.username;
				
			},
			error: function(e) {
				console.log("ERROR: ", e);
			},
			done: function(e) {
				console.log("DONE");
			}
	});
	// ======== Check Booking Request ==========
	 $.ajax({
			async: false,
			cache: false,
			type: "GET",
			url: "check_booking_request",
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
	// ======== Source Information ==========
	 $.ajax({
			async: false,
			cache: false,
			type: "GET",
			url: "location_data",
			contentType: "application/json",
			timeout: 100000,
			success: function(data) {
				console.log("location data:");
				console.log(data);
				var location= Object.keys( data.location );
				var l_form='<option disabled selected>Source Location</option>';
				var custom_pl_form='<option disabled selected>Choose your main source</option>';
				for(i=0;i<location.length;i++){
					console.log(location[i])
					custom_pl_form+='<option value="'+data.location[location[i]][0].location_id+'">'+location[i]+'</option>';
					l_form+='<optgroup label="'+location[i]+'">';
					for(j=0;j<data.location[location[i]].length;j++){
						console.log(data.location[location[i]][j]);
						l_form+='<option value="'+data.location[location[i]][j].id+'">'+data.location[location[i]][j].name+'   ('+location[i]+')</option>';
					}
					l_form+='</optgroup>';		
				}
				l_form+='<option value="custom_pickup">Custom Pick Up Location</option>'
				console.log(l_form)
				document.getElementById('source_name').innerHTML=l_form;
				document.getElementById('source_loc_id').innerHTML=custom_pl_form;
				$('#source_name').material_select();
			},
			error: function(e) {
				console.log("ERROR: ", e);
			},
			done: function(e) {
				console.log("DONE");
			}
	});
	 function selected_source(id){
		 $.ajax({
				async: false,
				cache: false,
				type: "GET",
				url: "check_location",
				data:{'id':id},
				contentType: "application/json",
				timeout: 100000,
				success: function(data) {
					var location= Object.keys( data.location );
					var l_form='<option disabled selected>Destination Location</option>';
					var custom_dropoff='<option disabled selected>Choose your main source</option>';
					for(i=0;i<location.length;i++){
						console.log(location[i])
						l_form+='<optgroup label="'+location[i]+'">';
						custom_dropoff+='<option value="'+data.location[location[i]][0].location_id+'">'+location[i]+'</option>';
						for(j=0;j<data.location[location[i]].length;j++){
							console.log(data.location[location[i]][j]);
							l_form+='<option value="'+data.location[location[i]][j].id+'">'+data.location[location[i]][j].name+'   ('+location[i]+')</option>';
						}
						l_form+='</optgroup>';		
					}
					l_form+='<option value="custom_dropoff">Custom Drop-off Location</option>'
					document.getElementById('destination_name').innerHTML=l_form;
					document.getElementById('select_dest_id').innerHTML=custom_dropoff;
					
					$('#select_dest_id').material_select();
					$('#destination_name').material_select();
				},
				error: function(e) {
					console.log("ERROR: ", e);
				},
				done: function(e) {
					console.log("DONE");
				}
		});
	 }
	 
	// ========Select Source Information ==========
	 $('#destination_name').change(function() {
			var id = $("#destination_name").val();
			console.log(id);
			if(id=="custom_dropoff"){
				$('#coustom_dropoff').modal();
				$('#coustom_dropoff').modal('open');
				$('#select_dest_id').material_select();
			}
	});
	 
	// ========Select Destination Information ==========
	 $('#source_name').change(function() {
			var id = $("#source_name").val();
			console.log(id);
			if(id=="custom_pickup"){
				$('#coustom_source_pl').modal();
				$('#coustom_source_pl').modal('open');
				$('#source_loc_id').material_select();
			}else{
				selected_source(id);
			}
	});
	 
	// ======== Departure time Information ==========
	 $.ajax({
			async: false,
			cache: false,
			type: "GET",
			url: "departure_time_info",
			contentType: "application/json",
			timeout: 100000,
			success: function(data) {
				console.log("departure_time_info");
				console.log(data);
				var option='<option value="" disabled selected>Select Departure Time</option>';
					for(i=0;i<data.length;i++){
						var get_time=convert_time(data[i].dept_time);
						option+='<option value="'+data[i].dept_time+'">'+get_time+'</option>';
					}
				document.getElementById('departure_time').innerHTML=option;
				$('#departure_time').material_select();
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
					bh_form+='<h5 class="center">Booking History</h5>'
						+'<ul class="collapsible" data-collapsible="accordion">';
					for(i=0;i<data.length;i++){
						var dept_date_time=convert_date(data[i].dept_date)+' '+data[i].dept_time;
						console.log("dept_date_time: "+dept_date_time)
						if(data[i].notification=="Cancelled"){
							bh_form+='<li><div class="collapsible-header"><i class="material-icons">cancel</i>&nbsp&nbsp'
								+ convert_date(data[i].dept_date)+';  '+convert_time(data[i].dept_time)
								+'<span class="right"><i class="material-icons">directions_bus</i>&nbsp&nbsp'+data[i].scource+' to '+data[i].destination +'</span></div>'
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
									+ convert_date(data[i].dept_date)+';  '+convert_time(data[i].dept_time)
									+'<span class="right"><i class="material-icons">directions_bus</i>&nbsp&nbsp'+data[i].scource+' to '+data[i].destination +'</span></div>'
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
									+ convert_date(data[i].dept_date)+';  '+convert_time(data[i].dept_time)
									+'<span class="right"><i class="material-icons">directions_bus</i>&nbsp&nbsp'+data[i].scource+' to '+data[i].destination +'</span></div>'
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
				bh_form+='</ul>'
				document.getElementById('all_booking_history').innerHTML=bh_form;
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
			document.getElementById('get_cancel_booking_footer').innerHTML=form;
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
									window.location.replace("customer_home");
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

	// ======== Request Booking Form==========
	 $.ajax({
			async: false,
			cache: false,
			type: "GET",
			url: "get_request_booking",
			contentType: "application/json",
			timeout: 100000,
			success: function(data) {
				console.log("get_request_booking");
				console.log(data);
				var mobile='<ul class="collapsible hide-on-med-and-up" data-collapsible="accordion">';
				var bh_form='';
				if(data.length>0){
					bh_form+='<h5 class="center">Booking Request</h5>'
							+'<table class="bordered centered hide-on-small-only"><thead><tr>'
							+'<th>No.</th>'
							+'<th>Departure Date & Time</th>'
							+'<th>Route</th>'
							+'<th>Number of Ticket</th>'
							+'<th>Status</th>'
							+'<th>Option</th>'
							+'</tr></thead><tbody>';
					
					for(i=0;i<data.length;i++){
						bh_form+='<tr><td>'+(i+1)+'</td>'
								 +'<td>'+convert_date(data[i].dept_date)+'; '+convert_time(data[i].dept_time)+'</td>'
								 +'<td>'+data[i].scource+' to '+data[i].destination +'</td>'
								 +'<td>'+data[i].number_of_ticket+'</td>'
								 +'<td>'+data[i].status+'</td>';
						if(data[i].status=='Confirmed'){
							bh_form+='<td><a href="#!" class="btn confirm_booking_request_model" request="'+data[i].id +'">Book Now</a></td>'
							
							mobile+='<li><div class="collapsible-header"><i class="material-icons">book</i>&nbsp&nbsp'+convert_date(data[i].dept_date)+'; '+ convert_time(data[i].dept_time)+'</div>'
								+'<div class="collapsible-body"><table>'
						        +'<tr><th>Departure Date</th><td><b>:</b>  &nbsp&nbsp '+convert_date(data[i].dept_date)+'</td></tr>'
						        +'<tr><th>Departure Time</th><td><b>:</b>  &nbsp&nbsp '+ convert_time(data[i].dept_time)+'</td></tr>'
						        +'<tr><th>Source</th><td><b>:</b> &nbsp&nbsp '+data[i].scource +'</td></tr>'
						        +'<tr><th>Destination</th><td><b>:</b> &nbsp&nbsp '+ data[i].destination +'</td></tr>'
						        +'<tr><th>Number of Ticket</th><td><b>:</b> &nbsp&nbsp '+data[i].number_of_ticket +'</td></tr>'
						        +'<tr><th>Status</th><td><b>:</b> &nbsp&nbsp '+data[i].status +'</td></tr>'
						        +'<tr><th colspan="2"><a href="#!" class="btn confirm_booking_request_model" request="'+data[i].id +'">Book Now</a></th></tr>'
								+'</table></div></li>'
							
						}else{
							bh_form+='<td></td>';
							
							mobile+='<li><div class="collapsible-header"><i class="material-icons">book</i>&nbsp&nbsp'+convert_date(data[i].dept_date)+'; '+ convert_time(data[i].dept_time)+'</div>'
								+'<div class="collapsible-body"><table>'
						        +'<tr><th>Departure Date</th><td><b>:</b>  &nbsp&nbsp '+convert_date(data[i].dept_date)+'</td></tr>'
						        +'<tr><th>Departure Time</th><td><b>:</b>  &nbsp&nbsp '+ convert_time(data[i].dept_time)+'</td></tr>'
						        +'<tr><th>Source</th><td><b>:</b> &nbsp&nbsp '+data[i].scource +'</td></tr>'
						        +'<tr><th>Destination</th><td><b>:</b> &nbsp&nbsp '+ data[i].destination +'</td></tr>'
						        +'<tr><th>Number of Ticket</th><td><b>:</b> &nbsp&nbsp '+data[i].number_of_ticket +'</td></tr>'
						        +'<tr><th>Status</th><td><b>:</b> &nbsp&nbsp '+data[i].status +'</td></tr>'
								+'</table></div></li>'
						}
   
					}
					bh_form+='</tbody></table>';
					mobile+='</ul>';
					bh_form+=mobile;
				}
				document.getElementById('get_booking_request').innerHTML=bh_form;
				$('.collapsible').collapsible();
			},
			error: function(e) {
				console.log("ERROR: ", e);
			},
			done: function(e) {
				console.log("DONE");
			}
	});
	 
	 // confirm booking request
	$('.confirm_booking_request_model').click(function(){
		var id=$(this).attr("request") ;
		console.log(id);
		$('#confirm_booking_request').modal();
		$('#confirm_booking_request').modal('open');
		var form='<button id="confirm_booking_request_btn" request="'+id
					+'" class="modal-action waves-effect waves-green btn-flat">Confirm</button>';
		document.getElementById('get_req_book_footer').innerHTML=form;
		$('#confirm_booking_request_btn').click(function(){
			var id1=$(this).attr("request") ;
			console.log(id1);
			$.ajax({
				async: false,
				cache: false,
				type: "GET",
				url: "request_book_now",
				data :{'id':id1},
				timeout: 100000,
				success: function(data) {
					
					var form=''
					if(data=="success"){
						form+="You booking have done.";
						$("#confirm" ).addClass( "confirm_success" );
					}else if(data=="no_bus_available"){
						form+="Sorry, No Bus Available on that day.";
						$("#confirm" ).addClass( "confirm_error" );
					}else if(data=="over_bus_available"){
						form+="Sorry, No more ticket available.";
						$("#confirm" ).addClass( "confirm_error" );
					}else{
						form+="Sorry, there is internal problem";
						$("#confirm" ).addClass( "confirm_error" );
					}
					console.log(data);
					
					document.getElementById('confirm_text').innerHTML=form;
					$('#confirm_booking_request').modal('close');
					$('#confirm').modal({
					    complete: function() {
								window.location.replace("customer_home");
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
	 
	 function compared_tomorrow(dept_date_time){
		 var current_date;
		 $.ajax({
				async: false,
				cache: false,
				type: "GET",
				url: "today",
				timeout: 100000,
				success: function(data) {
					current_date=data.slice(0, 19);
				},
				error: function(e) {
					console.log("ERROR: ", e);
				},
				done: function(e) {
					console.log("DONE");
				}
			});
		 var date = new Date(moment.utc(current_date, "YYYY-MM-DD  HH:mm:ss"));
		 date.setDate(date.getDate() + 1);
		 var tomorrow = String(date.getFullYear()+ "-" + (date.getMonth()+1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
		 var tmr = moment.utc(tomorrow, "YYYY-MM-DD  HH:mm:ss");
		 var dept_dt = moment.utc(dept_date_time, "YYYY-MM-DD  HH:mm:ss");
		 
		 console.log(tmr);
		 console.log(dept_dt);
 		 console.log(moment(dept_dt).isAfter(tmr));
		 if(moment(dept_dt).isAfter(tmr)){
   			 return true;
   		 }else{
   			 return false;
   		 }
	 }
	 
	 
//============================== Validation Part ===========================//
	 $.validator.addMethod("valueNotEquals", function(value, element, arg){
		 console.log(value);
		  return arg !== value;
		 }, "Value must not equal arg.");
	 $.validator.addMethod("SourceValueNothing", function(value, element, arg){
		  return arg !== value;
		 }, "Source location is required");
	 $.validator.addMethod("DestinationValueNothing", function(value, element, arg){
		  return arg !== value;
		 }, "Destination location is required");

	//============================== Custom Source Pickup Location ===========================
	 $("#form_book_now").validate({
	        rules: {
	        	source_name: {
	        		 valueNotEquals: "none",
	        		 SourceValueNothing: "custom_pickup"
	            },
	            destination_name: {
	        		 valueNotEquals: "none",
	        		 DestinationValueNothing: "custom_pickup",
	            },
	            departure_time: {
	        		 valueNotEquals: "none" 
	            },
	            departure_date: {
	        		 valueNotEquals: "none" 
	            },
	            number_of_seat: {
			         required: true,
			         min: 1,
			         max: 10
			     }

	        },
	        messages: {
	          
	            source_name: {
	            	required: "Source location is required"
	            },
	            destination_name: {
	            	required: "Destination location is required" 
	            },
	            departure_time: {
	            	required: "Departure time is required" 
	            },
	            departure_date: {
	            	required: "Departure date is required" 
	            },
	            number_of_seat: {
			         required: "Number of ticket is required",
			         min: "Manimun 1 tickets each time booking",
			         max: "Maximun 10 tickets each time booking"
			     }

	        },
	        errorElement: 'span',
	        errorPlacement: function(error, element) {
	        	
	            var placement = $(element).data('error');
	            if (placement) {
	                $(placement).append(error)
	                console.log("correct");
	            } else {
	                error.insertAfter(element);
	                console.log("incorrect");
	            }
	        },
	        submitHandler: function() {
	        	console.log("ALL Correct")
	        	
		   		 var source = $("#source_name").val();
		   		 var destination = $("#destination_name").val();
		   		 var time=$("#departure_time").val();
		   		 var date=$("#departure_date").val();
		   		 var number_of_seat=$("#number_of_seat").val();
		   		 var dept_dt=date+" "+time;
		   		console.log("dept_dt: "+dept_dt);
		   		console.log("destination: "+destination);
		   		 if(compared_tomorrow(dept_dt)){
		   			if(phone=='0'){
		   				$('#confirm_phone_number_modal').modal({
						    complete: function() {
						    	$.ajax({
					   				async: false,
					   				cache: false,
					   				type: "GET",
					   				url: "customer_booking",
					   				data :	{
					   						 source:source,
					   						 destination:destination,
					   						 time:time,
					   						 date:date,
					   						 number_of_seat:number_of_seat
					   				},
					   				timeout: 100000,
					   				success: function(data) {
					   					console.log(data);
					   					var text;
					   					if(data=="success"){
					   						text="You are sucessful booking";
					   					}else if(data=="no_bus_available"){
					   						text="No Bus is available";
					   					}else if(data=="over_bus_available"){
					   						text="Over bus available";
					   					}else{
					   						text="Process is error!!";
					   					}
					   					document.getElementById('confirm_text').innerHTML=text;
					   					$('#confirm').modal({
										    complete: function() {
													window.location.replace("customer_home");
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
							} 
						});
		   				$('#confirm_phone_number_modal').modal('open');
		   				$('#confirm_phone_number_btn').click(function(){
		   					var phone=$('#user_confirm_phone_number').val();
		   					console.log('phone: '+phone)
		   					$.ajax({
				   				async: false,
				   				cache: false,
				   				type: "GET",
				   				url: "confirm_phone_number",
				   				data :	{phone:phone},
				   				timeout: 100000,
				   				success: function(data) {
				   					console.log(data);
				   				},
				   				error: function(e) {
				   					console.log("ERROR: ", e);
				   				},
				   				done: function(e) {
				   					console.log("DONE");
				   				}
					    	});
		   				})
		        	}else{
		        		$.ajax({
			   				async: false,
			   				cache: false,
			   				type: "GET",
			   				url: "customer_booking",
			   				data :	{
			   						 source:source,
			   						 destination:destination,
			   						 time:time,
			   						 date:date,
			   						 number_of_seat:number_of_seat
			   				},
			   				timeout: 100000,
			   				success: function(data) {
			   					console.log(data);
			   					var text;
			   					if(data=="success"){
			   						text="You are sucessful booking";
			   					}else if(data=="no_bus_available"){
			   						text="No Bus is available";
			   					}else if(data=="over_bus_available"){
			   						text="Over bus available";
			   					}else{
			   						text="Process is error!!";
			   					}
			   					document.getElementById('confirm_text').innerHTML=text;
			   					$('#confirm').modal({
								    complete: function() {
											window.location.replace("customer_home");
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
		        	}
			   		 
		   		 }else{
		   			var form="Please book ticket before departure time 24 hours otherwise please click <a href=\"request_booking\">Here</a> to request booking ";
					$("#confirm" ).addClass( "confirm_error" );
						
						document.getElementById('confirm_text').innerHTML=form;
						$('#confirm').modal({
						    complete: function() {
									window.location.replace("customer_home");
							} 
						});
						$('#confirm').modal('open');
		   		 }

	            return false;
	        }
	    });
	 
	//============================== Custom Source Pickup Location ===========================
	 $("#form_coustom_source_pl").validate({
	        rules: {
	        	source_loc_id: {
	        		 valueNotEquals: "none" 
	            },
	            new_source_pickup_name: {
			         required: true,
			         minlength: 1,
			         maxlength: 30
			     }

	        },
	        messages: {
	        	source_loc_id: {
	                required: "Main location is required"
	            },
	            new_source_pickup_name: {
			         required: "Pick up location is required",
			         minlength: "Minimum one digit",
			         maxlength: "Maximum 30 digits"
			     }


	        },
	        errorElement: 'div',
	        errorPlacement: function(error, element) {
	            var placement = $(element).data('error');
	            if (placement) {
	                $(placement).append(error)
	                console.log("correct");
	            } else {
	                error.insertAfter(element);
	                console.log("incorrect");
	            }
	        },
	        submitHandler: function() {
	        	console.log($('#source_loc_id').val());
	        	console.log($('#new_source_pickup_name').val())
	            console.log("Successful Done!!!")
	             var location_id = $("#source_loc_id").val();
				 var pickup_name = $("#new_source_pickup_name").val();
				 $.ajax({
						async: false,
						cache: false,
						type: "GET",
						url: "create_custom_pickup_location",
						data :	{
							location_id:location_id,
							pickup_name:pickup_name
						},
						timeout: 100000,
						success: function(data) {
							console.log("Custom Pick UP Done!");
							console.log(data);
							var location= Object.keys( data.location );
							console.log(data.location[location[1]][0].location_id);
							var l_form='';
							var id; //  pickup location id
							for(i=0;i<location.length;i++){
								l_form+='<optgroup label="'+location[i]+'">';
								for(j=data.location[location[i]].length-1;j>=0;j--){
									if(location_id==data.location[location[i]][0].location_id&&j==data.location[location[i]].length-1){
										l_form+='<option selected value="'+data.location[location[i]][j].id+'">'+data.location[location[i]][j].name+'   ('+location[i]+')</option>';
										id=data.location[location[i]][j].id;
										console.log("Happy")
									}else{
										l_form+='<option value="'+data.location[location[i]][j].id+'">'+data.location[location[i]][j].name+'   ('+location[i]+')</option>';
									}
									
								}
								l_form+='</optgroup>';		
							}
							document.getElementById('source_name').innerHTML=l_form;
							$('#source_name').material_select();
							selected_source(id);// to retrieve destination
							$('#coustom_source_pl').modal('close');
						},
						error: function(e) {
							console.log("ERROR: ", e);
						},
						done: function(e) {
							console.log("DONE");
						}
				});
	            return false;
	        }
	    });
	//============================== Custom Drop-Off Location ===========================
	 $("#form_coustom_dropoff").validate({
	        rules: {
	        	select_dest_id: {
	        		 valueNotEquals: "none" 
	            },
	            new_dropoff_name: {
			         required: true,
			         minlength: 1,
			         maxlength: 30
			     }

	        },
	        messages: {
	        	select_dest_id: {
	                required: "Main location is required"
	            },
	            new_dropoff_name: {
			         required: "Pick up location is required",
			         minlength: "Minimum one digit",
			         maxlength: "Maximum 30 digits"
			     }


	        },
	        errorElement: 'div',
	        errorPlacement: function(error, element) {
	            var placement = $(element).data('error');
	            if (placement) {
	                $(placement).append(error)
	                console.log("correct");
	            } else {
	                error.insertAfter(element);
	                console.log("incorrect");
	            }
	        },
	        submitHandler: function() {
	        	console.log($('#select_dest_id').val());
	        	console.log($('#new_dropoff_name').val())
	            console.log("Successful Done!!!")
	             var location_id = $("#select_dest_id").val();
				 var drop_name = $("#new_dropoff_name").val();
				 new_custom_dropoff(location_id, drop_name);
	            return false;
	        }
	    });
	 
	 function new_custom_dropoff(location_id, drop_name){
		 console.log("DOOOOO")
		 $.ajax({
				async: false,
				cache: false,
				type: "GET",
				url: "create_custom_dropoff_location",
				data :	{
					location_id:location_id,
					dropoff_name:drop_name
				},
				timeout: 100000,
				success: function(data) {
					console.log(data);
					var form_dropoff='<option value="'+data.id+'">'+data.drop_off_name+'  ('+ data.location_name+')</option>';
					
					document.getElementById('destination_name').innerHTML=form_dropoff;
					$('#destination_name').material_select();
					$('#coustom_dropoff').modal('close');
				},
				error: function(e) {
					console.log("ERROR: ", e);
				},
				done: function(e) {
					console.log("DONE");
				}
		});
	 }
	 
});