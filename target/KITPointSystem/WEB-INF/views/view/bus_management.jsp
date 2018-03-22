<body onload="load()">
<article class="content cards-page">
                    <div class="title-block">
                        <h3 class="title"> Bus Management </h3><br>
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">Create</button>
                        <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Create a bus</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="myForm">
                                        <div class="form-group">
                                            <label class="control-label">Model</label>
                                            <input type="text" class="form-control boxed" id="model" maxlength="20" required> </div>
                                         <div class="form-group">
                                            <label class="control-label">Plate Number</label>
                                            <input type="text" class="form-control boxed" id="plate_number" maxlength="9" required> </div>
                                         <div class="form-group">
                                            <label class="control-label">Number of seat</label>
                                            <input type="text" class="form-control boxed" id="number_of_seat" maxlength="2" required> </div>
                                        <div class="form-group">
                                            <label class="control-label">Description</label>
                                            <textarea rows="3" class="form-control boxed" id="description" maxlength="80"></textarea>
                                        </div>
                                         <button type="submit" id="bsubmit" class="btn btn-default" style="display:none;">Create</button>
                                    </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
          <button onClick="goTO()" class="btn btn-info">Create</button>
        </div>
      </div>
      
    </div>
  </div>
<!--                         <p class="title-description"> Create, Update or View all buses information </p> -->
                    </div>
                    <section class="section">
	                        
                        	
                        <!-- /.row -->
                    </section>
                    
                </article>
                
</body>
<script type="text/javascript">
load = function(){	
	$.ajax({
		url:'getAllBuses',
		type:'GET',
		success: function(response){
			buses = response.buses;
    		var count=0;
    		var rrow=1;
    		if(buses.length<4)
    			count=4;
    		else count = buses.length;
    		for (var j=0;j<=parseInt(count/4);j++)
    			{
    			var row = '<div class="row" id="row'+(parseInt(j)+1)+'"></div>';
    			$(".section").append(row);
    			}
    		for(var i=0;i<buses.length;i++)
    			{
    			var card = '<div class="col-xl-3">'+
								'<div class="card card-info">'+
									 '<div class="card-block">'+
											'<h1><center>'+
												'<div class="cardHeader">'+
													buses[i].model+
												'</div></center>'+
											'</h1><a href="bus_update?id='+buses[i].id+'">Update</a>'+ 
											"<a href='javascript:func("+buses[i].id+")'>Delete</a>"+  
										  '</div>'+
				
								'</div>'+
							'</div>';
				if(i<4)
					$("#row1").append(card);
				else
					$("#row"+(parseInt(i/4)+1)).append(card);
					
    			}
			
			
	
	
		
		},
	error: function(err){
		swal("Oops!", "Cannot get all buses data", "error")
		console.log(JSON.stringify(err));
		}
		
	});
	
}

goTO = function(){
	$('#bsubmit').trigger('click');
}


$(document).ready(function(){
	$("#busMng").addClass("active");
	$("#number_of_seat").keypress(function (e) {
	     //if the letter is not digit then display error and don't type anything
	     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
	        //display error message
	        $("#errmsg").html("Digits Only").show().fadeOut("slow");
	               return false;
	    }
	   });
	$("#myForm").on('submit',function(e){
		e.preventDefault();
		var b_plate = $("#plate_number").val().trim();
		var b_model = $("#model").val().trim();
		var b_number_of_seat = $("#number_of_seat").val().trim();
		var b_des = $("#description").val().trim();
		var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;
		if((b_plate=='') || (b_model=='')||(b_number_of_seat==''))
		{
		swal("Oops!", "The input cannot be empty", "error")
		return
		}
		if((format.test(b_plate)) || (format.test(b_model))|| (format.test(b_number_of_seat)))
		{
		swal("Oops!", "You cannot input special characters in any one of the fields", "error")  
		return
		}
		if(getlength(b_plate)>9)
		{
			swal("Oops!", "Plate Number Cannot Be More Than 9 Digits", "error")  
			return
		}
		if(getlength(b_model)>20)
		{
			swal("Oops!", "Model Cannot Be More Than 20 Characters", "error")  
			return
		}
		if(getlength(b_number_of_seat)>2)
		{
			swal("Oops!", "Number of seats Cannot Be More Than 2 Digits", "error")  
			return
		}
		if(getlength(b_des)>80)
		{
			swal("Oops!", "Description Cannot Be More Than 80 Digits", "error")  
			return
		}
		$.ajax({
    		url:'createBus',
    		type:'GET',
    		data:{	plate_number:b_plate,
    				model:b_model,
    				number_of_seat:parseInt(b_number_of_seat),
    				description:b_des    			},
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
    				            window.location = "bus_management";
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

	


func=function(id)
{
swal({
    title: "Do you want to delete this bus?",
    text: "All information related to this bus will be deleted!",
    type: "warning",
    showCancelButton: true,
    confirmButtonColor: "#E71D36",
    confirmButtonText: "Delete",
    cancelButtonText: "Cancel",
    closeOnConfirm: false,
    closeOnCancel: true
  },
    function (isConfirm) {
      if (isConfirm) {
    	  $.ajax({
    	     url:'deleteBus',
    	     type:'GET',
    	     data:{id:id},
    	     traditional: true,
    	     success: function(response){
    	      if(response.status=="200")
    	      {
    	      setTimeout(function() {
    	             swal({
    	                 title: "Done!",
    	                 text: "You have deleted it successfully!",
    	                 type: "success"
    	             }, function() {
    	                 window.location = "bus_management";
    	             });
    	         }, 10);

    	      }

    	          else 
    	           {
    	           swal("Oops!","It is not deleted", "error")

    	           } 
    	     },
    				error: function(err){
    					
    					console.log(JSON.stringify(err));
    					}
    	       });
      } 
    });
    }
    
function getlength(number) {
    return number.toString().length;
}
	
</script>
