<body onload="load()">
<article class="content cards-page">
                    	
                    <section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Schedule Detail </h3>
                                    </div>
                                    <form class="form-inline clearfix" style="display:block;" id="myForm" >
                                       <div class="form-group col-md-4" style="margin-bottom:2%; float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Code</label>
                                            <input type="text" class="form-control" id="scode" style="width: inherit;" placeholder=Code required disabled> </div>
                                       <div class="form-group col-md-4" style="margin-bottom:2%; float:left;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Driver</label>
                                            <select class="form-control" style="width: inherit;" id="sdriver" disabled><option></option></select> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%; float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Bus</label>
                                            <select class="form-control" style="width: inherit;" id="sbus" required disabled><option></option></select> </div>                                     
										<div class="form-group col-md-4" style="margin-bottom:2%; float:left;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">From</label>
                                            <select class="form-control" style="width: inherit;" id="sfrom" required disabled><option></option></select> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">To</label>
                                            <select class="form-control" style="width: inherit;" id="sto" required disabled><option></option></select> </div>     
                                        <div class="form-group col-md-4" style="margin-bottom:2%;float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Number of booking</label>
                                            <input type="text" class="form-control" id="snumberbooking" style="width: inherit;" placeholder="Number of booking" required disabled> </div>                                       
                                        <div class="form-group col-md-4" style="margin-bottom:2%;float:left;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Departure Date</label>
                                            <input type="text" class="form-control" id="sdeptdate" style="width: inherit;" placeholder="Departure Date" required disabled> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Departure Time</label>
                                            <input type="text" class="form-control" id="sdepttime" style="width: inherit;" placeholder="Departure Time" required disabled> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Remaining seats</label>
                                            <input type="text" class="form-control" id="sremaining" style="width: inherit;" placeholder="Remaining seats" required disabled> </div>
                                       
                                    </form>
                                    
                                    
                                   
                                    <div class="col-xl-12">
                                    <div class="card-block" style="padding-left:2px;">
                                        <ul class="nav nav-tabs nav-tabs-bordered">
                                            <li class="nav-item">
                                                <a href="#home" style="background-color:#52BCD3" class="nav-link active" data-target="#home" data-toggle="tab" aria-controls="home" role="tab">All bookings of this schedule</a>
                                            </li>
                                        
                                        </ul>
                                        <!-- Tab panes -->
                                        <div class="tab-content tabs-bordered">
                                            <div class="tab-pane fade in active" style="display:initial;" id="home">
                                                <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                        	
                                                            <th>No</th>
                                                            <th>Code</th>
                                                            <th>Name</th>
                                                            <th>Phone Number</th>
                                                            <th>Number of bookings</th>
                                                            <th>User Type</th>
                                                            
                                                        </tr>
                                                    </thead>
                                                    <tbody id="allBooking">
                                                        
                                                    </tbody>
                                                    
                                                </table>
                                            </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                  
                            </div>
                            
                            
                                </div>
                            </div>
                            
                        </div>
                    </section>
                    
                    
                </article>
                
                
                
                
         <div id="user_info_modal" class="modal">
                    <div class="modal-content center">
                        <h6 class="center light-blue-text">User Information</h6>
                        <table id="get_user_info"></table>
                    </div>
      </div>
       
      
  
