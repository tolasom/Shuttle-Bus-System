<body onload="load()">
<article class="content cards-page">
                    	
                    <section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Bus Information </h3>
                                    </div>
                                    <br><br>
                                    <form class="form-group" id ="myForm">
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Model</label>
                                            <input type="text" class="form-control" id="model">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Plate Number</label>
                                            <input type="text" class="form-control" id="plate_number">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Number of seat</label>
                                            <input type="number" class="form-control" id="number_of_seat">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Description</label>
                                            <textarea rows="4" cols="100" class="form-inline boxed" id="description"></textarea>
                                        </div>
                                        <button class="btn btn-default" id="btnCancel" onclick="parent.history.go(-1)">Cancel</button> 
                                        <button type="submit" class="btn btn-primary" on>Update</button>
                                       
                                        
                                    </form>
                                </div>
                            </div>
                        </div>
                    </section>
                    
                </article>
                
</body>
<script type="text/javascript">
var id;
load = function () {
	var data = ${data};
	var bus = data.bus;
	$("#model").val(bus.model);
	$("#plate_number").val(bus.plate_number);
	$("#number_of_seat").val(bus.number_of_seat);
	$("#description").val(bus.description);
	id = bus.id;
}

$(document).ready(function(){
	$("#busMng").addClass("active");
	$("#myForm").on('submit',function(e){
		e.preventDefault();
		$.ajax({
    		url:'updateBus',
    		type:'GET',
    		data:{	id:id,
    				plate_number:$("#plate_number").val(),
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


	
</script>
