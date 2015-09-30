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
				.name("boundarysignalflow����")
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
		String assignee = "СA";
		// ʵ��
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("BoundarySignalProcess");
 
		System.out.println("������� pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());

		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).orderByTaskCreateTime().asc().list();

		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// ����ID
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
		 
		// �˴�Ҫע�⣬������ʹ�����·������ж������ʵ������������һ���ڵ��ϣ�ȫ��ʵ��������յ���
		// �����������������Ըı�ʱ����������ʵ������Ҫ���յ����ź�
		// this.processEngine.getRuntimeService().signalEventReceived("contractChangeSignal"); 
		
		// ��Ҫ��taskid ���ҵ� executionId
		String taskId = "65004";
		String executionId = this.processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult().getExecutionId();
		this.processEngine.getRuntimeService().signalEventReceived("contractChangeSignal", executionId); 
	}
}
