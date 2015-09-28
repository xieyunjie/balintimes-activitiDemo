package leaveflow;

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

public class LeaveFlowCtrl {

	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	@Test
	public void deploy() {

		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("leaveflow流程")
				.addClasspathResource("leaveflow/LeaveFlowProcess.bpmn")
				.addClasspathResource("leaveflow/LeaveFlowProcess.png")
				.deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().desc().singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void deleteDeployment() {
		processEngine.getRepositoryService().deleteDeployment("32601", true);
	}

	/**
	 * 开启任务，并设定提交的执行人<谢云杰>
	 * */
	@Test
	public void startProcess() {
		String assignee = "谢云杰";
		Map<String, Object> variablesMap = new HashMap<String, Object>(2);
		variablesMap.put("days", 5); // 请假天数
		variablesMap.put("creator", assignee); // 提交执行人
		// 实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("LeaveFlow", variablesMap);

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

	/**
	 * 办理任务 如果当前节点当中存在<流程变量>的判断，一定要传入相关值，否则，节点只会重新返回当前节点
	 * */
	@Test
	public void handleTask() {
//		 String taskID = "15006";
//		 // 办理任务 谢云杰(creator执行后,会触发leaveflow.LeaderExecutor)
//		 processEngine.getTaskService().complete(taskID);

		String taskID = "17502";
		Map<String, Object> variablesMap = new HashMap<String, Object>(1);
		variablesMap.put("pass", "1");
		// 通过个人任务获取到流程实例ID
		Task task = processEngine.getTaskService().createTaskQuery()
				.taskId(taskID).singleResult();
		String processInsID = task.getProcessInstanceId();

		// 办理任务 团队主管审核(主管执行后,会触发leaveflow.LeaderExecutor)
		processEngine.getTaskService().addComment(taskID, processInsID,
				"不能批准呀?！！");
		// processEngine.getTaskService().setVariablesLocal(taskID,
		// variablesMap);
		processEngine.getTaskService().complete(taskID, variablesMap);
	}

	@Test
	public void completeTask() {
		String taskID = "20005";
		Map<String, Object> variablesMap = new HashMap<String, Object>(1);
		variablesMap.put("pass", "0");
		processEngine.getTaskService().complete(taskID,variablesMap);
	}
}
