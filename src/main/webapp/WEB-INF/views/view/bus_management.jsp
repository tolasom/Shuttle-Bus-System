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
                                            <input type="text" class="form-control boxed" id="model"> </div>
                                         <div class="form-group">
                                            <label class="control-label">Plate Number</label>
                                            <input type="text" class="form-control boxed" id="plate_number"> </div>
                                         <div class="form-group">
                                            <label class="control-label">Number of seat</label>
                                            <input type="number" class="form-control boxed" id="number_of_seat"> </div>
                                        <div class="form-group">
                                            <label class="control-label">Description</label>
                                            <textarea rows="3" class="form-control boxed" id="description"></textarea>
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
	$("#myForm").on('submit',function(e){
		console.log("Fired")
		e.preventDefault();
		$.ajax({
    		url:'createBus',
    		type:'GET',
    		data:{	plate_number:$("#plate_number").val(),
    				model:$("#model").val(),
    				number_of_seat:$("#number_of_seat").val(),
    				description:$("#description").val()
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
	
</script>
