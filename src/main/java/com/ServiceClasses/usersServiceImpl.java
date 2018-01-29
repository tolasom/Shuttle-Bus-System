package com.ServiceClasses;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DaoClasses.usersDao;
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



@Service
public class usersServiceImpl implements usersService{

	@Autowired
	usersDao usersDao1;
	
	
	public User_Info findByUserName(String name) {
		return usersDao1.findByUserName(name);
	}
	
	public boolean addUser2(User_Info users) {
		return usersDao1.addUser2(users);
	}
	public List<User_Info> getAllUser(){
		return usersDao1.getAllUser();
	}
	public List<Project_Member> getMemberByProjectId(int id){
		return usersDao1.getMemberByProjectId(id);
	}
	public List<Project_Master> getAllProject(){
		return usersDao1.getAllProject();
	}
	public User_Info validate(User_Info user) throws Exception{
		return usersDao1.validate(user);
	}
	public boolean saveBatch(Batch_Master batch){
		return usersDao1.saveBatch(batch);
	}
	public boolean updateBatch(Batch_Master batch){
		return usersDao1.updateBatch(batch);
	}
	public boolean createProjectCategory(Project_Category_Master projectCategory){
		return usersDao1.createProjectCategory(projectCategory);
	}
	public boolean toSaveTask(Task_Model t) throws ParseException{
		return usersDao1.toSaveTask(t);
	}
	public int saveProject(Project_Model project) throws Exception
	{
		return usersDao1.saveProject(project);
	}
	public boolean updateProject(Project_Model project) throws Exception{
		return usersDao1.updateProject(project);
	}
	public boolean updateTask(Task_Model t) throws ParseException{
		return usersDao1.updateTask(t);
	}
	public void saveMember(int projectid, int arr[]) throws MalformedURLException, XmlRpcException{
		usersDao1.saveMember(projectid, arr);
	}
	public List<Semester_Master> getAllSemester(){
		return usersDao1.getAllSemester();
	}
	public List<Project_Category_Master> getProjectCategories(){
		return usersDao1.getProjectCategories();
	}
	public List<Batch_Master> getAllBatch(){
		return usersDao1.getAllBatch();
	}
	public List<Task_Master> getAllTask(){
		return usersDao1.getAllTask();
	}
	public Project_Master getProjectById(int id) throws Exception{
		return usersDao1.getProjectById(id);
	}
	public Task_Master getTaskById(int id){
		return usersDao1.getTaskById(id);
	}
	public int getProjectIdByTaskId(int taskId){
		return usersDao1.getProjectIdByTaskId(taskId);
	}
	public Map<Integer, String> getStudentSemester(int arr[]) throws Exception{
		return usersDao1.getStudentSemester(arr);
	}
	public Map<String, Float> pointCalculation(Map<Integer, String> mm, int t) throws Exception{
		return usersDao1.pointCalculation(mm,t);
	}
	public String getKitPoint(){
		return usersDao1.getKitPoint();
	}
	public List<Semester_Master> getStudent_Semester(int batch_id) throws XmlRpcException,
	MalformedURLException, ParseException{
		return usersDao1.getStudent_Semester(batch_id);
	}
	public String getSemesterByBatchId(int id) throws Exception{
		return usersDao1.getSemesterByBatchId(id);
	}
	public String getCurrentSemester(List<Semester_Master> semesters) throws ParseException{
		return usersDao1.getCurrentSemester(semesters);
	}
	public int[] getMembersIdByProjectId(int project_id)
	{
		return usersDao1.getMembersIdByProjectId(project_id);
	}
	public Project_Member getAMemberById (int id, int project_id)
	{
		return usersDao1.getAMemberById(id, project_id);
	}
	public String getKitPointByProjectId (int project_id)
	{
		return usersDao1.getKitPointByProjectId(project_id);
	}
	public Batch_Master getBatchById(int id){
		return usersDao1.getBatchById(id);
	}
	public int deleteProjectDetail(int id){
		return usersDao1.deleteProjectDetail(id);
	}
	public List<Task_Master> getAllTaskByProjectId(int id){
		return usersDao1.getAllTaskByProjectId(id);
	}
	public void deleteMemberByProjectId(int id){
		usersDao1.deleteMemberByProjectId(id);
	}
	public void deleteTaskByProjectId(int id){
		usersDao1.deleteTaskByProjectId(id);
	}
	public int editProfile(String email, String oldPassword, String newPassword, String name) throws Exception{
		return usersDao1.editProfile(email,oldPassword,newPassword,name);
	}

