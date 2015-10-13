package spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfoQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LoopWFCtrl {

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryService repositoryService;

	@Test
	public void deployBPNM() {

		Deployment deployment = this.repositoryService.createDeployment().name("LoopWF流程").addClasspathResource("spring/LoopWF.bpmn")
				.addClasspathResource("spring/LoopWF.png").deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	private String bizKey = "crm-ba1a1840-6d6c-11e5-b1fb-c86000a05d5f";

	@Test
	public void submitBPNM() {

		try {

			String creator = "小A";

			Map<String, Object> variablesMap = new HashMap<String, Object>(2);
			variablesMap.put("creator", creator);
			List<String> audits = new ArrayList<String>(3);
			audits.add("小E");
			audits.add("小D");
			variablesMap.put("audits", audits);
			
			List<String> candidateUsers = new ArrayList<String>(3); 
			
			candidateUsers.add("小X");
			candidateUsers.add("小Y");
			candidateUsers.add("小Z"); 
			variablesMap.put("adminAudit", candidateUsers); 
			
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("LoopWFProcess", bizKey, variablesMap);
			System.out.println("pid:" + pi.getId() + " activitiID:" + pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());

//			// this.taskService.createTaskQuery().processInstanceBusinessKey(bizKey).singleResult();
//			Task task = this.taskService.createTaskQuery().taskAssignee(creator).orderByTaskCreateTime().desc().list().get(0);
//			this.taskService.claim(task.getId(), creator);
//			this.taskService.complete(task.getId());
//			System.out.println("完成提交");
		} catch (ActivitiException exception) {

			System.out.println(exception);
			// TODO: handle exception
		}
	}

	@Test
	public void queryAndCompleteTask() {
		String creator = "小A";

		Task task = this.taskService.createTaskQuery().taskCandidateOrAssigned(creator).orderByTaskCreateTime().desc().list().get(0);
		this.taskService.claim(task.getId(), creator);
		this.taskService.complete(task.getId());

		System.out.println(task.getId());
	}

	@Test
	public void getTask() {
		String assignee = "小E";

		List<Task> tasks = taskService.createTaskQuery().active().taskAssignee(assignee).orderByTaskCreateTime().asc().list();

		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());
			System.out.println(" name:" + task.getName());
			System.out.println(" createtime:" + task.getCreateTime());
			System.out.println(" assignee:" + task.getAssignee());
		}
	}

	@Test
	public void completeTask() {
		String taskId = "2518";
		this.taskService.complete(taskId);
	}

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void TestService() {
		System.out.println(employeeService.getMsg());
	}

	/**
	 * 中止/激活流程定义 中止只能阻止流程发起，已发起的不影响。
	 * */
	@Test
	public void suspendProcessDef() {
		// this.repositoryService.suspendProcessDefinitionByKey("LoopWFProcess");
		this.repositoryService.activateProcessDefinitionByKey("LoopWFProcess");
		System.out.println("suspend");

	}

	/**
	 * 暂停流程，暂停后再查询个人任务时，需要加入 .active()，如：
	 * taskService.createTaskQuery().active().
	 * taskAssignee(assignee).orderByTaskCreateTime().asc().list()
	 * */
	@Test
	public void suspendProcessInst() {
		ProcessInstance pi = this.runtimeService.createProcessInstanceQuery().suspended().processInstanceBusinessKey(bizKey).singleResult();
		// this.runtimeService.deleteProcessInstance(processInstanceId,
		// deleteReason);
		// this.runtimeService.suspendProcessInstanceById(pi.getId());

		this.runtimeService.activateProcessInstanceById(pi.getId());
	}

	@Test
	public void showTaskDetail() {
		Task task = this.taskService.createTaskQuery().taskId("5003").singleResult();
		
		System.out.println(task);
		
		List<IdentityLink> list =this.taskService.getIdentityLinksForTask(task.getId());
		
		for(IdentityLink identityLink:list ){
			
			System.out.println("userId="+identityLink.getUserId());
			System.out.println("taskId="+identityLink.getTaskId());
			System.out.println("piId="+identityLink.getGroupId());
			System.out.println("######################"); 
		}  
	}
	
	@Test
	public void operateActiviti(){
		
//		List<Task> list = this.taskService.createTaskQuery().processInstanceBusinessKey("crm:81d17651-6e65-11e5-b1fb-c86000a05d5f").list();
//		List<Task> list = this.taskService.createTaskQuery().processInstanceBusinessKeyLike("%crm%").list();
//		for (Task task : list) {
//			System.out.println(task);
//		}
		
		
        TaskInfoQuery taskInfoQuery = this.taskService.createTaskQuery().taskCandidateOrAssigned("59e2be4d-23b7-11e5-970b-c86000a05d5f");
        taskInfoQuery = taskInfoQuery.processInstanceBusinessKeyLike("%" + "crm" + "%");
        Long totalcount = taskInfoQuery.count();

        List<Task> taskList = taskInfoQuery.orderByTaskCreateTime().asc().listPage((1 - 1) * 20, 20);

        for (Task task : taskList) {
        	System.out.println(task);
        }
		
	}

}
