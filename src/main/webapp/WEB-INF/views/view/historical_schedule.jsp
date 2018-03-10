<body onload="load()">
<article class="content cards-page">                   
                    
                    <div class="title-block">
                         <div class="clearfix" style="margin-bottom: 10px;">
                         	<div class="pull-left">
                         		<h3 class="title"> Schedules </h3>
                         		<p class="title-description"> All Historical schedules </p> 
                         	</div>
                         	<div class="btn-group pull-right">
		                        <button type="button" class="btn btn-info" id="btnList" style="color:white;border-right:2px solid white;"><i class="fa fa-list-ul"></i></button>
		                        <button type="button" class="btn btn-info" onclick="window.location.href='schedule'" style="color:white;"><i class="fa fa-calendar"></i></button>
		                    </div>
                         	
                         </div>
                         
                       
	                         <div style="margin-bottom: 10px;">
		                         <button type="button" class="btn btn-pill-left btn-info pull-left" style="color:white;" onclick="location.href='current_schedule';"><i class="fa fa-angle-left"></i> View all current schedules </button>
	                      	 </div>
	                  </div>
	                      	 <div style="margin-bottom: 10px;">
	                      	     <input type="text" class="form-control" placeholder="Search for any schedule by code..." id="txtbox"/>
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
                                                            <th>Code</th>
                                                            <th>Bus</th>
                                                            <th>Driver</th>
                                                            <th>From</th>
                                                            <th>To</th>
                                                            <th>Departure Date</th>
                                                            <th>Departure Time</th>
                                                            <th>Number of bookings</th>
                                                            <th></th>
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
			var schedule = '<tr class="hoverr search"  s-title="'+schedules[i].code+'" data-url="schedule_detail?id='+schedules[i].id+'"><td>'+(i+1)+'</td>'
								+'<td>'+schedules[i].code+'</td>'
								+'<td>'+searchBus(schedules[i].bus_id,buses)+'</td>'
								+'<td>'+schedules[i].driver_id+'</td>'
								+'<td>'+searchLocation(schedules[i].from_id,locations)+'</td>'
								+'<td>'+searchLocation(schedules[i].to_id,locations)+'</td>'
								+'<td>'+formatDate(schedules[i].dept_date)+'</td>'
								+'<td>'+schedules[i].dept_time+'</td>'
								+'<td>'+schedules[i].number_booking+'</td>'
								+'<td class="unhoverr" data-url="'+schedules[i].id+'" style="color:#e85a71"><i class="fa fa-trash"></i></td></tr>';
			$("#allSchedules").append(schedule);				
			}
			$( ".unhoverr" ).on('click', function(e) {
				e.stopPropagation();	
				var s_id = parseInt($(this).attr('data-url'));
				deleteSchedule(s_id);
			});
			$(".hoverr").on('click', function(e) {
				e.stopPropagation();
		    	location.href=$(this).attr('data-url');
			});
    	},
	error: function(err){
		swal("Oops!", "Cannot get all schedules data", "error")
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

function searchBus(id, myArray){
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].id === id) {
            return myArray[i].model;
        }
    }
}



deleteSchedule=function(s_id)
{
swal({
    title: "Do you want to delete this schedule?",
    text: "Make sure there is no booking in this schedule.",
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
    			url:'deleteSchedule?id='+s_id,
    			type:'GET',
    			success: function(response){
    				if(response.status=="1")
    	  	      {
    	  	      setTimeout(function() {
    	  	             swal({
    	  	                 title: "Done!",
    	  	                 text: response.message,
    	  	                 type: "success"
    	  	             }, function() {
    	  	            	window.location.reload();
    	  	             });
    	  	         }, 10);

    	  	      }

    	  	          else
    	  	           {
    	  	           swal("Oops!",response.message, "error")

    	  	           } 		
    			},
    			error: function(err){
    			swal("Oops!", "Cannot get all buses data", "error")
    			console.log(JSON.stringify(err));
    			}
    		});
      } 
    });
    }

</script>