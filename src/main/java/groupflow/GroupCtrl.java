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
				.createDeployment().name("groupflow����")
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
		String assignee = "л�ƽ�";
		Map<String, Object> variablesMap = new HashMap<String, Object>(1);
		variablesMap.put("creator", assignee); // �ύִ����
		// ʵ��
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("groupProcess", variablesMap);

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

	@Test
	public void submitBill() {
		String taskId = "37505";
		this.processEngine.getTaskService().complete(taskId);
	}

	/**
	 * ��ѯ�������� �ڸ���δ��ȡʱ�����������ǲ鲻�˵ġ�
	 * */
	@Test
	public void findAdminTask() {

		String userId = "A����Ա";
		List<Task> tasks = this.processEngine.getTaskService()
				.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// ����ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}

	}

	/**
	 * ��ѯ������
	 * */
	@Test
	public void findGroupList() {
		String userId = "A����Ա";
		List<Task> tasks = this.processEngine.getTaskService()
				.createTaskQuery().taskCandidateUser(userId).list();
		for (Task task : tasks) {
			System.out.println(" id:" + task.getId());// ����ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" ִ����:" + task.getAssignee());
		}
	}

	/**
	 * ��ȡ����
	 * */
	@Test
	public void takeTask() {

		String taskId = "30002";
		String userId = "����ԱA";

		processEngine.getTaskService().claim(taskId, userId);

		Task task = this.processEngine.getTaskService().createTaskQuery()
				.taskId(taskId).singleResult();

		System.out.println(" id:" + task.getId());// ����ID
		System.out.println(" id:" + task.getName());
		System.out.println(" id:" + task.getCreateTime());
		System.out.println(" ִ����:" + task.getAssignee());
	}

	
	/**
	 * �����ʹ����û������ȡ����Ҳ�ǿ���ͨ���ġ�
	 * */
	@Test
	public void completeTask() {
		String taskID = "40002";
		processEngine.getTaskService().complete(taskID);
	}
}
