package springtx;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springtx.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class LeaveFlowCtrl {

	@Resource
	private CustomerService customerService;

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private RepositoryService repositoryService;

	@Test
	public void deployBPNM() {

		Deployment deployment = this.repositoryService.createDeployment().name("SpringTx流程").addClasspathResource("springtx/SpringTxProcess.bpmn")
				.addClasspathResource("springtx/SpringTxProcess.png").deploy();

		System.out.println(deployment);

		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
		System.out.println("id:" + processDefinition.getId());
		System.out.println("name:" + processDefinition.getName());
	}

	@Test
	public void submit() {
		try {
			String creator = "小A";

			Map<String, Object> variablesMap = new HashMap<String, Object>(2);
			variablesMap.put("creator", creator);
			List<String> audits = new ArrayList<String>(3);
			audits.add("小E");
			audits.add("小D");
			variablesMap.put("audits", audits);

			ProcessInstance pi = runtimeService.startProcessInstanceByKey("SpringTxProcess", variablesMap);
			System.out.println("pid:" + pi.getId() + " activitiID:" + pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());
		} catch (ActivitiException exception) {
			System.out.println(exception);
		}
	}

	@Test
	public void completeTask() {
		String taskId = "42502";
		String user = "小E";
		this.customerService.completeTask(taskId, user);
	}
	
	@Test
	public void deleteProcessInst(){
		this.runtimeService.deleteProcessInstance("32501", "无");
	}
}
