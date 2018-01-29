package com.MainController;


import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlrpc.XmlRpcException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.DaoClasses.valuePerHourDaoImpl;
import com.EntityClasses.Batch_Master;
import com.EntityClasses.KIT_Point_Student_Wise;
import com.EntityClasses.Project_Category_Master;
import com.EntityClasses.Project_Master;
import com.EntityClasses.Project_Member;
import com.EntityClasses.Semester_Master;
import com.EntityClasses.Skillset_Master;
import com.EntityClasses.Sms_Server_Info;
import com.EntityClasses.Student;
import com.EntityClasses.Task_Master;
import com.EntityClasses.User_Info;
import com.EntityClasses.additional_hour;
import com.ModelClasses.Project_Model;
import com.ModelClasses.Reset_Password;
import com.ModelClasses.Task_Model;
import com.ServiceClasses.usersService;



@Controller
//@RequestMapping("users")
public class ControllerFile {
		
	@Autowired
	usersService usersService1;	
	@Autowired
	valuePerHourDaoImpl valuePerHour;
	
//	=================project============================
	@RequestMapping(value="/project", method=RequestMethod.GET)
	public ModelAndView viewProject() {
		String message = "Hello World";
		return new ModelAndView("project", "message", message);
	}


// ================================Login Validate================================================	
		@RequestMapping(value="/validate", method=RequestMethod.GET)
		public ModelAndView toValidate(User_Info user, BindingResult result) throws Exception
		{
			if(result.hasErrors())
			   {
				ModelAndView model2 = new ModelAndView("login");  //if it is error send it back to retrieve.jsp 
				model2.addObject("message", "Enter the correct format");  
				return model2;	
			   }
			user = usersService1.validate(user);
			if(user!=null)
				{return new ModelAndView("project");}
			else 
			{	
				ModelAndView model = new ModelAndView("login");
				model.addObject("message","Please input the correct username and password");
				return model;
			}
		}
		
		
		@RequestMapping(value = "/reset_password", method = RequestMethod.GET)
		public ModelAndView reset_password(HttpServletRequest request,String name) throws JsonGenerationException, JsonMappingException, IOException {
			ObjectMapper obj = new ObjectMapper();
			String json = "";
			ModelAndView view = null;
			List<User_Info> user=usersService1.check_valid_tocken(name);
	        System.out.println(name);
			try{
				if(user.size()==0)
				{
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("status", "incorrect");
					json = obj.writeValueAsString(model);
					view = new ModelAndView("reset_password","model",json);
				}
						
				else
				{
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("id", user.get(0).getId());
					model.put("status", "fine");
					json = obj.writeValueAsString(model);
					view = new ModelAndView("reset_password","model",json);
					
				}
			}catch (RuntimeException e)//NullpointerException
			{
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("status", "incorrect");
				json = obj.writeValueAsString(model);
				view = new ModelAndView("reset_password","model",json);
				
			}
			return view;
		}	
		
		
//	=================projectDetail============================
	@RequestMapping(value="/projectDetail", method=RequestMethod.GET)
	public ModelAndView viewProjectdetail() {
		//String message = "Hello World";
		return new ModelAndView("projectDetail");
	}
	@RequestMapping(value="/skillSetReporting", method=RequestMethod.GET)
	public ModelAndView skillSetReporting() {
		//String message = "Hello World";
		return new ModelAndView("skillSetReporting");
	}
	@RequestMapping(value="/profileMenu", method=RequestMethod.GET)
	public ModelAndView profileMenu() {
		//String message = "Hello World";
		return new ModelAndView("profileMenu");
	}
	@RequestMapping(value="/showCurrentPoint", method=RequestMethod.GET)
	public ModelAndView showCurrentPoint() {
		//String message = "Hello World";
		return new ModelAndView("showCurrentPoint");
	}
	@RequestMapping(value="/showEditProfile", method=RequestMethod.GET)
	public ModelAndView showEditProfile() {
		//String message = "Hello World";
		return new ModelAndView("showEditProfile");
	}
//	=================project report table============================
	@RequestMapping(value="/projectTable", method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView projectTable(@RequestParam("data") ArrayList<Object> projects) {
		
		System.out.println("Size is "+projects);
		return new ModelAndView("projectTable");
	}
//============================Retreive all project category from DB send through Ajax========================
			@RequestMapping(value="/projectCategoryList", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> getProjectCategoryList(){
						
				 Map<String,Object> map = new HashMap<String,Object>();
			
				   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
					List<Project_Category_Master> list = usersService1.getProjectCategories();
					 		
					if (list != null)
						map.put("data", list);
					else
						map.put("message","Data not found");			
					
					return map;
			}
			@RequestMapping(value="/skillSetList", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> getSkillset(){
						
				 Map<String,Object> map = new HashMap<String,Object>();
			
				   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
					List<Skillset_Master> list = usersService1.getAllSkillset();
					 		
					if (list != null)
						map.put("data", list);
					else
						map.put("message","Data not found");			
					
					return map;
			}
//============================Get Project AND Task========================
			@RequestMapping(value="/ProjectNTask", method=RequestMethod.GET)
			public @ResponseBody Map<String,?> getProjectNTask(){
						
				 Map<String,List> map = new HashMap<String,List>();
				 Map<String,Object> error = new HashMap<String,Object>();
				   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
					List<Project_Master> listProject = usersService1.getAllProject();
					List<Task_Master> listTask = usersService1.getAllTask();
					 		
					if (listProject == null || listTask == null)
						{
							error.put("message","Data not found");
							return error;
						}
						
					else
						{
							map.put("task", listTask);
							map.put("project", listProject);
							return map;
						}	
					}
			
//============================Retreive all users and ProjectCategory from DB send through Ajax========================
			@RequestMapping(value="/userNProjectCategoryList", method=RequestMethod.GET)
			public @ResponseBody Map<?,?> getUserNProjectCategoryListNStage(@RequestParam(value = "id", required=false, defaultValue = "0") Integer id) throws Exception{
				System.out.println("Hello");
				Project_Master project = usersService1.getProjectById(id);
				 Map<String,Object> map = new HashMap<String,Object>();
				 Map<String,Object> error = new HashMap<String,Object>();
					List<Project_Category_Master> listProjectCategory = usersService1.getProjectCategories();
					List<Skillset_Master> skillsets = usersService1.getAllSkillset();					
					List<User_Info> listUser = usersService1.getAllUser();
					List<Student> student = usersService1.getAllStudent();
					if (listProjectCategory == null || listUser == null)
						{
							error.put("message","Data not found");
							return error;
						}
					else if(student==null){
						System.out.println("Hi");throw new Exception();}
						
					else
						{
							map.put("category", listProjectCategory);
							map.put("user", listUser);
							map.put("student",student);
							map.put("skillset", skillsets);
							if(project!=null){
							map.put("currentproject",project);
							int members[] = usersService1.getMembersIdByProjectId(project.getId());
							int currentskill[] =  usersService1.getSkillsetByProjectId(project.getId());
							map.put("currentskill", currentskill);
							map.put("member", members);
							}
							return map;
						}	
					}
						 
						 
//=======================get right students base on project. To be used in Task=================================
   @RequestMapping(value="/studentInTask", method=RequestMethod.GET)
   public @ResponseBody Map<?,?> getRightStudent(@RequestParam(value = "id", required=false,defaultValue = "0") Integer projectid) throws ParseException, MalformedURLException, XmlRpcException{
     Map<String,Object> map = new HashMap<String,Object>();
     Map<String,Object> error = new HashMap<String,Object>();
       // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
     List<Project_Member> listMember = usersService1.getMemberByProjectId(projectid);
     if (listMember == null)
      {
       error.put("message","Data not found");
       return error;
      }
     else
      {
       map.put("student", listMember);
       return map;
      }
     }   
			 
						 
						 
						 
//=======================get Project and User===========================
			@RequestMapping(value="/ProjectNUser", method=RequestMethod.GET)
			public @ResponseBody Map<?,?> getProjectNUser(@RequestParam(value = "id", required=false,defaultValue = "0") Integer id) throws Exception{				
				 Map<String,Object> map = new HashMap<String,Object>();
				 Map<String,Object> error = new HashMap<String,Object>();
				   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
				if (id==0)	
				 {
					List<Project_Master> listProject = usersService1.getAllProject();
					List<Student> listStudent = usersService1.getAllStudent();
					if (listProject == null || listStudent ==null)
						{
							error.put("message","Data not found");
							return error;
						}
					else
						{
							map.put("project", listProject);
							map.put("student", listStudent);
							return map;
						}	
				}
				
				else
				{
					Task_Master currenttask = usersService1.getTaskById(id);
					int projectId = usersService1.getProjectIdByTaskId(id);
					List<Project_Member> listMember = usersService1.getMemberByProjectId(projectId);
					Project_Master project = usersService1.getProjectById(projectId);
					if (currenttask == null||listMember==null)
					{
						error.put("message","Data not found");
						return error;
					}
				else
					{
						map.put("currenttask", currenttask);
						map.put("member", listMember);
						map.put("project", project);
						return map;
					}	
				}
}
			//=======================get Project and User===========================
			@RequestMapping(value="/ProjectNUser2", method=RequestMethod.GET)
			public @ResponseBody Map<?,?> getProjectNUser2(@RequestParam(value = "id", required=false,defaultValue = "0") Integer id) throws Exception{				
				 Map<String,Object> map = new HashMap<String,Object>();
				 Map<String,Object> error = new HashMap<String,Object>();
				   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
				if (id==0)	
				 {
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					UserDetails userDetails=(UserDetails) auth.getPrincipal();
					List<Project_Master> listProject= valuePerHour.getProjectDataBaseOnAdmin(userDetails.getUsername());
					List<Student> listStudent = usersService1.getAllStudent();
					if (listProject == null || listStudent ==null)
						{
							error.put("message","Data not found");
							return error;
						}
					else
						{
							map.put("project", listProject);
							map.put("student", listStudent);
							return map;
						}	
				}
				
				else
				{
					Task_Master currenttask = usersService1.getTaskById(id);
					int projectId = usersService1.getProjectIdByTaskId(id);
					List<Project_Member> listMember = usersService1.getMemberByProjectId(projectId);
					Project_Master project = usersService1.getProjectById(projectId);
					if (currenttask == null||listMember==null)
					{
						error.put("message","Data not found");
						return error;
					}
				else
					{
						map.put("currenttask", currenttask);
						map.put("member", listMember);
						map.put("project", project);
						return map;
					}	
				}
}
			@RequestMapping(value="/deleteProjectDetail", method = RequestMethod.GET)
			public @ResponseBody Map<String,String>  deleteProjectDetail(@RequestParam(value = "id", required=false) Integer id){
				Map<String,String> map = new HashMap<String,String>();
				int status = 0;
				status= usersService1.deleteProjectDetail(id);
				if(status==1)
					map.put("status", "200");
				else 	
					map.put("status", "300");
				
				return map;
				}
			@RequestMapping(value="/deleteTaskDetail", method = RequestMethod.GET)
			public @ResponseBody Map<String,String>  deleteTaskDetail(@RequestParam(value = "id", required=false) Integer id){
				Map<String,String> map = new HashMap<String,String>();
				int status = 0;
				status= usersService1.deleteTaskDetail(id);
				if(status==1)
					map.put("status", "200");
				else 	
					map.put("status", "300");
				
				return map;
				}
//========================Save Project========================================================
			@RequestMapping(value="/saveProject", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> toSaveProject(Project_Model pm) throws Exception{
				if(pm.getKit_point().isEmpty())
					pm.setKit_point("0");	
					DecimalFormat df2 = new DecimalFormat(".##");
					Map<String, Float> pointNbudget = new HashMap<String, Float>();
					int[] m = pm.getMember();
					int [] skill = pm.getSkill();
					Map<String,Object> map = new HashMap<String,Object>();
					Map<Integer, String> mm = usersService1.getStudentSemester(m);
//					mm={	{11,Semester 2, 4}, {11,Semester 2, 4}....}  11:student_id, 4:batch_id
					if(pm.isAuto())
					pointNbudget =usersService1.pointCalculation(mm,pm.getInitially_planned());
					else{
						pointNbudget.put("budget", (float) pm.getBudget());
						pointNbudget.put("point", Float.parseFloat(pm.getKit_point()));
					}
					if (pointNbudget.containsKey("No Value"))
							{
								int batch_id = Math.round(pointNbudget.get("batch_id"));
								String batch = new String();
								Set<String> keys = pointNbudget.keySet();								
								Batch_Master b = usersService1.getBatchById(batch_id);
								batch  =b.getName();
								map.put("status", "555");
								map.put("message","Please Create Value Per Hour for "+batch);
								return map;
							}
					else if(pointNbudget.containsKey("No Point"))
					{
						map.put("status", "888");
						map.put("message","Please create KIT point value first!");
						return map;
					}
					//pm.setKit_point(Float.toString(pointNbudget.get("point")));
					pm.setKit_point(df2.format(pointNbudget.get("point")));
					pm.setBudget(Math.round(pointNbudget.get("budget")));
					int id = usersService1.saveProject(pm);
					if(id!=0)
						
					{
						usersService1.saveMember(id,m);
						usersService1.saveSkillset(id, skill);
						if(pm.getStatus().equals("Completed Project"))
							usersService1.addPointStudent(m, id, Float.parseFloat(df2.format(pointNbudget.get("point"))), pm.getInitially_planned());
						else
							usersService1.addPointStudent(m, id, 0, pm.getInitially_planned());
						map.put("status","200");
						map.put("message","Your record has been saved successfully");
						
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Failed");
						return map;
					}
				}
//========================projectReportSubmit========================================================
			@RequestMapping(value="/projectReportSubmit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> projectReportSubmit(Project_Model pm) throws Exception{
					Map<String,Object> map = new HashMap<String,Object>();
					List<Project_Master> projects= usersService1.getProjectReporting(pm);
					if(projects!=null)
						
					{
						map.put("status","200");
						map.put("message","Succeeded");		
						map.put("data", projects);
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Failed");
						return map;
					}
				}
			@RequestMapping(value="/additional_hour_submit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> additional_hour_submit(additional_hour hour) throws Exception{
					Map<String,Object> map = new HashMap<String,Object>();
					if(usersService1.additional_hour_submit(hour))
						
					{
						map.put("status","200");
						return map;
					}
					else {
						map.put("status","999");
						return map;
					}
				}
			@RequestMapping(value="/resetPwSubmit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> resetPwSubmit(Reset_Password pw) throws Exception{
					Map<String,Object> map = new HashMap<String,Object>();
					if(usersService1.reset_passwordd(pw))
						
					{
						map.put("status","200");
						map.put("message","Succeeded");
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Failed");
						return map;
					}
				}
			
			
			
			@RequestMapping(value="/skillSetReportingSubmit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> skillSetReportingSubmit(Project_Model pm) throws Exception{
					int skill[] = pm.getSkill();
					Map<String,Object> map = new HashMap<String,Object>();
					List<Project_Master> projects = usersService1.getProjectOnSkillset(skill);
					if(projects!=null)
						
					{
						map.put("status","200");
						map.put("message","Succeeded");		
						map.put("data", projects);
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Failed");
						return map;
					}
				}
//========================projectReportSubmit========================================================
			@RequestMapping(value="/updatePointSubmit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> updatePointSubmit(KIT_Point_Student_Wise p) throws Exception{
					System.out.println("ID "+p.getUser_id());
					System.out.println("P "+p.getKit_point());
					Map<String,Object> map = new HashMap<String,Object>();
					if(usersService1.updatePoint(p))
						
					{
						map.put("status","200");
						map.put("message","You have updated successfully");		
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","It is not updated");
						return map;
					}
				}

			@RequestMapping(value="/updatePointMemberSubmit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> updatePointMemberSubmit(KIT_Point_Student_Wise p) throws Exception{
				System.out.println("ID "+p.getProject_id());
				System.out.println("P "+p.getKit_point());	
				Map<String,Object> map = new HashMap<String,Object>();
					if(usersService1.updatePointMember(p))
						
					{
						map.put("status","200");
						map.put("message","You have updated successfully");		
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","It is not updated");
						return map;
					}
				}
			@RequestMapping(value="/additional_hour_update", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> additional_hour_update(additional_hour p) throws Exception{
				Map<String,Object> map = new HashMap<String,Object>();
					if(usersService1.additional_hour_update(p))
						
					{
						map.put("status","200");
						map.put("message","You have updated successfully");		
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","It is not updated");
						return map;
					}
				}

//=================view points============================
			@RequestMapping(value="/viewPoint", method=RequestMethod.GET)
			public ModelAndView viewPoint(@RequestParam("id") int id) {
				return new ModelAndView("viewPoint", "message", id);
				}	
//=================update points============================
			@RequestMapping(value="/updatePoint", method=RequestMethod.GET)
			public ModelAndView updatePoint(@RequestParam("id") int id) {
				return new ModelAndView("updatePoint", "message", id);
				}	
//========================taskReportSubmit========================================================
			@RequestMapping(value="/taskReportSubmit", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> taskReportSubmit(Task_Model tm) throws Exception{
					Map<String,Object> map = new HashMap<String,Object>();
					List<Task_Master> tasks= usersService1.getTaskReporting(tm);
					if(tasks!=null)
						
					{
						map.put("status","200");
						map.put("message","Succeeded");		
						map.put("data", tasks);
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Failed");
						return map;
					}
				}
//========================get All point========================================================
			@RequestMapping(value="/getAllPoint", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> getAllPoint(@RequestParam("id") int batch_id){
					Map<String,Object> map = new HashMap<String,Object>();
					List<Student> students= usersService1.getAllPoint(batch_id);
					if(students!=null)
						
					{
						map.put("status","200");
						map.put("message","Succeeded");		
						map.put("data", students);
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Failed");
						return map;
					}
				}
//========================update All point========================================================
			@RequestMapping(value="/updateAllPoint", method=RequestMethod.GET)
			public ModelAndView updateAllPoint(@RequestParam("id") int user_id/*,@RequestParam("name") String name*/) throws Exception{
					ObjectMapper mapper = new ObjectMapper();
					Map<String,Object> map = new HashMap<String,Object>();
					List<KIT_Point_Student_Wise> points =  usersService1.updateAllPoint(user_id);
					map.put("data", points);
					map.put("name", usersService1.getUserById(user_id).getName());
					String json = "";
					try {
						json = mapper.writeValueAsString(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("updatePoint", "message", json);
				}
			@RequestMapping(value="/viewAllMember", method=RequestMethod.GET)
			public ModelAndView viewAllMember(@RequestParam("id") int project_id/*,@RequestParam("name") String name*/) throws Exception{
					ObjectMapper mapper = new ObjectMapper();
					List<Project_Member> members =  usersService1.getMemberByProjectId(project_id);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("data", members);
					map.put("name", usersService1.getProjectById(project_id).getProject_name());
					String json = "";
					try {
						json = mapper.writeValueAsString(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("viewAllMember", "message", json);
				}
			
			
			@RequestMapping(value="/viewAllProjectBySkill", method=RequestMethod.GET)
			public ModelAndView viewAllProjectBySkill(@RequestParam("data") int s[]) throws Exception{
				ObjectMapper mapper = new ObjectMapper();
				int skill[] = s;
				Map<String,Object> map = new HashMap<String,Object>();
				List<Project_Master> projects = usersService1.getProjectOnSkillset(skill);
				
				String json = "";
				try {
					json = mapper.writeValueAsString(projects);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return new ModelAndView("viewAllProjectBySkill", "message", json);
			}
			
			
			@RequestMapping(value="/updateAllPoint2", method=RequestMethod.GET)
			public ModelAndView updateAllPoint2() throws Exception{
					Authentication auth = SecurityContextHolder.getContext().getAuthentication();
					UserDetails userDetails=(UserDetails) auth.getPrincipal();
					ObjectMapper mapper = new ObjectMapper();
					List<KIT_Point_Student_Wise> points =  usersService1.updateAllPoint(usersService1.getUserIdByName(userDetails.getUsername()));
					System.out.println("size "+points.size());
					String json = "";
					try {
						json = mapper.writeValueAsString(points);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("showCurrentPoint", "message", json);
				}
			@RequestMapping(value="/updatePointMember", method=RequestMethod.GET)
			public ModelAndView updatePointMember(@RequestParam("id") int project_id) throws Exception{
					List<Student> student = usersService1.getAllStudent();
					ObjectMapper mapper = new ObjectMapper();
					List<KIT_Point_Student_Wise> points =  usersService1.getPointByProjectId(project_id);
					List<Integer> myList = new ArrayList<Integer>();
					for(Student s:student)
						myList.add(Integer.parseInt(s.getId()));
					int size = myList.size();
  	    		    Integer[] users = myList.toArray(new Integer[size]);
					Arrays.sort(users);
					for (int i=0;i<points.size();i++)
					{
						if(contains(users,points.get(i).getUser_id()))
		    		     {
							int id = points.get(i).getUser_id();
							points.get(i).setName(usersService1.getUserById(id).getName());
		    		     }
					}
					points.get(0).setTpoint(usersService1.getProjectById(points.get(0).getProject_id()).getKit_point());
					String json = "";
					try {
						json = mapper.writeValueAsString(points);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("updatePointMember", "message", json);
				}
			
			@RequestMapping(value="/additional_hour", method=RequestMethod.GET)
			public ModelAndView additional_hour(@RequestParam("id") int project_id) throws Exception{
					List<Student> student = usersService1.getAllStudent();
					ObjectMapper mapper = new ObjectMapper();
					List<additional_hour> hours =  usersService1.getAdditionalHourProjectId(project_id);
					List<Integer> myList = new ArrayList<Integer>();
					for(Student s:student)
						myList.add(Integer.parseInt(s.getId()));
					int size = myList.size();
  	    		    Integer[] users = myList.toArray(new Integer[size]);
					Arrays.sort(users);
					System.out.println("USER "+users.length);
					for (int i=0;i<hours.size();i++)
					{
						System.out.println("Idd "+hours.get(i).getUser_id());
						if(contains(users,hours.get(i).getUser_id()))
		    		     {
							int id = hours.get(i).getUser_id();
							System.out.println("Idd "+id);
							hours.get(i).setName(usersService1.getUserById(id).getName());
		    		     }
					}
					
					Map<String, Object> map = new HashMap<String, Object>();
					List<Project_Member> members =  usersService1.getMemberByProjectId(project_id);
					map.put("hours", hours);
					map.put("members", members);
					//points.get(0).setTpoint(usersService1.getProjectById(points.get(0).getProject_id()).getKit_point());
					String json = "";
					try {
						json = mapper.writeValueAsString(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("additional_hour", "message", json);
				}
			@RequestMapping(value="/additional_hour_admin", method=RequestMethod.GET)
			public ModelAndView additional_hour_admin(@RequestParam("id") int project_id) throws Exception{
					List<Student> student = usersService1.getAllStudent();
					ObjectMapper mapper = new ObjectMapper();
					List<additional_hour> hours =  usersService1.getAdditionalHourProjectId(project_id);
					List<Integer> myList = new ArrayList<Integer>();
					for(Student s:student)
						myList.add(Integer.parseInt(s.getId()));
					int size = myList.size();
  	    		    Integer[] users = myList.toArray(new Integer[size]);
					Arrays.sort(users);
					System.out.println("USER "+users.length);
					for (int i=0;i<hours.size();i++)
					{
						System.out.println("Idd "+hours.get(i).getUser_id());
						if(contains(users,hours.get(i).getUser_id()))
		    		     {
							int id = hours.get(i).getUser_id();
							System.out.println("Idd "+id);
							hours.get(i).setName(usersService1.getUserById(id).getName());
		    		     }
					}
					
					Map<String, Object> map = new HashMap<String, Object>();
					List<Project_Member> members =  usersService1.getMemberByProjectId(project_id);
					map.put("hours", hours);
					map.put("members", members);
					//points.get(0).setTpoint(usersService1.getProjectById(points.get(0).getProject_id()).getKit_point());
					String json = "";
					try {
						json = mapper.writeValueAsString(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("additional_hour", "message", json);
				}
//========================Update Project========================================================
			@RequestMapping(value="/updateProject", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> toUpdateProject(Project_Model pm) throws Exception{
	        		//int[] s = pm.getStage();
				if(pm.getKit_point().isEmpty())
					pm.setKit_point("0");	
					
//					DecimalFormat df2 = new DecimalFormat(".##");
					Project_Master project = new Project_Master();
					project = usersService1.getProjectById(pm.getId());
					Map<String,Object> map = new HashMap<String,Object>();
/*						
					Map<Integer, String> mm = usersService1.getStudentSemester(pm.getMember());	
					if(project.getKit_point() == null) {
						project.setKit_point("0");
					}	
					if (project.getKit_point().equals(pm.getKit_point()))
					{
						int newa[] = pm.getMember();
						Arrays.sort(newa);
						int old[] = usersService1.getMembersIdByProjectId(pm.getId());
				        Arrays.sort(old);
						if (Arrays.equals(newa, old)&&pm.getInitially_planned()==project.getInitially_planned())
						{
							
						}
						else{
							Map<String, Float> pointNbudget = null;
							pointNbudget =usersService1.pointCalculation(mm,pm.getInitially_planned());
							if (pointNbudget.containsKey("No Value"))
							{
								int batch_id = Math.round(pointNbudget.get("batch_id"));
								String batch = new String();
								Set<String> keys = pointNbudget.keySet();								
								Batch_Master b = usersService1.getBatchById(batch_id);
								batch  =b.getName();
								map.put("status", "555");
								map.put("message","Please Create Value Per Hour for "+batch);
								return map;
							}
							else if(pointNbudget.containsKey("No Point"))
							{
								map.put("status", "888");
								map.put("message","Please create KIT point value first!");
								return map;
							}
							pm.setKit_point(df2.format(pointNbudget.get("point")));
							pm.setBudget(Math.round(pointNbudget.get("budget")));
						}
					}
					else{
						pm.setKit_point(df2.format(Float.parseFloat(pm.getKit_point())));
					}*/
					
					if(usersService1.updateProject(pm))
					{
/*						if(pm.getStatus().equals("Completed Project")&&!project.getStatus().equals("Completed Project"))
							usersService1.addPointStudent(pm.getMember(), pm.getId(), Float.parseFloat(pm.getKit_point()), pm.getInitially_planned());
						if(pm.getStatus().equals("Completed Project")&&project.getStatus().equals("Completed Project"))
							usersService1.updatePointStudent(pm.getMember(), pm.getId(), Float.parseFloat(pm.getKit_point()), pm.getInitially_planned());*/
						map.put("status","200");
						map.put("message","Your record has been saved successfully");
						return map;
					}
					else {
						
						map.put("status","999");
						map.put("message","Project name already existed");
						return map;
					}
				}
//========================Update Task========================================================
			@RequestMapping(value="/updateTask", method=RequestMethod.GET)
			public @ResponseBody Map<String,Object> toUpdateTask(Task_Model tm) throws ParseException{
	        		//int[] s = pm.getStage();
					Map<String,Object> map = new HashMap<String,Object>();				
					if(usersService1.updateTask(tm))
					{
						map.put("status","200");
						map.put("message","Your record has been updated successfully");
						return map;
					}
					else {
						map.put("status","999");
						map.put("message","Your record has not been updated");
						return map;
					}
				}
//	=================taskDetails============================
	@RequestMapping(value="/taskDetail", method=RequestMethod.GET)
	public ModelAndView viewTaskdetail() {
		//String message = "Hello World";
		return new ModelAndView("taskDetail");
	}
//	=================taskDetails============================
	@RequestMapping(value="/task", method=RequestMethod.GET)
	public ModelAndView viewTaskView() {
		//String message = "Hello World";
		return new ModelAndView("taskView");
	}
	


//======================save task===============================
	@RequestMapping(value="/saveTask", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> toSaveTask(Task_Model task) throws ParseException{
			
			//System.out.println("Name is: "+projectCategoryName.getName());
			Map<String,Object> map = new HashMap<String,Object>();				
			if(usersService1.toSaveTask(task)){
				map.put("status","200");
				map.put("message","Your record has been saved successfully");
				return map;
			}
			else {
				//System.out.println("Else Runs");
				map.put("status","999");
				map.put("message","Your record is not saved");
				return map;
			}
		}

//	=================setting============================
	@RequestMapping(value="/setting", method=RequestMethod.GET)
	public ModelAndView viewSetting() {
		String message = "Hello World";
		return new ModelAndView("viewSetting", "message", message);
	}
//	=================view batch============================
	@RequestMapping(value="/batch", method=RequestMethod.GET)
	public ModelAndView viewBatch() {
		String message = "Hello World";
		return new ModelAndView("viewBatch", "message", message);
	}
//	=================view reporting============================
	@RequestMapping(value="/reporting", method=RequestMethod.GET)
	public ModelAndView viewReporting() {
		String message = "Hello World";
		return new ModelAndView("viewReporting", "message", message);
	}
//	=================kit point============================
	@RequestMapping(value="/kitpoint", method=RequestMethod.GET)
	public ModelAndView viewkitpoint() {
		String message = "Hello World";
		return new ModelAndView("viewKitPoint", "message", message);}
//	================kitpoint_value============================
				@RequestMapping(value="/kitpoint_value", method=RequestMethod.GET)
				public ModelAndView kitpoint_value() {
					String message = "Hello World";
					return new ModelAndView("kitpoint_value", "message", message);
	}
//	================view_update_point============================
				@RequestMapping(value="/view_update_point", method=RequestMethod.GET)
				public ModelAndView view_update_point() {
					String message = "Hello World";
					return new ModelAndView("view_update_point", "message", message);
	}
//	========= ========view project category============================
	@RequestMapping(value="/projectCategory", method=RequestMethod.GET)
	public ModelAndView viewProjectCategory() {
		return new ModelAndView("viewProjectCaterory");
	}

//================================Project Category Create============================================
		@RequestMapping(value="/projectCategoryCreate", method=RequestMethod.GET)
		public @ResponseBody Map<String,Object> toCreateProjectCategory(Project_Category_Master projectCategoryName){
				Map<String,Object> map = new HashMap<String,Object>();				
				if(usersService1.createProjectCategory(projectCategoryName)){
					map.put("status","200");
					map.put("message","Your record has been saved successfully");
					return map;
				}
				else {
					map.put("status","999");
					map.put("message","Your record already existed");
					return map;
				}
				
			}
		//================================Skill set Create============================================
				@RequestMapping(value="/skillsetCreate", method=RequestMethod.GET)
				public @ResponseBody Map<String,Object> skillsetCreate(Skillset_Master skillset){
						Map<String,Object> map = new HashMap<String,Object>();				
						if(usersService1.createSkillset(skillset)){
							map.put("status","200");
							map.put("message","Your record has been saved successfully");
							return map;
						}
						else {
							map.put("status","999");
							map.put("message","Your record already existed");
							return map;
						}
						
					}

//	=================create new user============================
	@RequestMapping(value="/newUser", method=RequestMethod.GET)
	public ModelAndView createUser() {
		String message = "Hello World";
		return new ModelAndView("createUser", "message", message);
	}
//	=================view profile & change password============================
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public ModelAndView changePassword() {
		String message = "Hello World";
		return new ModelAndView("changePassword", "message", message);
	}
//	=================Create Batch============================
	@RequestMapping(value="/createBatch", method=RequestMethod.GET)
	public ModelAndView createBatch() {
		return new ModelAndView("createBatch");
	}
//	=================Project Reporting============================
	@RequestMapping(value="/projectReporting", method=RequestMethod.GET)
	public ModelAndView projectReporting() {
		return new ModelAndView("projectReporting");
	}
//	=================Task Reporting============================
	@RequestMapping(value="/taskReporting", method=RequestMethod.GET)
	public ModelAndView taskReporting() {
		return new ModelAndView("taskReporting");
	}

//============================Retreive all semesters from DB send through Ajax========================
		@RequestMapping(value="/semesterList", method=RequestMethod.GET)
		public @ResponseBody Map<String,Object> getSemesterList(){
					
			 Map<String,Object> map = new HashMap<String,Object>();
		
			   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
				List<Semester_Master> list = usersService1.getAllSemester();
				 		
				if (list != null)
					map.put("data", list);
				else
					map.put("message","Data not found");			
				
				return map;
		}
//============================Get sms server info========================
		@RequestMapping(value="/getSmsServerInfo", method=RequestMethod.GET)
		public @ResponseBody Map<String,Object> getSmsServerInfo(){
					
			 Map<String,Object> map = new HashMap<String,Object>();
		
			   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
				Sms_Server_Info info = usersService1.getSmsServerInfo();
				 		
				if (info != null)
				{
					map.put("status", 999);
					map.put("data", info); }
				else
				{
					map.put("status", 404);
					map.put("data", info);
				}
				
				return map;
		}
//============================update batch and student========================
				@RequestMapping(value="/updateBatchNStudent", method=RequestMethod.GET)
				public @ResponseBody Map<String,Object> updateBatchNStudent() throws Exception{
							
					 Map<String,Object> map = new HashMap<String,Object>();
					 if(usersService1.updateSemester())
					 {
						 if(usersService1.updateStudent())
						 {
							 map.put("status", 999);
							 map.put("message", "Both batch and student are updated successfully");
							 return map;
						 }
						 else
						 {
							 map.put("status", 888);
							 map.put("message", "Batch updated"); 
						 }
					 }
					 else
					 {
						 map.put("status", 111);
						 map.put("message", "No updation, Please check with SMS server info. properly!"); 
					 }
					 return map;
				}
//============================Retreive all users from DB send through Ajax========================
				@RequestMapping(value="/allUser", method=RequestMethod.GET)
				public @ResponseBody Map<String,Object> getAllUser(){
							
					 Map<String,Object> map = new HashMap<String,Object>();
				
					   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
						List<User_Info> list = usersService1.getAllUser();
						 		
						if (list != null)
							map.put("data", list);
						else
							map.put("message","Data not found");			
						
						return map;
				}
//================================Update Batch============================================
				@RequestMapping(value="/updateBatch_1", method=RequestMethod.GET)
				public Map<String,Object> toUpdateBatch(){
					System.out.println("Hi");
					return null;
						
						
					}
				@RequestMapping(value="/getSkillset", method=RequestMethod.GET)
				public @ResponseBody Map<String,?> showBatch(){
					 Map<String,List> map = new HashMap<String,List>();
					 Map<String,Object> error = new HashMap<String,Object>();
						List<Skillset_Master> skills = usersService1.getAllSkillset();
						if (skills == null)
							{
								error.put("message","batch not found");
								return error;
							}
							
						else
							{
								map.put("skill", skills);
								return map;
							}	
				}
//================================Save Batch============================================
@RequestMapping(value="/batchSubmit", method=RequestMethod.GET)
public @ResponseBody Map<String,Object> toCreateProjectCategory(Batch_Master batch){
		
		//System.out.println("Name is: "+projectCategoryName.getName());
		Map<String,Object> map = new HashMap<String,Object>();				
		if(usersService1.saveBatch(batch)){
			map.put("status","200");
			map.put("message","Your record has been saved successfully");
			return map;
		}
		else {
			//System.out.println("Else Runs");
			map.put("status","999");
			map.put("message","Your record already existed");
			return map;
		}
	}
//================================Save Server Info============================================
@RequestMapping(value="/serverInfoSubmit", method=RequestMethod.GET)
public @ResponseBody Map<String,Object> saveServerInfo(Sms_Server_Info info){
	Sms_Server_Info DBinfo = new Sms_Server_Info();
	Map<String,Object> map = new HashMap<String,Object>();
	DBinfo  = usersService1.getSmsServerInfo();
	if(DBinfo==null)
	{
		if(usersService1.saveServerInfo(info)){
			map.put("status","200");
			map.put("message","Your record has been saved successfully");
			return map;
		}
		else {
			//System.out.println("Else Runs");
			map.put("status","999");
			map.put("message","Your record has not been saved successfully");
			return map;
		}
	}
	else
	{
		if(usersService1.updateServerInfo(info)){
			map.put("status","201");
			map.put("message","Your record has been updated successfully");
			return map;
		}
		else {
			//System.out.println("Else Runs");
			map.put("status","1000");
			map.put("message","Your record has not been updated successfully");
			return map;
		}
	}
	
		
		
		
	}


//===================View Update Batch======================================
	@RequestMapping(value="/showUpdateBatch", method=RequestMethod.GET)
	public ModelAndView updateBatch() {
		return new ModelAndView("updateBatch");
	}
//============================Retreive all semesters and batches from DB send through Ajax========================
			@RequestMapping(value="/semesterAndBatchList", method=RequestMethod.GET)
			public @ResponseBody Map<String,?> getSemesterAndBatchList(){
						
				 Map<String,List> map = new HashMap<String,List>();
				 Map<String,Object> error = new HashMap<String,Object>();
				   // DaoClasses.userDaoImpl dao = new DaoClasses.userDaoImpl();
					List<Semester_Master> listSemester = usersService1.getAllSemester();
					List<Batch_Master> listBatch = usersService1.getAllBatch();
					 		
					if (listSemester == null || listBatch == null)
						{
							error.put("message","Data not found");
							return error;
						}
						
					else
						{
							map.put("semester", listSemester);
							map.put("batch", listBatch);
							return map;
						}	
					}

	@RequestMapping(value="/addUser", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> getSaved(User_Info users){
		
		Map<String,Object> map = new HashMap<String,Object>();		
		if(usersService1.addUser2(users)){
			map.put("status","200");
			map.put("message","Your record has been saved successfully");
		}
		else {
			map.put("status","999");
			map.put("message","Email or Name already existed");
			return map;
		}
		
		return map;
	}
		
	

	@RequestMapping(value="/updateProjectDetail", method = RequestMethod.GET)
	public ModelAndView updateProjectDetail(@RequestParam(value = "id", required=false) Integer id){
		ModelAndView view =new ModelAndView("updateProjectDetail");
		//System.out.println("ID in Controller is "+id);
		view.addObject("id",id);
		return view;
		}
	
	
	@RequestMapping(value="/updateProjectDetailAdminView", method = RequestMethod.GET)
	public ModelAndView updateProjectDetailAdminView(@RequestParam(value = "id", required=false) Integer id){
		ModelAndView view =new ModelAndView("updateProjectDetailAdminView");
		//System.out.println("ID in Controller is "+id);
		view.addObject("id",id);
		return view;
		}
	
	@RequestMapping(value="/updateTaskDetail", method = RequestMethod.GET)
	public ModelAndView updateTask(@RequestParam(value = "id", required=false) Integer id){
		ModelAndView view =new ModelAndView("updateTaskDetail");
		//System.out.println("ID in Controller is "+id);
		view.addObject("id",id);
		return view;
		}
	@RequestMapping(value="/updateTaskDetailAdminView", method = RequestMethod.GET)
	public ModelAndView updateTaskDetailAdminView(@RequestParam(value = "id", required=false) Integer id){
		ModelAndView view =new ModelAndView("updateTaskDetailAdminView");
		//System.out.println("ID in Controller is "+id);
		view.addObject("id",id);
		return view;
		}
	@RequestMapping(value="/editProfile", method=RequestMethod.GET)
	public @ResponseBody Map<String,String> editProfile(User_Info user) throws Exception{
	// Pass the new password in name variable
	// Pass the name in user_type variable
			String newPassword = user.getName();
			Map<String,String> map = new HashMap<String,String>();
			int check = usersService1.editProfile(user.getEmail(),user.getPassword(),newPassword,user.getUser_type());
			if(check==1)
			{
				map.put("status", "999");
				map.put("message", "Updation is completely done!");
			}
			else if(check==2)
			{
				map.put("status", "777");
				map.put("message", "You cannot edit profile for others!");
			}
			else
			{
				map.put("status", "888");
				map.put("message", "Either email or password is incorrect!");
			}
			return map;
			
		}
	@RequestMapping(value="/forgetPasswordSubmit", method=RequestMethod.GET)
	public @ResponseBody Map<String,String> forgetPasswordSubmit(User_Info user) throws Exception{
		System.out.println("Hello pp"+user.getEmail());
			String email = user.getEmail();
			Map<String,String> map = new HashMap<String,String>();
			int check = usersService1.getUserByEmail(email);
			if(check==0)
			{
				map.put("status", "999");
				map.put("message", "Email does not exist");
			}
			else
			{
				usersService1.forgot_password_email_sending(email);
				map.put("status", "888");
				map.put("message", "Done!");
			}
			return map;
			
		}
	//=============To find an element in array==============================
		public static boolean contains(Integer[] users, int item) {
		      int index = Arrays.binarySearch(users, item);
		      return index >= 0;
		   }
}







	

	
	






