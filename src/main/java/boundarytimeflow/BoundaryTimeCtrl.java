package boundarytimeflow;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BoundaryTimeCtrl {

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryService repositoryService;

	@Test
	public void deployBPNM() {

		Deployment deployment = this.repositoryService.createDeployment().name("BoundaryTimeFlow")
				.addClasspathResource("boundarytimeflow/BoundaryTimeProcess.bpmn").addClasspathResource("boundarytimeflow/BoundaryTimeProcess.png").deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void submitBPNM() {
		try {
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("BoundaryTimeProcess");
			System.out.println("pid:" + pi.getId() + " activitiID:" + pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());
			Task task = this.taskService.createTaskQuery().taskAssignee("小A").singleResult();
			this.taskService.complete(task.getId());
			
			Thread.sleep(130 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
