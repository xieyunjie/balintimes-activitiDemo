package boundarysignalflow;

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

public class SignalCtrl {
	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	@Test
	public void deploy() {

		Deployment deployment = processEngine
				.getRepositoryService()
				.createDeployment()
				.name("boundarysignalflow流程")
				.addClasspathResource(
						"boundarysignalflow/BoundarySignalProcess.bpmn")
				.addClasspathResource(
						"boundarysignalflow/BoundarySignalProcess.png")
				.deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().desc().singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void startProcess() {
		String assignee = "小A";
		// 实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("BoundarySignalProcess");
 
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
	public void completeTask() {
		String taskId = "60004";
		this.processEngine.getTaskService().complete(taskId);
	}

	@Test
	public void sendSignal() { 
		 
		// 此处要注意，如果如果使用以下方法：有多个流程实例，若运行至一个节点上，全部实例均会接收到。
		// 可以用于例如政策性改变时，所有流程实例都需要接收当中信号
		// this.processEngine.getRuntimeService().signalEventReceived("contractChangeSignal"); 
		
		// 需要从taskid 查找到 executionId
		String taskId = "65004";
		String executionId = this.processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult().getExecutionId();
		this.processEngine.getRuntimeService().signalEventReceived("contractChangeSignal", executionId); 
	}
}
