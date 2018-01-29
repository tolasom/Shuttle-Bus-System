package com.DaoClasses;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;















import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;

import com.EntityClasses.Batch_Master;
import com.EntityClasses.KIT_Point_Student_Wise;
import com.EntityClasses.Project_Category_Master;
import com.EntityClasses.Project_Master;
import com.EntityClasses.Project_Member;
import com.EntityClasses.Project_Stage_Master;
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



public interface usersDao {
	
	
	public User_Info findByUserName(String name);

	public boolean addUser2(User_Info users);
	public List<User_Info> getAllUser();
	public List<Project_Member> getMemberByProjectId(int id);
	public List<Project_Master> getAllProject();
	public User_Info validate(User_Info user) throws Exception;
	public boolean saveBatch(Batch_Master batch);
	public boolean updateBatch(Batch_Master batch);
	public boolean createProjectCategory(Project_Category_Master projectCategory);
	public boolean toSaveTask(Task_Model t) throws ParseException;
	public int saveProject(Project_Model project) throws Exception;
	public boolean updateProject(Project_Model project) throws Exception;
	public boolean updateTask(Task_Model t) throws ParseException;
	public void saveMember(int projectid, int arr[]) throws MalformedURLException, XmlRpcException;
	public List<Semester_Master> getAllSemester();
	public List<Project_Category_Master> getProjectCategories();
	public List<Batch_Master> getAllBatch();
	public List<Task_Master> getAllTask();
	public Project_Master getProjectById(int id) throws Exception;
	public Task_Master getTaskById(int id);
	public int getProjectIdByTaskId(int taskId);
	public Map<Integer, String> getStudentSemester(int[] arr) throws Exception;
	public Map<String, Float> pointCalculation(Map<Integer, String> mm, int t) throws Exception;
	public String getKitPoint();
	public List<Semester_Master> getStudent_Semester(int batch_id) throws XmlRpcException, MalformedURLException, ParseException;
	public String getSemesterByBatchId(int id) throws Exception;
	public String getCurrentSemester(List<Semester_Master> semesters) throws ParseException;
	public int[] getMembersIdByProjectId(int project_id);
	public Project_Member getAMemberById (int id, int project_id);
	public String getKitPointByProjectId (int project_id);
	public Batch_Master getBatchById(int id);
	public int deleteProjectDetail(int id);
	public void deleteMemberByProjectId(int id);
	public void deleteTaskByProjectId(int id);
	public List<Task_Master> getAllTaskByProjectId(int id);
	public int editProfile(String email, String oldPassword, String newPassword, String name) throws Exception;
	public int deleteTaskDetail(int id);
	public List<Student> getAllStudent();
	public boolean updateStudent() throws Exception;
	public List<Student> exchangeBatchValue(List<Student> givenStudent) throws Exception;
	public boolean validateStudent (Student studentValidate,List<Student> studentDB);
	public List<Batch_Master> pullAllBatch() throws Exception;
	public boolean updateSemester()throws Exception;
	public Sms_Server_Info getSmsServerInfo();
	public boolean updateServerInfo(Sms_Server_Info info);
	public boolean saveServerInfo(Sms_Server_Info info);
	public List<Project_Master> getProjectReporting(Project_Model project) throws ParseException;
	public List<Task_Master> getTaskReporting(Task_Model task) throws ParseException;
	public void addPointStudent (int students[],int project_id,float point,int hours);
	public User_Info getUserById(int id);
	public List<Student> getAllPoint(int batch_id);
	public List<Student> getAllStudentByBatchId(int batch_id);
	public List<KIT_Point_Student_Wise> updateAllPoint(int user_id) throws Exception;
	public boolean updatePoint(KIT_Point_Student_Wise point);
	public int getUserIdByName(String name);
	public void updatePointStudent (int students[],int project_id,float point,int hours);
	public List<KIT_Point_Student_Wise> getPointByProjectId(int project_id) throws Exception;
	public boolean updatePointMember(KIT_Point_Student_Wise point);
	public boolean createSkillset(Skillset_Master skillset);
	public List<Skillset_Master> getAllSkillset();
	public void saveSkillset(int projectid, int arr[]) throws Exception;
	public int[] getSkillsetByProjectId(int project_id);
	public List<Project_Master> getProjectOnSkillset(int[] skill) throws Exception;
	public int getUserByEmail(String email) ;
	public boolean forgot_password_email_sending(String email);
	public List<User_Info> check_valid_tocken(String token);
	public Boolean reset_passwordd(Reset_Password pw);
	public List<additional_hour> getAdditionalHourProjectId(int project_id) throws Exception;
	public boolean additional_hour_submit(additional_hour hour);
	public boolean additional_hour_update(additional_hour p);
}
