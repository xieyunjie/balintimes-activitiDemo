package spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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

		Deployment deployment = this.repositoryService.createDeployment().name("LoopCardinality����").addClasspathResource("spring/LoopWF.bpmn")
				.addClasspathResource("spring/LoopWF.png").deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void submitBPNM() {

		String creator = "小A";
		String bizKey = "crm-ba1a1840-6d6c-11e5-b1fb-c86000a05d5f";

		Map<String, Object> variablesMap = new HashMap<String, Object>(2);
		variablesMap.put("creator", creator);
		List<String> audits = new ArrayList<String>(3);
		audits.add("小E");
		audits.add("小D"); 
		variablesMap.put("audits", audits); 
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("LoopWFProcess", bizKey, variablesMap);
		System.out.println("pid:" + pi.getId() + " activitiID:" + pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());


		// this.taskService.createTaskQuery().processInstanceBusinessKey(bizKey).singleResult();
		Task task = this.taskService.createTaskQuery().taskAssignee(creator).orderByTaskCreateTime().desc().list().get(0);
		this.taskService.complete(task.getId());
		System.out.println("完成提交");
	}
	
	@Test
	public void queryTask() {
		String creator = "小e";
		
		Task task = this.taskService.createTaskQuery().taskAssignee(creator).orderByTaskCreateTime().desc().list().get(0);
		this.taskService.complete(task.getId());
		
		System.out.println(task.getId());
	}

	@Test
	public void getTask() {
		String assignee = "小E";

		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().asc().list();

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
	
}
