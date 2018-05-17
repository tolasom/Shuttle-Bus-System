<body onload="load()">
<article class="content cards-page">
					<div class="title-block">
                         <div class="clearfix" style="margin-bottom: 10px;">
                         	<div class="pull-left">
                         		<h3 class="title"> Bookings </h3>
                         		<p class="title-description"> All current and future bookings </p> 
                         	</div>
                         	<div class="btn-group pull-right menulist">
		                        <button type="button" class="btn btn-info" id="allbtn" style="color:white;border-right:2px solid white;">All</button>
		                        <button type="button" class="btn btn-info" id="customerbtn" style="color:white; border-right:2px solid white;"><i class="fa fa-users"></i></button>
		                        <button type="button" class="btn btn-info"  id="studentbtn" style="color:white;"><i class="fa fa-graduation-cap"></i></button>
		                    </div>
                         	
                         </div>
                         
                       
	                         <div style="margin-bottom: 10px;">
		                         <button type="button" class="btn btn-pill-right btn-info pull-right" style="color:white;" onclick="location.href='historical_booking';">View all historical bookings <i class="fa fa-angle-right"></i></button>
		                          <button class="btn btn-warning pull-left" onClick="openModal()" style="color:white;" id="moveBtn">Assign Schedule <i class="fa fa-exchange"></i></button>
		                         
	                      	 </div>
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
                                            <h3 class="title"> Current and future bookings </h3>
                                            
                                        </div>

                                        <section class="example">
                                            <div class="table-responsive">
                                                <table class="table table-striped table-bordered table-hover">
                                                    <thead>
                                                        <tr>
                                                        	<th></th>
                                                            <th>No</th>
                                                            <th>Code</th>
                                                            <th>Name</th>
                                                            <th>From</th>
                                                            <th>To</th>
                                                            <th>Departure Date</th>
                                                            <th>Departure Time</th>
                                                            <th>User Type</th>
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
                <div id="user_info_modal" class="modal">
                    <div class="modal-content center">
                        <h6 class="center light-blue-text">User Information</h6>
                        <table id="get_user_info"></table>
                    </div>
      </div>
</body>

<script>
load = function(){	
	 $("#allbtn").addClass("active");
	$.ajax({
		url:'getAllCurrentBookings',
		type:'GET',
		success: function(response){
			console.log(response)
			bookings = response.bookings;
			locations = response.locations;
			customers = response.customers;
			console.log(locations)
			for (var i=0;i<bookings.length;i++)
				{
				var color="";
				var cb="<td></td>";
				if(bookings[i].notification=="Unassigned")
					{
						color = "background-color: coral;";
						cb = '<td class="unhoverr"><label class="item-check" id="select-all-items"><input type="checkbox" class="checkbox">'
    					+'<span></span></label></td>';
    				}
				var booking = '<tr class="hoverr search '+bookings[i].description+'" style="'+color+'"s-title="'+bookings[i].code+'" data-url="booking_detail?id='+bookings[i].id+'">'
									+cb
									+'<td>'+(i+1)+'</td>'
									+'<td>'+bookings[i].code+'</td>'
									+'<td class="user_info" style="color:blue" data='+bookings[i].user_id+'>'+searchCustomer(bookings[i].user_id,customers)+'</td>'
									+'<td>'+searchLocation(bookings[i].from_id,locations)+'</td>'
									+'<td>'+searchLocation(bookings[i].to_id,locations)+'</td>'
									+'<td>'+formatDate(bookings[i].dept_date)+'</td>'
									+'<td>'+bookings[i].dept_time+'</td>'
									+'<td>'+bookings[i].description+'</td></tr>';
				$("#allBooking").append(booking);				
				}
			$(".hoverr").on('click', function() {
    	    	location.href=$(this).attr('data-url');
    		});
    		$( ".unhoverr" ).on('click', function(e) {
				e.stopPropagation();	
			});
    		$( ".user_info" ).on('click', function(e) {
                 console.log("KK");
                 e.stopPropagation();
                 var id=$(this).attr('data');
                 console.log(id);
                 $.ajax({
                        async: false,
                        cache: false,
                        type: "GET",
                        url: "get_sch_driver_info2",
                        data :{'id':id},
                        timeout: 100000,
                        success: function(data) {
                            console.log(data);
                            if(data[0].phone_number==""||data[0].phone_number==null)
                            	data[0].phone_number = "";
                            var data='<tr><th>User\'s Name</th><td><b>:</b>  &nbsp&nbsp '+data[0].name+'</td></tr>'
                              +'<tr><th>Phone Number</th><td><b>:</b>  &nbsp&nbsp '+ data[0].phone_number+'</td></tr>'
                              +'<tr><th>Email</th><td><b>:</b> &nbsp&nbsp '+data[0].email +'</td></tr>'
                              document.getElementById('get_user_info').innerHTML=data;
                            $('#user_info_modal').modal();
                            $('#user_info_modal').modal('open');
                            
                        },
                        error: function(e) {
                            console.log("ERROR: ", e);
                        },
                        done: function(e) {
                            console.log("DONE");
                        }
                    });
             });
    	},
	error: function(err){
		swal("Oops!", "Cannot get all buses data", "error")
		console.log(JSON.stringify(err));
		}
		
	});
	
	$(".checkbox").on('click', function(e) {
		var a = $(this).parents(".hoverr")[0];
		$(a).toggleClass("selected");
		showMoveBtn(a);
	});
	
$("#moveBtn").hide();
	


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

	$("#customerbtn").on('click', function(e) {
		   
		  $("#allbtn").removeClass("active");
		  $("#studentbtn").removeClass("active");
		  $("#customerbtn").addClass("active");

		  $(".customer").each(function(){
		    	 $(this).show();
		  });

		  $(".student").each(function(){
		    	 $(this).show();
		  });
		  $(".student").each(function(){
		    	 $(this).hide();
		  });
		  
	});

	$("#studentbtn").on('click', function(e) {
		   
		  $("#customerbtn").removeClass("active");
		  $("#allbtn").removeClass("active");
		  $("#studentbtn").addClass("active");
		   
		  $(".customer").each(function(){
		    	 $(this).show();
		  });
		  $(".student").each(function(){
		    	 $(this).show();
		  });
		  $(".customer").each(function(){
		    	 $(this).hide();
		  });
		  
	});

	$("#allbtn").on('click', function(e) {
			
		  $("#customerbtn").removeClass("active");
		  $("#studentbtn").removeClass("active");
		  $("#allbtn").addClass("active");
		   
		  $(".customer").each(function(){
		    	 $(this).show();
		  });
		  $(".student").each(function(){
		    	 $(this).show();
		  });
		  
	});

});



function openModal(){
	$('#moveModal').modal('toggle');
	$('#mschedule').children('option:not(:first)').remove();
	for(var i=0; i<all_schedule.length; i++)					
		{
		if(all_schedule[i].id!=idd)
			{
			var s  = "<option value="+all_schedule[i].id+">"+all_schedule[i].code+"</option>";
			$("#mschedule").append(s);
			}
		}
	$("#moveSimple").hide();
	$("#moveNew").show();

}


function showMoveBtn(e){
	
	var numItems = $('.selected').length;
	if(numItems>0)
		$("#moveBtn").show();
	else 
		$("#moveBtn").hide();
	
}


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