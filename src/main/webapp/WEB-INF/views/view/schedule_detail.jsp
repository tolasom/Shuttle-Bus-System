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
                                            <select class="form-control" style="width: inherit;" id="sdriver"><option></option></select> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%; float:left;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Bus</label>
                                            <select class="form-control" style="width: inherit;" id="sbus" required><option></option></select> </div>                                     
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
                            
                            <div class="col-md-12">
								<button class="btn btn-warning pull-right" onClick="openModal()" style="color:white;" id="moveBtn">Move to other schedule <i class="fa fa-exchange"></i></button>
							</div>
                                </div>
                            </div>
                            
                        </div>
                    </section>
                    
                    
                </article>
                
                
                
                <div class="modal fade" id="moveModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Move bookings to another schedule</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="mf">
                                        <div class="form-group">
                                            <label class="control-label">Choose a schedule</label>
                                            <select class="form-control boxed" id="mschedule">
                                            	<option></option>
                                            </select>
                                             </div>
                                          <button type="submit" id="bsubmit2" class="btn btn-default" style="display:none;">Create</button>
                                    </form>
        </div>
        <div class="modal-footer">
          <button onClick="goTO2()" class="btn btn-info" id="moveNew">Create a new schedule</button>
          <button onClick="goTO3()" class="btn btn-info" id="moveSimple">Move</button>
        </div>
      </div>
      
    </div>
  </div>
  
  
  
  
  <div class="modal fade" id="selectBusModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Move bookings to another schedule</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="moveForm">
                                        <div class="form-group">
                                            <label class="control-label">Select a driver</label>
                                            <select class="form-control boxed" id="mdriver" required>
                                            	<option></option>
                                            </select>
                                       </div>
                                       <div class="form-group">
                                            <label class="control-label">Select a bus</label>
                                            <select class="form-control boxed" id="mbus" required>
                                            	<option></option>
                                            </select>
                                       </div>
                                         <button type="submit" id="bsubmit" class="btn btn-default" style="display:none;">Create</button>
                                    </form>
        </div>
        <div class="modal-footer">
          <button onClick="goTO()" class="btn btn-info" id="moveNew">Create a new schedule</button>
        </div>
      </div>
      
    </div>
  </div>
       
       
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
  // if(schedule.source_id!=0)
  //     $("#sfrom").val(searchPLocation(schedule.source_id,p_locations)+", "+searchLocation(booking.from_id,locations));
  // else
  //       $("#from").val(searchLocation(booking.from_id,locations));
  // if(booking.destination_id!=0)
  //     $("#to").val(searchPLocation(booking.destination_id,p_locations)+", "+searchLocation(booking.to_id,locations));
  // else
  //     $("#to").val(searchLocation(booking.to_id,locations));

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
						+'<td class="unhoverr"><label class="item-check" id="select-all-items"><input type="checkbox" class="checkbox">'
    					+'<span></span></label></td>'
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
	$(".checkbox").on('click', function(e) {
    console.log("Chh")
		var a = $(this).parents(".hoverr")[0];
		$(a).toggleClass("selected");
		showMoveBtn(a);
	});
	$("#moveBtn").hide();
}