</body>
<script type="text/javascript">
var current_schedule;
var all_bus;
var all_driver;
var all_customer;
var all_schedule;
var idd;
var all_booking;
var s_code;
load = function () {
	$('.sameheight-item').attr( "style", "" );
	var bootstrapjs = $("<script>");
  	$(bootstrapjs).attr('src', '/resources/Bootstrap/js/bootstrap.min.js');
 	$(bootstrapjs).appendTo('body');
	var data = ${data};
    console.log(data)
	var bookings = data.bookings;
	var buses  = data.buses;
	all_schedule = data.schedules;
	all_bus = buses;
	all_driver = data.drivers;
	all_customer = data.customers;
	all_booking = bookings;
	var locations = data.locations;
	var p_locations = data.p_locations;
	var schedule  = data.schedule;
	idd = schedule.id;
	current_schedule = schedule;
	s_code = schedule.code;
	$("#scode").val(schedule.code);
	$("#sremaining").val(schedule.remaining_seat);
	$("#sfrom").val(searchLocation(schedule.source_id,locations));
	$("#sto").val(searchLocation(schedule.destination_id,locations));
	$("#snumberbooking").val(schedule.number_booking);
	$("#sdeptdate").val(formatDate(schedule.dept_date));
	$("#sdepttime").val(schedule.dept_time);
	$("#sfrom").append("<option value="+current_schedule.source_id+">"+searchPLocation(current_schedule.source_id,p_locations)+", "+searchLocation(current_schedule.from_id,locations)+" </option>");
	$("#sto").append("<option value="+current_schedule.destination_id+">"+searchPLocation(current_schedule.destination_id,p_locations)+", "+searchLocation(current_schedule.to_id,locations)+" </option>");
	for(i=0; i<all_bus.length; i++)
  {
      if(all_bus[i].model == 'Rental Bus')
          all_bus[i].plate_number = ""
      $("#sbus").append("<option value="+all_bus[i].id+">"+all_bus[i].model+" "+all_bus[i].plate_number+" </option>");
  }             
	for(i=0; i<all_driver.length; i++)					
		$("#sdriver").append("<option value="+all_driver[i].id+">"+all_driver[i].name+" </option>");
	$("#sdriver").val(schedule.driver_id);
	$("#sbus").val(schedule.bus_id);
	$("#sfrom").val(current_schedule.source_id);
	$("#sto").val(current_schedule.destination_id);
	
	for (var i=0;i<bookings.length;i++)
	{
	var booking = '<tr class="hoverr" data-url="booking_detail?id='+bookings[i].id+'" toGet="'+bookings[i].id+'">'
    					+'<td>'+(i+1)+'</td>'
    					+'<td>'+bookings[i].code+'</td>'
						+'<td class="user_info" style="color:blue" data='+bookings[i].user_id+'>'+searchCustomer(bookings[i].user_id,all_customer)+'</td>'
                        +'<td>'+searchPhone(bookings[i].user_id,all_customer)+'</td>'
						+'<td>'+bookings[i].number_booking+'</td>'
                        +'<td>'+bookings[i].description+'</td></tr>';
	$("#allBooking").append(booking);				
	}
	$( ".unhoverr" ).on('click', function(e) {
		e.stopPropagation();	
	});
	

    $( ".user_info" ).on('click', function(e) {
                 console.log("KK");
                 e.stopPropagation();
                 var id=$(this).attr('data');
                 console.log(id);
                 $.ajax({
                        async: false,
                        cache: false,
                        type: "GET",
                        url: "get_sch_driver_info2",
                        data :{'id':id},
                        timeout: 100000,
                        success: function(data) {
                            console.log(data);
                            if(data[0].phone_number==""||data[0].phone_number==null)
                                data[0].phone_number = "";
                            var data='<tr><th>User\'s Name</th><td><b>:</b>  &nbsp&nbsp '+data[0].name+'</td></tr>'
                              +'<tr><th>Phone Number</th><td><b>:</b>  &nbsp&nbsp '+ data[0].phone_number+'</td></tr>'
                              +'<tr><th>Email</th><td><b>:</b> &nbsp&nbsp '+data[0].email +'</td></tr>'
                              document.getElementById('get_user_info').innerHTML=data;
                            $('#user_info_modal').modal();
                            $('#user_info_modal').modal('open');
                            
                        },
                        error: function(e) {
                            console.log("ERROR: ", e);
                        },
                        done: function(e) {
                            console.log("DONE");
                        }
                    });
             });
	



	$(".hoverr").on('click', function(e) {
		e.stopPropagation();
    	location.href=$(this).attr('data-url');
	});
	console.log(typeof(schedule.dept_time))
}



$(document).ready(function(){
	$("#scheduleMng").addClass("active");
	$('.sameheight-item').attr( "style", "" );
});


formatDate =function (date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
      if (day.length < 2) day = '0' + day;

    return [month, day, year].join('/');
};

function searchLocation(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].name;
        }
    }
}


function searchPLocation(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].name;
        }
    }
}



function searchSchedule(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id == id) {
            return myArray[i];
        }
    }
}


function searchCustomer(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].name;
        }
    }
}



function searchBus(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].model;
        }
    }
}


function searchBooking(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].number_booking;
        }
    }
}



function validateNumberOfSeat(id, myArray,seats){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
        	if(myArray[i].number_of_seat>=seats)
            	return true;
        	else
        		return false;
        }
    }
}


function searchPhone(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            if(myArray[i].phone_number==null||myArray[i].phone_number=="")
                return "";

            return myArray[i].phone_number;
        }
    }
}


function toDate(dStr,format) {
	var now = new Date();
	if (format == "h:m") {
 		now.setHours(dStr.substr(0,dStr.indexOf(":")));
 		now.setMinutes(dStr.substr(dStr.indexOf(":")+1));
 		now.setSeconds(0);
 		return now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
	}else 
		return "Invalid Format";
}


function toDate2(dStr,format) {
	var now = new Date();
	if (format == "h:m:s") {
 		now.setHours(dStr.substr(0,dStr.indexOf(":")));
 		now.setMinutes(dStr.substr(dStr.indexOf(":")+1));
 		now.setSeconds(dStr.substr(dStr.indexOf(":")+2));
 		return now;
	}else 
		return "Invalid Format";
}




</script>
