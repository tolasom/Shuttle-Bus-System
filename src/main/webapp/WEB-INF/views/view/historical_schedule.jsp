<body onload="load()">
<article class="content cards-page">
                    <div class="title-block">
                        <h3 class="title"> Schedules </h3>
                         <p class="title-description"> View all schedules </p> 
                         <div class="btn-group pull-right">
	                        <button type="button" class="btn btn-info" id="btnList" style="color:white;border-right:2px solid white;"><i class="fa fa-list-ul"></i></button>
	                        <button type="button" class="btn btn-info" onclick="window.location.href='schedule'" style="color:white;"><i class="fa fa-calendar"></i></button>
                         </div>
                         <button type="button" class="btn btn-pill-left btn-info pull-left" style="color:white;" onclick="location.href='current_schedule';"><i class="fa fa-angle-left"></i> View all current schedules </button>
                    </div>
                    <section class="section">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-block">
                                        <div class="card-title-block">
                                            <h3 class="title"> Historical schedules </h3>
                                            
                                        </div>
                                        <section class="example">
                                            <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>No</th>
                                                            <th>Bus</th>
                                                            <th>Driver</th>
                                                            <th>From</th>
                                                            <th>To</th>
                                                            <th>Departure Date</th>
                                                            <th>Departure Time</th>
                                                            <th>Number of bookings</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="allSchedules">
                                                       
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
		url:'getAllHistoricalSchedules',
		type:'GET',
		success: function(response){
			console.log(response)
			var schedules = response.schedules;
			var locations = response.locations;
			var buses = response.buses;
			for (var i=0;i<schedules.length;i++)
			{
			var schedule = '<tr class="hoverr" data-url="schedule_detail?id='+schedules[i].id+'"><td>'+(i+1)+'</td>'
								+'<td>'+searchBus(schedules[i].bus_id,buses)+'</td>'
								+'<td>'+schedules[i].driver_id+'</td>'
								+'<td>'+searchLocation(schedules[i].source_id,locations)+'</td>'
								+'<td>'+searchLocation(schedules[i].destination_id,locations)+'</td>'
								+'<td>'+formatDate(schedules[i].dept_date)+'</td>'
								+'<td>'+schedules[i].dept_time+'</td>'
								+'<td>'+schedules[i].number_booking+'</td></tr>';
			$("#allSchedules").append(schedule);				
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


goTO = function(){
	$('#bsubmit').trigger('click');
}


$(document).ready(function(){
	$("#scheduleMng").addClass("active");
	$("#btnList").addClass("active");
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