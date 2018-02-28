<body onload="load()">
<article class="content cards-page">
                    	
                    <section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Booking Request Information </h3> <br>
                                     
                                        
                      
                                    </div>
                                    
                                    
                                    
                                   
                                    
                                    <form class="form-inline" id ="myForm">
                                    	<div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Name</label>
                                            <input type="text" class="form-control" id="uname" style="width: inherit;"> 
                                        </div>
                                        
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">From</label>
                                            <input type="text" class="form-control" id="from" style="width: inherit;"> 
                                        </div>
                                        
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">To</label>
                                            <input type="text" class="form-control" id="to" style="width: inherit;"> 
                                        </div>
                                        
                               			<div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Departure Date</label>
                                            <input type="text" class="form-control" id="dept_date" style="width: inherit;"> 
                                        </div>
                                        
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Departure Time</label>
                                            <input type="text" class="form-control" id="dept_time" style="width: inherit;"> 
                                        </div>
                                        
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Requested On</label>
                                            <input type="text" class="form-control" id="requestedOn" style="width: inherit;"> 
                                        </div>
                                        
                                        
                                        
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Number of bookings</label>
                                            <input type="text" class="form-control" id="no_booking" style="width: inherit;"> 
                                        </div>
                                       
                                        
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Status</label>
                                            <input type="text" class="form-control" id="status" style="width: inherit;"> 
                                        </div>
                                        
                                        <button type="submit" id ="bsubmit" style="display:none;">aa</button>
                                        
                                        
                                        
                               
                                       
                                        
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
	var request = data.request;
	var locations = data.locations;
	id= parseInt(request.id);
	console.log(data)
	$("#uname").val(request.user_id);
	$("#from").val(searchLocation(request.source_id,locations));
	$("#to").val(searchLocation(request.destination_id,locations));
	$("#dept_date").val(formatDate(request.dept_date));
	$("#dept_time").val(request.dept_time);
	$("#requestedOn").val(formatDate(request.created_at));
	$("#no_booking").val(request.number_of_booking);
	$("#description").val(request.description);
	$("#status").val(request.status);
	
	
	
	$("input").prop('disabled', true);
}

$(document).ready(function(){
	$("#bookingRequestMng").addClass("active");
	
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





	
</script>
