package loopcardinality;

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

public class LoopCardinalityCtrl {

	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	@Test
	public void deploy() {

		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("LoopCardinality流程")
				.addClasspathResource("loopcardinality/LoopCardinality.bpmn")
				.addClasspathResource("loopcardinality/LoopCardinality.png")
				.deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId(deployment.getId())
				.orderByProcessDefinitionVersion().desc().singleResult();
		// .orderByProcessDefinitionVersion().desc().singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void startProcess() {
		String assignee = "小A";
		Map<String, Object> variablesMap = new HashMap<String, Object>(2);
		variablesMap.put("takeCount", 3);
		// 实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("BoundaryProcess", variablesMap);

		System.out.println("启动完成 pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());

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
	public void submit() {
		String taskId = "15005";
		this.processEngine.getTaskService().complete(taskId);
	}

	@Test
	public void getBTask() {

		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.taskAssignee("小B").orderByTaskCreateTime().asc().list();
		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// 任务ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}
	}

	@Test
	public void complete() {
		String taskId = "22502";
		this.processEngine.getTaskService().complete(taskId);
	}

}
