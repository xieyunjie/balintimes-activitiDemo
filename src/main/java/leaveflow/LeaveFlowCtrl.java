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
				.createDeployment().name("leaveflow����")
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
	 * �������񣬲��趨�ύ��ִ����<л�ƽ�>
	 * */
	@Test
	public void startProcess() {
		String assignee = "л�ƽ�";
		Map<String, Object> variablesMap = new HashMap<String, Object>(2);
		variablesMap.put("days", 5); // �������
		variablesMap.put("creator", assignee); // �ύִ����
		// ʵ��
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("LeaveFlow", variablesMap);

		System.out.println("������� pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());

		// ��ѯ<л�ƽ�>ִ�е�
		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).orderByTaskCreateTime().asc().list();

		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// ����ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}
	}

	/**
	 * �������� �����ǰ�ڵ㵱�д���<���̱���>���жϣ�һ��Ҫ�������ֵ�����򣬽ڵ�ֻ�����·��ص�ǰ�ڵ�
	 * */
	@Test
	public void handleTask() {
//		 String taskID = "15006";
//		 // �������� л�ƽ�(creatorִ�к�,�ᴥ��leaveflow.LeaderExecutor)
//		 processEngine.getTaskService().complete(taskID);

		String taskID = "17502";
		Map<String, Object> variablesMap = new HashMap<String, Object>(1);
		variablesMap.put("pass", "1");
		// ͨ�����������ȡ������ʵ��ID
		Task task = processEngine.getTaskService().createTaskQuery()
				.taskId(taskID).singleResult();
		String processInsID = task.getProcessInstanceId();

		// �������� �Ŷ��������(����ִ�к�,�ᴥ��leaveflow.LeaderExecutor)
		processEngine.getTaskService().addComment(taskID, processInsID,
				"������׼ѽ?����");
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
