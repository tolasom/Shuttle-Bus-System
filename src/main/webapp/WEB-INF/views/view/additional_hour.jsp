<body onload="load()">
<div class="row " id="margin-body">
<div id="tablewrapper" >
<table id="customers" style="width:50%; width:50%;margin: 0 AUTO;">
  <tr>
    <th>No</th>
    <th>Name</th>
    <th>Additional Hour</th>
  </tr>
</table>
</div>
</div>
<div id ="el" style="display: inline-block;margin:1cm 1cm 0cm 16cm;">
<button data-toggle="modal" data-target="#myModal" id="id1" style="display:none;"></button>
<a onclick="create()" id ="create" href="#" class="btn btn-info btn-md center" style="color:white;width:80px;background-color:#ccc;border-color:#ccc">
          <span class="glyphicon glyphicon-plus"></span>Create</a>
<a onclick="edit()" id ="edit" href="#" class="btn btn-info btn-md center" style="color:white;width:80px;background-color:#ccc;border-color:#ccc">
          <span class="glyphicon glyphicon-edit"></span>Edit</a>
<a onclick="parent.history.go(-1)" class="btn btn-info btn-md center" id="goBack1" style="margin-left:15px;width:110px;background-color:#4CAF50;">
          <span class="glyphicon glyphicon-chevron-left"></span> Go Back
        </a>


<a onclick="update()" id ="update" href="#" class="btn btn-info btn-md center" style="color:white;width:80px;background-color:#ccc;border-color:#ccc">
          <span class="glyphicon glyphicon-check"></span>Update</a>
          <a onclick="parent.history.go(-2)" class="btn btn-info btn-md center" id="goBack2" style="margin-left:15px;width:110px;background-color:#4CAF50;">
          <span class="glyphicon glyphicon-chevron-left"></span> Go Back
        </a>
          </div>
          
          
          
          
          <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Create student's additional hour record</h4>
        </div>
        <div class="modal-body">
     <form class="form-group" id="myForm2">
    <div>
      <label class="col-sm-4" for="email">Student</label>
      <div class="col-sm-7">
      <select class="form-control" id="sstudent">
	                                    
	  </select>
      
      </div><br><br>
      <label class="col-sm-4" for="email">Additional Hour</label>
      <div class="col-sm-7">
      <input type="text" class="form-control cdd" id="shour" maxlength="3" placeholder="Input only integer" required>
      </div>	
      <button id="bsubmit" type="submit" class="btn btn-default" style="display:none;">Create</button>
      
    </div>
  </form>
  <br>
        </div>
        <div class="modal-footer">
          <button onClick="goTO()" class="btn btn-default" id="bbtnn">Create</button>
          <button type="button" id="closing" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>	
  
  
<script type="text/javascript">


goTO = function(){
	$('#bsubmit').trigger('click');
}


load = function()
{
	$("#update").hide();
	$("#goBack2").hide();
	var dt = ${message};
	var data = dt.hours;
	d=dt;			
	if(isEmpty(data))
		$("#edit").hide();
	else
		$("#update").attr("project_id",data[0].project_id);
//	$("#update").attr("user_id",data.)
	var sum=0;
	for(i=0;i<data.length;i++)
	{
		sum += parseFloat(data[i].kit_point);
		var row = "<tr class='record'><td>"+(i+1)+"</td>"+
		"<td class='member' id='"+data[i].user_id+"'>"+data[i].name+"</td>"+
		"<td><input type='text' readonly class='cc' maxlength='3' style='border: none;border-color: transparent;' id='idd' value="+data[i].additional_hour+"></td></tr>";
		$("#customers").append(row);
		
	}
	$('.cdd').keypress(function(evt){
    	var event = evt || window.event;
   	  	var keyentered = event.keyCode || event.which;
   	  	var keyentered = String.fromCharCode(keyentered);

   	  	var regex = /[0-9]/;
   	  	//var regex2 = /^[a-zA-Z.,;:|\\\/~!@#$%^&*_-{}\[\]()`"'<>?\s]+$/;

   	  	if(!regex.test(keyentered)) {
   	    	event.returnValue = false;
   	    	if(event.preventDefault) event.preventDefault();
   	  	}
    });
}

