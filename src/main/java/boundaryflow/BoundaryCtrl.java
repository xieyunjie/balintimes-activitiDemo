package boundaryflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class BoundaryCtrl {
	private ProcessEngine processEngine = ProcessEngines
			.getDefaultProcessEngine();

	@Test
	public void deploy() {
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("BoundaryProcess流程")
				.addClasspathResource("boundaryflow/BoundaryProcess.bpmn")
				.deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = processEngine
				.getRepositoryService().createProcessDefinitionQuery()
				.deploymentId(deployment.getId())
				.orderByProcessDefinitionVersion().desc().singleResult();
		// .orderByProcessDefinitionVersion().desc().singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());

		processEngine.getProcessEngineConfiguration().getJobExecutor().start();
	}

	@Test
	public void startProcess() throws InterruptedException {
		Map<String, Object> variablesMap = new HashMap<String, Object>(2);
		variablesMap.put("result", false);
		// 实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("BoundaryProcess", variablesMap);

		System.out.println("启动完成 pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());
	}

	@Test
	public void completeTask() {
		String taskId = "";
		this.processEngine.getTaskService().complete(taskId);
	}
}
