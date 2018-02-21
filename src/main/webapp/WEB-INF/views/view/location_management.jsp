 <body onload="load()">
 <article class="content grid-page">
                    <div class="title-block">
                        <h3 class="title"> Location Management </h3><br>
                        <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">Create</button>
                        <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Create a location</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="myForm">
                                        <div class="form-group">
                                            <label class="control-label">Name</label>
                                            <input type="text" class="form-control boxed" id="name"> </div>
                                       
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
  
  
  
  
  <div class="modal fade" id="myModal2" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Create a pick up location</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="myForm2">
                                        <div class="form-group">
                                            <label class="control-label">Name</label>
                                            <input type="text" class="form-control boxed" id="pname"> </div>
                                       
                                         <button type="submit" id="bpsubmit" class="btn btn-default" style="display:none;">Create</button>
                                    </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
          <button onClick="goTO2()" class="btn btn-info">Create</button>
        </div>
      </div>
      
    </div>
  </div>
  
  
  
  <div class="modal fade" id="myModal3" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Update a location</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="updateLocationForm">
                                        <div class="form-group">
                                            <label class="control-label">Name</label>
                                            <input type="text" class="form-control boxed" id="updateLName"> </div>
                                       
                                         <button type="submit" id="updateLocationBtn" class="btn btn-default" style="display:none;">Create</button>
                                    </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
          <button onClick="goTO3()" class="btn btn-info">Update</button>
        </div>
      </div>
      
    </div>
  </div>
  
  
  
  
  
   <div class="modal fade" id="myModal4" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title center">Update a  pick up location</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <form id="updatePLocationForm">
                                        <div class="form-group">
                                            <label class="control-label">Name</label>
                                            <input type="text" class="form-control boxed" id="updatePLName"> </div>
                                       
                                         <button type="submit" id="updatePLocationBtn" class="btn btn-default" style="display:none;">Create</button>
                                    </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
          <button onClick="goTO4()" class="btn btn-info">Update</button>
        </div>
      </div>
      
    </div>
  </div>
  
  
  
  
  
  
                    </div>
 					<section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card sameheight-item">
                                    <div class="card-block">
                                        
                                        <section class="section" id='allLocation'>
                                            
                                            
                                            
                                        </section>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    </article>
</body>
<script>
var l;
var llid;
var plid;
var p_location_data;
var location_data;

load = function(){
	
	$.ajax({
		url:'getAllLocations',
		type:'GET',
		success: function(response){
			console.log(response);
			locations = response.locations;
			p_locations = response.p_locations;
    		var count=0;
    		var rrow=1;
    		if(locations.length<3)
    			count=3;
    		else count = locations.length;
    		for (var j=0;j<=parseInt(count/3);j++)
    			{
    			var row = '<div class="row" id="row'+(parseInt(j)+1)+'"></div>';
    			$("#allLocation").append(row);
    			}
    		for(var i=0;i<locations.length;i++)
    			{
    			var card = '<div class="col-md-4">'+
					'<div class="box-placeholder"><h3>'+locations[i].name+'</h3><a data-toggle="modal" data-target="#myModal3" onClick="assignValue2('+locations[i].id+')">Edit</a><a href="javascript:delLocation('+locations[i].id+')\">Delete</a><br>'+
					'<a data-toggle="modal" data-target="#myModal2" onClick="assignValue('+locations[i].id+')">Create a pick up location</a>'+'<div id="'+locations[i].id+'"></div>'+
				 '</div>';
    			
				if(i<3)
					$("#row1").append(card);
				else
					$("#row"+(parseInt(i/3)+1)).append(card);
					
    			}
    		for (var k=0;k<p_locations.length;k++)
    			{
    			var p_card = '<a data-toggle="modal" data-target="#myModal4" onClick="assignValue3('+p_locations[k].id+')">'+p_locations[k].name+'</a><a href="javascript:delPickUp('+p_locations[k].id+')\">Delete</a><br>';
    			$("#" + p_locations[k].location_id).append(p_card);
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
	$('#name').val("");
}
goTO2 = function(){
	$('#bpsubmit').trigger('click');
	$('#pname').val("");
}
goTO3 = function(){	
	$('#updateLocationBtn').trigger('click');
}
goTO4 = function(){	
	$('#updatePLocationBtn').trigger('click');
}

 assignValue = function (a){
	l=a; 
}
 assignValue2 = function (a){
		llid=a;
		$.ajax({
    		url:'getLocationById',
    		type:'GET',
    		data:{	id:a   },
    		traditional: true,			
    		success: function(response){
    				location_data  = response.location;
    				$("#updateLName").val(location_data.name);
    				},
    		error: function(err){
    				console.log(JSON.stringify(err));
    				
    				}
    		
    			});	
		
	}
 assignValue3 = function (a){
		plid=a;
		$.ajax({
 		url:'getPickUpLocationById',
 		type:'GET',
 		data:{	id:a   },
 		traditional: true,			
 		success: function(response){
 				p_location_data  = response.p_location;
 				$("#updatePLName").val(p_location_data.name);
 				},
 		error: function(err){
 				console.log(JSON.stringify(err));
 				
 				}
 		
 			});	
		
	}

$(document).ready(function(){
	$("#locationMng").addClass("active");
	$("#myForm").on('submit',function(e){
		console.log("Fired")
		e.preventDefault();
		$.ajax({
    		url:'createLocation',
    		type:'GET',
    		data:{	name:$("#name").val()
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
    				            window.location = "location_management";
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
	
	
	
	$("#myForm2").on('submit',function(e){
		console.log("Fired2")
		e.preventDefault();
		$.ajax({
    		url:'createPickUpLocation',
    		type:'GET',
    		data:{	name:$("#pname").val(),
    				location_id:l
    			},
    		traditional: true,			
    		success: function(response){
    				if(response.status=="1")
    					window.location.reload();
    				//var obj = jQuery.parseJSON(response);
    				else 
     					swal("Oops!", response.message, "error")    
    				
    				},
    		error: function(err){
    				console.log(JSON.stringify(err));
    				
    				}
    		
    			});	
		
	});	
	
	
	$("#updateLocationForm").on('submit',function(e){
		e.preventDefault();
		$.ajax({
    		url:'updateLocation',
    		type:'GET',
    		data:{	id:llid,
    				name:$("#updateLName").val()
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
    				            window.location = "location_management";
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
	
	
	

	$("#updatePLocationForm").on('submit',function(e){
		e.preventDefault();
		$.ajax({
    		url:'updatePickUpLocation',
    		type:'GET',
    		data:{	id:plid,
    				name:$("#updatePLName").val()
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
    				            window.location = "location_management";
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



delLocation=function(id)
{
swal({
    title: "Do you want to delete this location?",
    text: "All information related to this location will be deleted!",
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
    	     url:'deleteLocation',
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
    	                 window.location = "location_management";
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
    
    
delPickUp=function(id)
{
swal({
    title: "Do you want to delete this pick up location?",
    text: "All information related to this pick up location will be deleted!",
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
    	     url:'deletePickUpLocation',
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
    	                 window.location = "location_management";
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