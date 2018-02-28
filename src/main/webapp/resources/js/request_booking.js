$(document).ready(function() {
	 $('select').material_select();
	 
	 $("#departure_date").flatpickr({
			mode: "single",
			minDate: "today",
			dateFormat: "Y-m-d"
	});
	 $("#departure_time").flatpickr({
		 	enableTime: true,
		    noCalendar: true,
		    dateFormat: "H:i",
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
	 

	// ======== Booking Session to store at Booking Request ==========
	 $('#book_now').click(function(){
		 var source = $("#source_name").val();
		 var destination = $("#destination_name").val();
		 var time=$("#departure_time").val();
		 var date=$("#departure_date").val();
		 var number_of_seat=$("#number_of_seat").val();

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
				url: "customer_request_booking",
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
					$('#confirm').modal();
				    $('#confirm').modal('open');
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