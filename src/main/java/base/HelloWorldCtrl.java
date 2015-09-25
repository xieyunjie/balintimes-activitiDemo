package base;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class HelloWorldCtrl {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	@Test
	public void deploy() {

		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("helloword����")
				.addClasspathResource("base/HelloWorld.bpmn")
				.addClasspathResource("base/HelloWorld.png").deploy();

		System.out.println(deployment);

	}

	/**
	 * ���̶��� �ᱨ�� ����SQL��ˣ�select distinct RES.* from ACT_RE_PROCDEF RES order by
	 * order by RES.ID_ asc LIMIT ? OFFSET ?
	 * 
	 * attention: mybatis�汾�������£���ʹ��3.2.8���������еĲ�ѯ�������
	 * */
	@Test
	public void queryProcessDefinition() {
		List<ProcessDefinition> pdList = processEngine.getRepositoryService()
				.createProcessDefinitionQuery().orderByProcessDefinitionId()
				.asc().list();

		for (ProcessDefinition processDefinition : pdList) {
			System.out.println("id:" + processDefinition.getId());
			System.out.println("name:" + processDefinition.getName());
		}
	}

	/**
	 * �������� -- ��ʼ -- �¸�ִ����Ϊ(��һ��)
	 * */
	@Test
	public void startProcess() {
		// ʵ��
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("HelloWorld");

		System.out.println("pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());
	}

	/**
	 * �����˲�ѯ�Լ��İ�������
	 * */
	@Test
	public void queryMyTask() {
		String assignee = "������";
		
		// ���ص����б� �Ը���
		List<Task> list = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).orderByTaskCreateTime().asc().list();
		
		for (Task task : list) {
			System.out.println(" id:" + task.getId());// ����ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}
	}
	
	/**
	 * ��������
	 * */
	@Test
	public void handleTask() {
		//String taskID = "104"; // ��ID��������һ�����
		//String taskID = "5102";
		String taskID = "7602";
		// ��������
		processEngine.getTaskService().complete(taskID);		
	}
	

}
