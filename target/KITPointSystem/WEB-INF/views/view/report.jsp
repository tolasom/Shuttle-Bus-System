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
                                            <input type="text" name ="date" class="form-control boxed" id="deptdate" required> </div>
                                         <div class="form-group">
                                            <label class="control-label">Departure Time</label>
                                            <input type="text" name ="time" class="form-control boxed" id="depttime" required> </div>
                                        	</form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
          <button onClick="goTO()" class="btn btn-info">Create</button>
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
	$("#reportMng").addClass("active");
	$('#myModal').on('hide.bs.modal', function (e) {
		window.history.back();
	})
	
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
		int count=0;
		e.preventDefault();
		if($("#from").val()==""||$("#from").val()==null)
			count++;
		if($("#to").val()==""||$("#to").val()==null)
			count++;
		if($("#deptdate").val()==""||$("#deptdate").val()==null)
			count++;
		if($("#depttime").val()==""||$("#depttime").val()==null)
			count++;
		if(count>=4)
			{
			swal("Action Disallowed!", "Please fill out at least one field!", "error")
			return
			}
		else
			{
			$.ajax({
    		url:'reportSubmit',
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
			}
		
	});
	
	
	
	
});

load = function(){
	$('#mybtn').trigger('click');
	$.ajax({
		url:'bookingReport',
		type:'GET',		
		success: function(response){
				console.log(response)
				locations = response.locations;
				for(i=0; i<locations.length; i++)					
					$("#from").append("<option value="+locations[i].id+">"+locations[i].name+" </option>");
				},
		error: function(err){
			
				}
		
			});
	
}

</script>