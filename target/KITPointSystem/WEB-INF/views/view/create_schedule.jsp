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
                                            <input type="text" class="form-control" style="width: inherit;" id="scode" placeholder=Code required> </div>
                                       <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Driver</label>
                                            <select class="form-control" style="width: inherit;" id="sdriver" required><option></option></select> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Bus</label>
                                            <select class="form-control" style="width: inherit;" id="sbus" required><option></option></select> </div>                                     
										<div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">From</label>
                                            <select class="form-control" style="width: inherit;" id="sfrom" required><option></option></select></div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">To</label>
                                            <select class="form-control" style="width: inherit;" id="sto" required><option></option></select> </div>     
                                                                               
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Departure Date</label>
                                            <input type="text" name="date" class="form-control" style="width: inherit;" id="sdeptdate" placeholder="Departure Date" required> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Departure Time</label>
                                            <input type="text" name="time" class="form-control" style="width: inherit;" id="sdepttime" placeholder="Departure Time" required> </div>
                                        <div class="form-group col-md-12" style="margin-bottom:2%;">
											<button type="submit" class="btn btn-info">Create</button>
											</div>
                                    </form>
                      <!--                  <div class="col-xl-12">
                                    <div class="card-block" style="padding-left:2px;">
                                        <ul class="nav nav-tabs nav-tabs-bordered">
                                            <li class="nav-item">
                                                <a href="#home" style="background-color:#52BCD3" class="nav-link active" data-target="#home" data-toggle="tab" aria-controls="home" role="tab">All bookings of this schedule</a>
                                            </li>
                                        
                                        </ul>
                                        
                                        <div class="tab-content tabs-bordered">
                                            <div class="tab-pane fade in active" style="display:initial;" id="home">
                                                <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <tr>
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
                                  
                            </div>  -->
                                </div>
                            </div>
                        </div>
                    </section>
                    
                    
                </article>
                
</body>
<script type="text/javascript">
var locations;
var pickup_location;
var s_code;
load = function () {
	var data = ${data};
	console.log(data)
	var buses  = data.buses;
	var code = data.code;
	s_code = code;
	var location = data.locations;
	pickup_locations = location;
	locations = data.main_locations;
	var drivers = data.drivers;
	for(i=0; i<buses.length; i++)					
		$("#sbus").append("<option value="+buses[i].id+">"+buses[i].model+" </option>");
	for(i=0; i<pickup_locations.length; i++)					
		$("#sfrom").append("<option value="+pickup_locations[i].id+">"+pickup_locations[i].name+"  ("+searchPLocation(pickup_locations[i].location_id,locations)+")"+" </option>");
	for(i=0; i<drivers.length; i++)					
		$("#sdriver").append("<option value="+drivers[i].id+">"+drivers[i].name+" </option>");
	$("#scode").val(code);
	$("#scode").prop('disabled', true);
	
}



$(document).ready(function(){
	$("#scheduleMng").addClass("active");
	
	$("#sfrom").change(function(){
		var input  = this.value;
		var location_id;
		$('#sto').children('option:not(:first)').remove();
		for(i=0; i<pickup_locations.length; i++)
			{
			if(pickup_locations[i].id==input)
				location_id = pickup_locations[i].location_id;
				
			}
		for(i=0; i<pickup_locations.length; i++)
		{
		if(pickup_locations[i].location_id==location_id)
			{
			
			}
		else
			$("#sto").append("<option value="+pickup_locations[i].id+">"+pickup_locations[i].name+"  ("+searchPLocation(pickup_locations[i].location_id,locations)+")"+" </option>");
			
		}	
	    
	});
	
	$("#myForm").on('submit',function(e){
		e.preventDefault();
		if($("#scode").val()!=s_code)
		{
		swal("Action Disallowed!", "You cannot change Code!", "error")
		return
		}
		if($("#sdriver").val()==""||$("#sdriver").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Driver field blank!", "error")
		return
		}
		if($("#sbus").val()==""||$("#sbus").val()==null)
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
		$.ajax({
    		url:'createSchedule',
    		type:'GET',
    		data:{	code:$("#scode").val(),
    				driver_id:parseInt($("#sdriver").val()),
    				bus_id:parseInt($("#sbus").val()),
    				source_id:parseInt($("#sfrom").val()),
    				destination_id:parseInt($("#sto").val()),
    				number_booking:0,
    				dept_date:$("#sdeptdate").val(),
    				dept_time:toDate($("#sdepttime").val(),'h:m')
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
		
	});
});


formatDate =function (date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
      if (day.length < 2) day = '0' + day;

    return [month, day, year].join('-');
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

function searchBus(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].model;
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
