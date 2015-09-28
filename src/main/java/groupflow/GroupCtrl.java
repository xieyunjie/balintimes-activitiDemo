package groupflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class GroupCtrl {
	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	@Test
	public void deploy() {

		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("groupflow流程")
				.addClasspathResource("groupflow/GroupProcess.bpmn")
				.addClasspathResource("groupflow/GroupProcess.png").deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().desc().singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void startProcess() {
		String assignee = "谢云杰";
		Map<String, Object> variablesMap = new HashMap<String, Object>(1);
		variablesMap.put("creator", assignee); // 提交执行人
		// 实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("groupProcess", variablesMap);

		System.out.println("启动完成 pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());

		// 查询<谢云杰>执行单
		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).orderByTaskCreateTime().asc().list();

		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// 任务ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}
	}

	@Test
	public void submitBill() {
		String taskId = "37505";
		this.processEngine.getTaskService().complete(taskId);
	}

	/**
	 * 查询个人任务 在个人未领取时，个个任务是查不了的。
	 * */
	@Test
	public void findAdminTask() {

		String userId = "A管理员";
		List<Task> tasks = this.processEngine.getTaskService()
				.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// 任务ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}

	}

	/**
	 * 查询组任务
	 * */
	@Test
	public void findGroupList() {
		String userId = "A管理员";
		List<Task> tasks = this.processEngine.getTaskService()
				.createTaskQuery().taskCandidateUser(userId).list();
		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// 任务ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" 执行人:" + task.getAssignee());
		}
	}

	/**
	 * 领取任务
	 * */
	@Test
	public void takeTask() {

		String taskId = "30002";
		String userId = "管理员A";

		processEngine.getTaskService().claim(taskId, userId);

		Task task = this.processEngine.getTaskService().createTaskQuery()
				.taskId(taskId).singleResult();

		System.out.println(" id:" + task.getId());// 任务ID
		System.out.println(" id:" + task.getName());
		System.out.println(" id:" + task.getCreateTime());
		System.out.println(" 执行人:" + task.getAssignee());
	}

	
	/**
	 * 这里，即使组里没有人领取任务，也是可以通过的。
	 * */
	@Test
	public void completeTask() {
		String taskID = "40002";
		processEngine.getTaskService().complete(taskID);
	}
}
