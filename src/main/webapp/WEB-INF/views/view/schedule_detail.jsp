<body onload="load()">
<article class="content cards-page">
                    	
                    <section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Schedule Detail </h3>
                                    </div>
                                    <form class="form-inline">
                                       <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Code</label>
                                            <input type="text" class="form-control" id="scode" placeholder=Code> </div>
                                       <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Driver</label>
                                            <input type="text" class="form-control" id="sdriver" placeholder="Driver"> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Bus</label>
                                            <input type="text" class="form-control" id="sbus" placeholder="Bus"> </div>                                     
										<div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">From</label>
                                            <input type="text" class="form-control" id="sfrom" placeholder="From"> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">To</label>
                                            <input type="text" class="form-control" id="sto" placeholder="To"> </div>     
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Number of booking</label>
                                            <input type="text" class="form-control" id="snumberbooking" placeholder="Number of booking"> </div>                                       
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputEmail3"  style="margin-right:4%;">Departure Date</label>
                                            <input type="text" class="form-control" id="sdeptdate" placeholder="Departure Date"> </div>
                                        <div class="form-group col-md-4" style="margin-bottom:2%;">
                                            <label for="exampleInputPassword3"  style="margin-right:4%;">Departure Time</label>
                                            <input type="text" class="form-control" id="sdepttime" placeholder="Departure Time"> </div>
                                        
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
                                  
                            </div>
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
	var bookings = data.bookings;
	var buses  = data.buses;
	var locations = data.locations;
	var schedule  = data.schedule;
	$("#scode").val(schedule.id);
	$("#sdriver").val(schedule.driver_id);
	$("#sbus").val(searchBus(schedule.bus_id,buses));
	$("#sfrom").val(searchLocation(schedule.source_id,locations));
	$("#sto").val(searchLocation(schedule.destination_id,locations));
	$("#snumberbooking").val(schedule.number_booking);
	$("#sdeptdate").val(formatDate(schedule.dept_date));
	$("#sdepttime").val(schedule.dept_time);
	for (var i=0;i<bookings.length;i++)
	{
	var booking = '<tr class="hoverr" data-url="booking_detail?id='+bookings[i].id+'"><td>'+(i+1)+'</td>'
						+'<td>'+bookings[i].user_id+'</td>'
						+'<td>'+bookings[i].number_booking+'</td></tr>';
	$("#allBooking").append(booking);				
	}
	$(".hoverr").on('click', function() {
    	location.href=$(this).attr('data-url');
	});
}



$(document).ready(function(){
	$("#scheduleMng").addClass("active");
	$("#myForm").on('submit',function(e){
		e.preventDefault();
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

function searchBus(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].model;
        }
    }
}

	
</script>
