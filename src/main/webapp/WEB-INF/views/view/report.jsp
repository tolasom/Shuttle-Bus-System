<body onload="load()">
<article class="content cards-page">                   
                  
	                      	
                    <section class="section">
                        <div class="row">
                           
                        </div>
                    </section>
                </article>
                
                
              
              
              
                
                <button style="display: none;"class="btn btn-primary" data-toggle="modal" data-target="#myModal" data-backdrop="static" data-keyboard="false" id="mybtn">
    Login modal</button>
    <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Which bookings?</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="myForm">
                                        <div class="form-group">
                                            <label class="control-label">From (Source Location)</label>
                                            <select class="form-control boxed" id="from">
                                            	<option></option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">To (Destination Location)</label>
                                            <select class="form-control boxed" id="to">
                                            	<option></option>
                                            </select>
                                        </div>
                                        
                                         <div class="form-group">
                                            <label class="control-label">Departure Date</label>
                                            <input type="text" name ="date" class="form-control boxed" id="deptdate"> </div>
                                         <div class="form-group">
                                            <label class="control-label">Departure Time</label>
                                            <select class="form-control boxed" id="depttime">
                                                <option value="nth"></option>
                                            </select> 
                                            </div>
                                         <div class="form-group">
                                            <label class="control-label">Status</label>
                                            <select class="form-control boxed" id="status">
                                                <option value="nth"></option>
                                                <option value="Booked">Booked</option>
                                                <option value="Cancelled">Cancelled</option>
                                            </select> 
                                            </div>
                                            <button type="submit" id="bsubmit" class="btn btn-default" style="display:none;">Create</button>
                                        	</form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
          <button onClick="goTO()" class="btn btn-info">View Before Generating</button>
        </div>
      </div>
      
    </div>
  </div>
  
  
  
  

                
</body>
<script>
var locations;

$(document).ready(function(){
	var bootstrapjs = $("<script>");
  	$(bootstrapjs).attr('src', '/KIT_Point_Management_System/resources/Bootstrap/js/bootstrap.min.js');
  	$(bootstrapjs).appendTo('body');
	$("#breport").addClass("active");
	$('#myModal').on('hide.bs.modal', function (e) {
		window.location.href = "current_schedule";
	})
	
    $(".ir2").slideToggle();
                        $("#ddr1").toggleClass("irr2");
                        $("#ddr2").toggleClass("irr2");
    $( "#reportMng" ).off();
    $("[name=date]").keydown(function (event) {
            event.preventDefault();
        });

	$("#from").change(function(){
		var input  = this.value;
		var location_id;
		$('#to').children('option:not(:first)').remove();
		for(i=0; i<locations.length; i++)
			{
			if(locations[i].id==input)
				location_id = locations[i].id;
				
			}
		for(i=0; i<locations.length; i++)
		{
		if(locations[i].id==location_id)
			{
			
			}
		else
			$("#to").append("<option value="+locations[i].id+">"+locations[i].name+" </option>");
			
		}	
	    
	});
	
	
	$("#myForm").on('submit',function(e){
		count=0;
		e.preventDefault();
        var from = $("#from").val();
        var to = $("#to").val();
        var date = $("#deptdate").val();
        var time = $("#depttime").val();
        var status  = $("#status").val();
		if(from==""||from==null)
		{
            count++;
            from = 0;
        }
        else
            from = parseInt(from);
		if(to==""||to==null)
		{
            count++;
            to = 0;
        }
        else
            to = parseInt(to);
		if(date==""||date==null)
			{
                count++;
                date = "nth";
            }
		if(time=="nth")
			count++;
        if(status=="nth")
            count++;
        
		if(count>=5)
			{
			swal("Action Disallowed!", "Please fill out at least one field!", "error")
			return
			}
		else
			{
            console.log(from)
            console.log(to)
			$.ajax({
    		url:'reportSubmit',
    		type:'GET',
    		data:{	from_id:from,
    				to_id:to,
    				dept_date:date,
    				n:time,
                    notification:status

    			},
    		traditional: true,			
    		success: function(response){
                    var data = JSON.stringify(response.message);
                     if(response.message.length>0)
                    {
                        setTimeout(function() {
                            swal({
                                title: "Done!",
                                text: response.message.length+" bookings were found!",
                                type: "success"
                            }, function() {
                                window.location = "bookingReport2?data="+data;
                            });
                        }, 10);
                    }
                    else 
                        swal("Oops!", "No bookings as you required!", "error")
                  },
    		error: function(err){
    				console.log(JSON.stringify(err));
    				
    				}
    		
    			});	
			}
		
	});
	
	
	
	
});

load = function(){
	$('#mybtn').trigger('click');
    var ad = ${data};
    locations = ad.locations;
    var times = ad.times;
    for(i=0; i<locations.length; i++)                   
        $("#from").append("<option value="+locations[i].id+">"+locations[i].name+" </option>");
    for(i=0; i<times.length; i++)                   
        $("#depttime").append("<option value="+times[i].dept_time+">"+times[i].dept_time+" </option>");
	
}


goTO = function(){
    $('#bsubmit').trigger('click');
}

function toDate(dStr,format) {
    var now = new Date();
    console.log(dStr)
    if (format == "h:m") {
        now.setHours(dStr.substr(0,dStr.indexOf(":")));
        now.setMinutes(dStr.substr(dStr.indexOf(":")+1));
        now.setSeconds(0);
        return now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
    }else 
        return "Invalid Format";
}
</script>