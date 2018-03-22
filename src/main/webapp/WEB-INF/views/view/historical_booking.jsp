<body onload="load()">
<article class="content cards-page">
                    <div class="title-block">
                        <h3 class="title"> Bookings </h3>
                         <p class="title-description"> All historical bookings </p> <br>
                         <button type="button" class="btn btn-pill-left btn-info pull-left" style="color:white;" onclick="location.href='admin_booking';"><i class="fa fa-angle-left"></i> View all current and future bookings </button>
                    </div>
                    
                    <div style="margin-bottom: 10px;">
	                      	     <input type="text" class="form-control" placeholder="Search for any booking by code..." id="txtbox"/>
	                    	 </div>	
                    <section class="section">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-block">
                                        <div class="card-title-block">
                                            <h3 class="title"> Historical bookings </h3>
                                            
                                        </div>
                                        <section class="example">
                                            <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>No</th>
                                                            <th>Code</th>
                                                            <th>Name</th>
                                                            <th>From</th>
                                                            <th>To</th>
                                                            <th>Departure Date</th>
                                                            <th>Departure Time</th>
                                                            <th>Number of bookings</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="allBooking">
                                                        
                                                    </tbody>
                                                </table>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </article>
                
</body>
<script>
load = function(){	
	$.ajax({
		url:'getAllHistoricalBookings',
		type:'GET',
		success: function(response){
			console.log(response)
			bookings = response.bookings;
			locations = response.locations;
			customers = response.customers;
			
			console.log(locations)
			for (var i=0;i<bookings.length;i++)
				{
				var booking = '<tr class="hoverr search" s-title="'+bookings[i].code+'" data-url="booking_detail?id='+bookings[i].id+'"><td>'+(i+1)+'</td>'
				+'<td>'+bookings[i].code+'</td>'
				+'<td>'+searchCustomer(bookings[i].user_id,customers)+'</td>'
				+'<td>'+searchLocation(bookings[i].from_id,locations)+'</td>'
				+'<td>'+searchLocation(bookings[i].to_id,locations)+'</td>'
				+'<td>'+formatDate(bookings[i].dept_date)+'</td>'
				+'<td>'+bookings[i].dept_time+'</td>'
				+'<td>'+bookings[i].number_booking+'</td></tr>';
				$("#allBooking").append(booking);				
				}
			$(".hoverr").on('click', function() {
    	    	location.href=$(this).attr('data-url');
    		});
    	},
	error: function(err){
		swal("Oops!", "Cannot get all buses data", "error")
		console.log(JSON.stringify(err));
		}
		
	});
	
}

$(document).ready(function(){
	$("#bookingMng").addClass("active");
	$("#txtbox").keyup(function(){
		   
		   
		   $(".search").each(function(){
	 	         
		          $(this).show();
		         
		        });
		     var searchValue = $("#txtbox").val().toLowerCase();
		     	if(searchValue!=null&&searchValue!="")
		     		{
		     		 $(".search").each(function(){
		    	         var title = $(this).attr('s-title'); 
		    	         title = title.toLowerCase();
		    	         var check = title.search(searchValue);
		    	         if(check==-1)
		    	         {
		    	          $(this).hide();
		    	         }
		    	        });
		     		}
		     	else{
		       
		        $(".search").each(function(){
		   	         
		   	          $(this).show();
		   	         
		   	        });
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

    return [month, day, year].join('-');
};

function searchLocation(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].name;
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

</script>