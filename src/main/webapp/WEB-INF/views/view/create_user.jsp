<body>
<article class="content cards-page">
                    	
<section class="section">
    <div class="row sameheight-container">
        <div class="col-md-12">
            <div class="card card-block sameheight-item">
                <div class="title-block">
                    <h3 class="title"> Create User </h3>
                </div>
                <form class="form-inline" id="myForm">
                   <div class="form-group col-md-4" style="margin-bottom:2%;">
                        <label for="exampleInputPassword3"  style="margin-right:4%;">Username</label>
                        <input type="text" class="form-control" style="width: inherit;" id="uusername" placeholder="Username" required> </div>
                    <div class="form-group col-md-4" style="margin-bottom:2%;">
                        <label for="exampleInputPassword3"  style="margin-right:4%;">Email</label>
                        <input type="text" class="form-control" style="width: inherit;" id="uemail" placeholder=Email required> </div>
                    <div class="form-group col-md-4" style="margin-bottom:2%;">
                        <label for="exampleInputPassword3"  style="margin-right:4%;">Password</label>
                        <input type="text" class="form-control" style="width: inherit;" id="upassword" placeholder=Password required> </div>
                    <div class="form-group col-md-4" style="margin-bottom:2%;">
                        <label for="exampleInputPassword3"  style="margin-right:4%;">Confirm Password</label>
                        <input type="text" class="form-control" style="width: inherit;" id="uconfirmpassword" placeholder="Confirm Password" required> </div>
                    <div class="form-group col-md-4" style="margin-bottom:2%;">
                        <label for="exampleInputPassword3"  style="margin-right:4%;">Phone Number</label>
                        <input type="text" class="form-control" style="width: inherit;" id="uphonenumber" placeholder="Phone Number" required> </div>
                   
                    <div class="form-group col-md-4" style="margin-bottom:2%;">
                        <label for="exampleInputEmail3"  style="margin-right:4%;">User Type</label>
                        <select class="form-control" style="width: inherit;" id="uusertype" required>
                            <option>Admin</option>
                            <option>Driver</option>
                        </select></div>

                    <div class="form-group col-md-12" style="margin-bottom:2%;">
						<button type="submit" class="btn btn-info">Create</button>
						</div>
                </form>
  <!--                  <div class="col-xl-12">
                <div class="card-block" style="padding-left:2px;">
                    <ul class="nav nav-tabs nav-tabs-bordered">
                        <li class="nav-item">
                            <a href="#home" style="background-color:#52BCD3" class="nav-link active" data-target="#home" data-toggle="tab" aria-controls="home" role="tab">All bookings of this schedule</a>
                        </li>
                    
                    </ul>
                    
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
              
        </div>  -->
            </div>
        </div>
    </div>
</section>
                    
</article>
                
</body>
<script type="text/javascript">

$(document).ready(function(){
	$("#userMng").addClass("active");
    $(".ir").slideToggle();
                        $("#dds1").toggleClass("irr");
                        $("#dds2").toggleClass("irr");
	$( "#settingMng" ).off();
    $("[name=date]").keydown(function (event) {
            event.preventDefault();
        });
    $("[name=time]").keydown(function (event) { 
            event.preventDefault();
        });

	
	
	$("#myForm").on('submit',function(e){
		e.preventDefault();
		if($("#sdriver").val()==""||$("#sdriver").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Driver field blank!", "error")
		return
		}
		if($("#sbus").val()==""||$("#sbus").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Bus field blank!", "error")
		return
		}
		if($("#sfrom").val()==""||$("#sfrom").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave From field blank!", "error")
		return
		}
		if($("#sto").val()==""||$("#sto").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave To field blank!", "error")
		return
		}
		if($("#sdeptdate").val()==""||$("#sdeptdate").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Departure Date field blank!", "error")
		return
		}
		if($("#sdepttime").val()==""||$("#sdepttime").val()==null)
		{
		swal("Action Disallowed!", "You cannot leave Departure Time blank!", "error")
		return
		}
		$.ajax({
    		url:'createUserr',
    		type:'GET',
    		data:{	username:$("#scode").val(),
    				email:$("#sdriver").val(),
    				password:$("#sbus").val(),
    				phone_number:$("#sfrom").val(),
    				profile:$("#sto").val()
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
    				            window.location = "current_schedule";
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


formatDate =function (date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
      if (day.length < 2) day = '0' + day;

    return [month, day, year].join('-');
};


	
</script>
