<body onload="load()">
<article class="content cards-page">
                    	
                    <section class="section">
                        <div class="row sameheight-container">
                            <div class="col-md-12">
                                <div class="card card-block sameheight-item">
                                    <div class="title-block">
                                        <h3 class="title"> Booking Information </h3>
                                    </div>
                                    <br><br>
                                    <form class="form-group" id ="myForm">
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Name</label>
                                            <input type="text" class="form-control" id="uname">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">From</label>
                                            <input type="text" class="form-control" id="from">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">To</label>
                                            <input type="text" class="form-control" id="to">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Departure Date</label>
                                            <input type="text" class="form-control" id="dept_date">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Departure Time</label>
                                            <input type="text" class="form-control" id="dept_time">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Booked On</label>
                                            <input type="text" class="form-control" id="bookedOn">
                                        </div>
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Number of bookings</label>
                                            <input type="text" class="form-control" id="no_booking">
                                        </div>
                                        
                                        <div class="form-group">
                                            <label for="exampleInputEmail3">Description</label>
                                            <textarea rows="4" cols="100" class="form-inline boxed" id="description"></textarea>
                                        </div>
                                        <button class="btn btn-default" id="btnCancel" onclick="parent.history.go(-1)"><i class="fa fa-angle-left"></i><b>Back</b></button> 
                               
                                       
                                        
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
	var booking = data.booking;
	var locations = data.locations;
	console.log(data)
	$("#uname").val(booking.user_id);
	$("#from").val(searchLocation(booking.source_id,locations));
	$("#to").val(searchLocation(booking.destination_id,locations));
	$("#dept_date").val(formatDate(booking.dept_date));
	$("#dept_time").val(booking.dept_time);
	$("#bookedOn").val(formatDate(booking.booking_date));
	$("#no_booking").val(booking.number_booking);
	$("#description").val(booking.description);
	
	
	
	$("input").prop('disabled', true);
}

$(document).ready(function(){
	$("#bookingMng").addClass("active");
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

	
</script>
