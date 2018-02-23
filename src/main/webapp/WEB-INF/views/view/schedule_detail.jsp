<body onload="load()">
<article class="content cards-page">
                    	
                    <section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Schedule Detail </h3>
                                    </div>
                                    <form class="form-inline" id="myForm">
                                       <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Code</label>
                                            <input type="text" class="form-control" id="scode" style="width: inherit;" placeholder=Code> </div>
                                       <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Driver</label>
                                            <select class="form-control" style="width: inherit;" id="sdriver"><option></option></select> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Bus</label>
                                            <select class="form-control" style="width: inherit;" id="sbus"><option></option></select> </div>                                     
										<div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">From</label>
                                            <select class="form-control" style="width: inherit;" id="sfrom"><option></option></select> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">To</label>
                                            <select class="form-control" style="width: inherit;" id="sto"><option></option></select> </div>     
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Number of booking</label>
                                            <input type="text" class="form-control" id="snumberbooking" style="width: inherit;" placeholder="Number of booking"> </div>                                       
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Departure Date</label>
                                            <input type="text" class="form-control" id="sdeptdate" style="width: inherit;" placeholder="Departure Date"> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Departure Time</label>
                                            <input type="text" class="form-control" id="sdepttime" style="width: inherit;" placeholder="Departure Time"> </div>
                                        <div class="form-group col-md-12" style="margin-bottom:2%;">
											<button type="submit" class="btn btn-info">Update</button>
											</div>
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
                                                        	<th></th>
                                                            <th>No</th>
                                                            <th>Name</th>
                                                            <th>Number of bookings</th>
                                                            
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
                
</body>
<script type="text/javascript">
var current_schedule;
var all_bus;
var all_driver;
load = function () {
	var data = ${data};
	var bookings = data.bookings;
	var buses  = data.buses;
	all_bus = buses;
	all_driver = data.drivers;
	var locations = data.locations;
	var schedule  = data.schedule;
	current_schedule = schedule;
	$("#scode").val(schedule.code);
	$("#sfrom").val(searchLocation(schedule.source_id,locations));
	$("#sto").val(searchLocation(schedule.destination_id,locations));
	$("#snumberbooking").val(schedule.number_booking);
	$("#sdeptdate").val(formatDate(schedule.dept_date));
	$("#sdepttime").val(schedule.dept_time);
	$("#sfrom").append("<option value="+current_schedule.source_id+">"+searchLocation(current_schedule.source_id,locations)+" </option>");
	$("#sto").append("<option value="+current_schedule.destination_id+">"+searchLocation(current_schedule.destination_id,locations)+" </option>");
	for(i=0; i<all_bus.length; i++)					
		$("#sbus").append("<option value="+all_bus[i].id+">"+all_bus[i].model+" </option>");
	for(i=0; i<all_driver.length; i++)					
		$("#sdriver").append("<option value="+all_driver[i].id+">"+all_driver[i].name+" </option>");
	$("#sdriver").val(schedule.driver_id);
	$("#sbus").val(schedule.bus_id);
	$("#sfrom").val(current_schedule.source_id);
	$("#sto").val(current_schedule.destination_id);
	for (var i=0;i<bookings.length;i++)
	{
	var booking = '<tr class="hoverr" data-url="booking_detail?id='+bookings[i].id+'">'
						+'<td><label class="item-check" id="select-all-items"><input type="checkbox" class="checkbox">'
    					+'<span></span></label></td>'
    					+'<td>'+(i+1)+'</td>'
						+'<td>'+bookings[i].user_id+'</td>'
						+'<td>'+bookings[i].number_booking+'</td></tr>';
	$("#allBooking").append(booking);				
	}
	$(".hoverr").on('click', function() {
    	location.href=$(this).attr('data-url');
	});
}



$(document).ready(function(){
	$("#scheduleMng").addClass("active");
	$("#myForm").on('submit',function(e){
		e.preventDefault();
		var driverId = parseInt($("#sdriver").val());
		var busId = parseInt($("#sbus").val());
		var seats = $("#snumberbooking").val();
		if($("#scode").val()!=current_schedule.code)
		{
		swal("Action Disallowed!", "You cannot update schedule code", "error")
		return
		}
		if($("#sfrom").val()!=current_schedule.source_id)
		{
		swal("Action Disallowed!", "You cannot update \"From\".", "error")
		return
		}
		if($("#sto").val()!=current_schedule.destination_id)
		{
		swal("Action Disallowed!", "You cannot update \"To\".", "error")
		return
		}
		if($("#snumberbooking").val()!=current_schedule.number_booking)
		{
		swal("Action Disallowed!", "You cannot update number of bookings.", "error")
		return
		}
		if($("#snumberbooking").val()!=current_schedule.number_booking)
		{
		swal("Action Disallowed!", "You cannot update number of bookings.", "error")
		return
		}
		if(validateNumberOfSeat(busId,all_bus,seats))
			{
			$.ajax({
	    		url:'updateSchedule',
	    		type:'GET',
	    		data:{	
	    				id:current_schedule.id,
	    				driver_id:driverId,
	    				bus_id:busId,
	    				
	    			},
	    		traditional: true,			
	    		success: function(response){
	    				if(response.status=="1")
	    					{
	    					setTimeout(function() {
	    				        swal({
	    				            title: "Done!",
	    				            text: response.message,
	    				            type: "success"
	    				        }, function() {
	    				            window.location = "current_schedule";
	    				        });
	    				    }, 10);
	    					
	    					}
	    				//var obj = jQuery.parseJSON(response);
	    				else 
	     					swal("Oops!", response.message, "error")    
	    				
	    				},
	    		error: function(err){
	    				console.log(JSON.stringify(err));
	    				
	    				}
	    		
	    			});
			}
		else
			{
			swal("Action Disallowed!", "This bus's seats are not enough, please reset", "error")
			}
			
		
	});
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

function searchBus(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].model;
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

	
</script>