	public int deleteTaskDetail(int id) {
		return usersDao1.deleteTaskDetail(id);
	}
	public List<Student> getAllStudent(){
		return usersDao1.getAllStudent();
	}
	public List<Student> exchangeBatchValue(List<Student> givenStudent) throws Exception{
		return usersDao1.exchangeBatchValue(givenStudent);
	}
	public boolean validateStudent (Student studentValidate,List<Student> studentDB){
		return usersDao1.validateStudent(studentValidate, studentDB);
	}
	public List<Batch_Master> pullAllBatch() throws Exception{
		return usersDao1.pullAllBatch();
	}
	public boolean updateStudent() throws Exception{
		return usersDao1.updateStudent();
	}
	public boolean updateSemester()throws Exception{
		return usersDao1.updateSemester();
	}

	public Sms_Server_Info getSmsServerInfo() {
		
		return usersDao1.getSmsServerInfo();
	}
	public boolean updateServerInfo(Sms_Server_Info info)
	{
		return usersDao1.updateServerInfo(info);
	}
	public boolean saveServerInfo(Sms_Server_Info info)
	{
		return usersDao1.saveServerInfo(info);
	}

	public List<Project_Master> getProjectReporting(Project_Model project) throws ParseException {
		// TODO Auto-generated method stub
		return usersDao1.getProjectReporting(project);
	}
	public List<Task_Master> getTaskReporting(Task_Model task) throws ParseException{
		return usersDao1.getTaskReporting(task);
	}
	public void addPointStudent (int students[],int project_id,float point,int hours){
		usersDao1.addPointStudent(students, project_id, point, hours);
	}
	public List<Student> getAllPoint(int batch_id){
		return usersDao1.getAllPoint(batch_id);
	}
	public List<Student> getAllStudentByBatchId(int batch_id){
		return usersDao1.getAllStudentByBatchId(batch_id);
	}
	public List<KIT_Point_Student_Wise> updateAllPoint(int user_id) throws Exception{
		return usersDao1.updateAllPoint(user_id);
	}
	public boolean updatePoint(KIT_Point_Student_Wise point){
		return usersDao1.updatePoint(point);
	}
	public int getUserIdByName(String name){
		return usersDao1.getUserIdByName(name);
	}
	public void updatePointStudent (int students[],int project_id,float point,int hours){
		usersDao1.updatePointStudent(students, project_id, point, hours);
	}
	public List<KIT_Point_Student_Wise> getPointByProjectId(int project_id) throws Exception{
		return usersDao1.getPointByProjectId(project_id);
	}
	public User_Info getUserById(int id){
		return usersDao1.getUserById(id);
	}
	public boolean updatePointMember(KIT_Point_Student_Wise point){
		return usersDao1.updatePointMember(point);
	}
	public boolean createSkillset(Skillset_Master skillset){
		return usersDao1.createSkillset(skillset);
	}
	public List<Skillset_Master> getAllSkillset(){
		return usersDao1.getAllSkillset();
	}
	public void saveSkillset(int projectid, int arr[]) throws Exception{
		usersDao1.saveSkillset(projectid, arr);
	}
	public int[] getSkillsetByProjectId(int project_id)
	{
		return usersDao1.getSkillsetByProjectId(project_id);
	}
	public List<Project_Master> getProjectOnSkillset(int[] skill) throws Exception 
	{
		return usersDao1.getProjectOnSkillset(skill);
	}
	public int getUserByEmail(String email) {
		return usersDao1.getUserByEmail(email);
	}
	public boolean forgot_password_email_sending(String email){
		return usersDao1.forgot_password_email_sending(email);
	}
	public List<User_Info> check_valid_tocken(String token){
		return usersDao1.check_valid_tocken(token);
	}
	public Boolean reset_passwordd(Reset_Password pw){
		return usersDao1.reset_passwordd(pw);
	}
	public List<additional_hour> getAdditionalHourProjectId(int project_id) throws Exception{
		return usersDao1.getAdditionalHourProjectId(project_id);
	}
	public boolean additional_hour_submit(additional_hour hour){
		return usersDao1.additional_hour_submit(hour);
	}
	public boolean additional_hour_update(additional_hour p){
		return usersDao1.additional_hour_update(p);
	}
}