function goBack() {
    window.history.back();
}
function edit(){
	$( "input" ).removeAttr( "readonly" );
	$ (".cc").removeAttr("style");
	$("#el").css('margin-left',19+'cm');
	$("#edit").hide();
	$("#create").hide();
	$("#update").show();
	$("#goBack2").show();
	$("#goBack1").hide();
}







function create(){
	$("#sstudent option").remove();
	$('#id1').trigger('click');
	var members = d.members;
	var hours = d.hours;
	if(members.length==hours.length)
		{
		$("#sstudent").append('<option value="" selected>You already had all members\'s record</option>');
		$("#sstudent").prop('disabled', true);
		$("#sstudent").css('background-color','#CC3333');
		$("#sstudent").css('color','black');
		$("#bbtnn").hide();
		}
	else
		$("#bbtnn").attr("value",members[0].project_id);
		
	for (i=0;i<members.length;i++)
		{
			var count = 0;
			for (j=0;j<hours.length;j++)
				{
					if(members[i].user_id==hours[j].user_id)
						{
						continue;
						}
					else
						count++;
				}
			if(count==hours.length)
				{
				$("#sstudent").append("<option value="+members[i].user_id+">"+members[i].user_name+" </option>");
				}
		}
	
	
}
function update(){
	var project_id = d.hours[0].project_id;
	var allHour="";	
	
	$('#customers .record').each(function() {
	    var user_id = $(this).find(".member").attr("id");    
	    var hour = $(this).find(".cc").val();
	    if(hour=="" || hour==null)
	    	hour=0;
	    console.log(hour)	
	    allHour +=user_id+","+hour+"/";
	    });
	allHour = allHour.substring(0, allHour.length-1);
	console.log(allHour)
	$.ajax({
		url:'additional_hour_update',
		type:'GET',
		data:{		project_id:project_id,
					name:allHour,},
		traditional: true,			
		success: function(response){
				if(response.status=="200")
					{setTimeout(function() {
				        swal({
				            title: "Done!",
				            text: "You have updated it successfully!",
				            type: "success"
				        }, function() {
				        	parent.history.go(-2)
				        });
				    }, 10);}
				else 
					swal("Oops!", response,message, "error")
					
			},
		error: function(err){
				console.log(JSON.stringify(err));
				
				}
		
			});	
	
}


function isEmpty(obj) {
    for(var key in obj) {
        if(obj.hasOwnProperty(key))
            return false;
    }
    return true;
}	




$(document).ready(function() {
    $('li#projectStlye').addClass('active');
    
    
    $("#myForm2").on("submit",function(e){
		e.preventDefault();
		if(getlength($("#shour").val())>3)
		{
			swal("Oops!", "Hour Cannot Be More Than 3 Digits", "error")  
			return
		}
		$.ajax({
    		url:'additional_hour_submit',
    		type:'GET',
    		data:{		user_id:$("#sstudent").val(),
    					project_id:$("#bbtnn").val(),
    					additional_hour:$("#shour").val(),},
    		traditional: true,			
    		success: function(response){
    				if(response.status=="200")
    					{
    					window.location.reload()
    					}
    				
    				else 
     					swal("Oops!", "Project Name already existed!", "error")    
    				
    				},
    		error: function(err){
    				console.log(JSON.stringify(err));
    				
    				}
    		
    			});	
				
	});
    
    
    $('.cc').keypress(function(evt){
    	var event = evt || window.event;
   	  	var keyentered = event.keyCode || event.which;
   	  	var keyentered = String.fromCharCode(keyentered);

   	  	var regex = /[0-9]/;
   	  	//var regex2 = /^[a-zA-Z.,;:|\\\/~!@#$%^&*_-{}\[\]()`"'<>?\s]+$/;

   	  	if(!regex.test(keyentered)) {
   	    	event.returnValue = false;
   	    	if(event.preventDefault) event.preventDefault();
   	  	}
    });
    
});
function getlength(number) {
    return number.toString().length;
}
</script>
</body>