$(document).ready(function(){
	$("#scheduleMng").addClass("active");
	$('.sameheight-item').attr( "style", "" );
	
	
	$("#myForm").on('submit',function(e){
		e.preventDefault();
		var driverId = 0;
		if($("#sdriver").val()==""||$("#sdriver").val()==null)
			{}
		else
			driverId = parseInt($("#sdriver").val());
		
		var busId = 0;
		if($("#sbus").val()==""||$("#sbus").val()==null)
			{}
		else
			busId = parseInt($("#sbus").val());
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
		if($("#sremaining").val()!=current_schedule.remaining_seat)
		{
		swal("Action Disallowed!", "You cannot update number of remaining seat.", "error")
		return
		}
		if($("#sdeptdate").val()!=formatDate(current_schedule.dept_date))
		{
		swal("Action Disallowed!", "You cannot update departure date.", "error")
		return
		}
		if($("#sdepttime").val()!=current_schedule.dept_time)
		{
		swal("Action Disallowed!", "You cannot update departure time.", "error")
		return
		}
		if($("#sbus").val()==""||$("#sbus").val()==null||$("#sbus").val()==0)
		{
		swal("Action Disallowed!", "You cannot leave Bus field blank!", "error")
		return
		}
		if($("#sfrom").val()==""||$("#sfrom").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave From field blank!", "error")
		return
		}
		if($("#sto").val()==""||$("#sto").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave To field blank!", "error")
		return
		}
		if($("#sdeptdate").val()==""||$("#sdeptdate").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Departure Date field blank!", "error")
		return
		}
		if($("#sdepttime").val()==""||$("#sdepttime").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Departure Time blank!", "error")
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
	
	
	$("#moveForm").on('submit',function(e){
		e.preventDefault();
		var b_ids = [];
		$(".selected").each(function(){
			b_ids.push(parseInt($(this).attr("toget"))); 
		});
		var total_seat=0;
		for (var i=0;i<b_ids.length;i++)
			{
				total_seat+=searchBooking(b_ids[i],all_booking);
			}
		console.log(total_seat)
		if($("#mbus").val()==""||$("#mbus").val()==null)
			{
			swal("Action Disallowed!", "You cannot leave Bus field blank.", "error")
			return
			}
		if($("#mdriver").val()==""||$("#mdriver").val()==null)
			{
			swal("Action Disallowed!", "You cannot leave Driver field blank.", "error")
			return
			}
		if(validateNumberOfSeat(parseInt($("#mbus").val()),all_bus,total_seat))
		{
			$.ajax({
	    		url:'moveNew',
	    		type:'GET',
	    		data:{	
	    				id:idd,
	    				b:b_ids,
	    				driver_id:parseInt($("#mdriver").val()),
	    				bus_id:parseInt($("#mbus").val())	    				
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
	    				            window.location.reload();;
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
			swal("Action Disallowed!", "This bus's seats are not enough", "error")
		}
	});
	
	
	
	$("#mf").on('submit',function(e){
		e.preventDefault();
		console.log($("#mschedule").val());
		var old_date = new Date($("#sdeptdate").val()).getTime();
		var s = searchSchedule($("#mschedule").val(),all_schedule)
		var new_date = s.dept_date;
		var n1= String (formatDate(new Date()))+" "+ s.dept_time;
		var new_time = Date.parse(n1);
		var o1 = String (formatDate(new Date())) +" "+ $("#sdepttime").val();
		var old_time = Date.parse(o1);
		console.log(old_time);
		console.log(new_time);
		var b_ids = [];
		$(".selected").each(function(){
			b_ids.push(parseInt($(this).attr("toget"))); 
		});
		var total_seat=0;
		for (var i=0;i<b_ids.length;i++)
			{
				total_seat+=searchBooking(b_ids[i],all_booking);
			}
		if(old_date!=new_date||old_time!=new_time)
		{
		swal("Action Disallowed!", "You cannot move to the schedule which has different departure date and time!", "error")
		return
		}
		if(s.remaining_seat>=total_seat)
		{
			$.ajax({
	    		url:'moveSimple',
	    		type:'GET',
	    		data:{	
	    				old_id:idd,
	    				new_id:$("#mschedule").val(),
	    				b:b_ids,
	    				number_booking:total_seat	    				
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
	    				            window.location.reload();;
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
			swal("Action Disallowed!", "This bus's seats of that schedule are not enough", "error")	
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


function searchPLocation(id, myArray){
    if(id==0)
      return "";
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
function searchPhone(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
        	if(myArray[i].phone_number==null||myArray[i].phone_number=="")
        		return "";

            return myArray[i].phone_number;
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


function showMoveBtn(e){
	
	var numItems = $('.selected').length;
	if(numItems>0)
		$("#moveBtn").show();
	else 
		$("#moveBtn").hide();
	
}


function openModal(){
	$('#moveModal').modal('toggle');
	$('#mschedule').children('option:not(:first)').remove();
	for(var i=0; i<all_schedule.length; i++)					
		{
		if(all_schedule[i].id!=idd)
			{
			var s  = "<option value="+all_schedule[i].id+">"+all_schedule[i].code+"</option>";
			$("#mschedule").append(s);
			}
		}
	$("#moveSimple").hide();
	$("#moveNew").show();

}

$( "#mschedule" ).on('change', function(e) {
	if(this.value==""||this.value==null)
		{
		$("#moveSimple").hide();
		$("#moveNew").show();
		}
	else
		{
		$("#moveNew").hide();
		$("#moveSimple").show();
		}
		
});

goTO = function(){
	$('#bsubmit').trigger('click');
}

goTO3 = function(){
	$('#bsubmit2').trigger('click');
}
goTO2 = function(){
	$("#selectBusModal").modal('toggle');
	$('#mdriver').children('option:not(:first)').remove();
	$('#mbus').children('option:not(:first)').remove();
	for(i=0; i<all_bus.length; i++)					
		$("#mbus").append("<option value="+all_bus[i].id+">"+all_bus[i].model+" </option>");
	for(i=0; i<all_driver.length; i++)					
		$("#mdriver").append("<option value="+all_driver[i].id+">"+all_driver[i].name+" </option>");
}

</script>
