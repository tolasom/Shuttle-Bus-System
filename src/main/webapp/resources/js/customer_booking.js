$(document).ready(function() {
	 $('select').material_select();
	 
	 $(".flatpickr input").flatpickr({
			mode: "single",
			minDate: "today",
			dateFormat: "Y-m-d"
	});
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
	// ======== User Information ==========
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
				var location= Object.keys( data.location );
				var l_form='';
				for(i=0;i<location.length;i++){
					console.log(location[i])
					l_form+='<optgroup label="'+location[i]+'">';
					for(j=0;j<data.location[location[i]].length;j++){
						console.log(data.location[location[i]][j]);
						l_form+='<option value="'+data.location[location[i]][j].id+'">'+data.location[location[i]][j].name+'</option>';
					}
					l_form+='</optgroup>';		
				}
				console.log(l_form)
				document.getElementById('source_name').innerHTML=l_form;
				$('#source_name').material_select();
			},
			error: function(e) {
				console.log("ERROR: ", e);
			},
			done: function(e) {
				console.log("DONE");
			}
	});
	// ========Select Source Information ==========
	 $('#source_name').change(function() {
			var id = $("#source_name").val();
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
					var l_form='';
					for(i=0;i<location.length;i++){
						console.log(location[i])
						l_form+='<optgroup label="'+location[i]+'">';
						for(j=0;j<data.location[location[i]].length;j++){
							console.log(data.location[location[i]][j]);
							l_form+='<option value="'+data.location[location[i]][j].id+'">'+data.location[location[i]][j].name+'</option>';
						}
						l_form+='</optgroup>';		
					}
					console.log(l_form)
					document.getElementById('destination_name').innerHTML=l_form;
					$('#destination_name').material_select();
				},
				error: function(e) {
					console.log("ERROR: ", e);
				},
				done: function(e) {
					console.log("DONE");
				}
		});
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
				var option='<option value="" disabled selected>Departure Time</option>';
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
	 
		// ======== Booking Session ==========
	 $('#book_now').click(function(){
		 var source = $("#source_name").val();
		 var destination = $("#destination_name").val();
		 var time=$("#departure_time").val();
		 var date=$("#departure_date").val();
		 var number_of_seat=$("#number_of_seat").val();
		 console.log("KK");
		 console.log(source);
		 var submit={
				 "source":source,
				 "destination":destination,
				 "time":time,
				 "date":date,
				 "number_of_seat":number_of_seat,
		 }
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
					alert("A")
					//document.getElementById('fullname').innerHTML=data.username;
				},
				error: function(e) {
					console.log("ERROR: ", e);
					alert("A")
				},
				done: function(e) {
					console.log("DONE");
				}
		});
	 }) 
	 
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
							+'<table class="bordered"><thead><tr>'
							+'<th>No.</th>'
							+'<th>Departure Date&Time</th>'
							+'<th>Source and Destination</th>'
							+'<th>Number of Ticket</th>'
							+'<th>Bus Model</th>'
							+'<th>Driver\'s Name</th>'
							+'</tr></thead><tbody>';
					for(i=0;i<data.length;i++){
//						var get_time=convert_time(data[i].dept_time);
//						option+='<option value="'+data[i].dept_time+'">'+get_time+'</option>';
						bh_form+='<tr><td>'+(i+1)+'</td>'
								 +'<td>'+convert_date(data[i].dept_date)+' '+convert_time(data[i].dept_time)+'</td>'
								 +'<td>'+data[i].scource+' to '+data[i].destination +'</td>'
								 +'<td>'+data[i].number_of_ticket+'</td>'
								 +'<td>'+data[i].bus_model+'</td>'
								 +'<td>'+data[i].diver_name+'</td></tr>';
   
					}
					bh_form+='</tbody></table>';
				}
				document.getElementById('booking_history').innerHTML=bh_form;
			},
			error: function(e) {
				console.log("ERROR: ", e);
			},
			done: function(e) {
				console.log("DONE");
			}
	});

	 console.log("LLLLLLLLLLLLLLLL")
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
				
//				var bh_form='';
//				if(data.length>0){
//					bh_form+='<h5 class="center">Booking History</h5>'
//							+'<table class="bordered"><thead><tr>'
//							+'<th>No.</th>'
//							+'<th>Departure Date&Time</th>'
//							+'<th>Source and Destination</th>'
//							+'<th>Number of Ticket</th>'
//							+'<th>Bus Model</th>'
//							+'<th>Driver\'s Name</th>'
//							+'</tr></thead><tbody>';
//					for(i=0;i<data.length;i++){
//						bh_form+='<tr><td>'+(i+1)+'</td>'
//								 +'<td>'+convert_date(data[i].dept_date)+' '+convert_time(data[i].dept_time)+'</td>'
//								 +'<td>'+data[i].scource+' to '+data[i].destination +'</td>'
//								 +'<td>'+data[i].number_of_ticket+'</td>'
//								 +'<td>'+data[i].bus_model+'</td>'
//								 +'<td>'+data[i].diver_name+'</td></tr>';
//   
//					}
//					bh_form+='</tbody></table>';
//				}
//				document.getElementById('booking_history').innerHTML=bh_form;
			},
			error: function(e) {
				console.log("ERROR: ", e);
			},
			done: function(e) {
				console.log("DONE");
			}
	});
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