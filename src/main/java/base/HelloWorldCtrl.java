package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import sun.misc.ClassLoaderUtil;

public class HelloWorldCtrl {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deployZip() {  
System.out.println(this.getClass().getResource("CRMAuditProcess.zip").getPath());
//			ZipInputStream zipInputStream = new ZipInputStream(this.getClass().getResourceAsStream("CRMAuditProcess.zip"));
//			Deployment deployment = processEngine.getRepositoryService()
//					.createDeployment().name("helloword流程").addZipInputStream(zipInputStream).deploy();
//			System.out.println("OKOK"); 

	}
	@Test
	public void deploy() {

		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment().name("helloword流程")
				.addClasspathResource("base/HelloWorld.bpmn")
				.addClasspathResource("base/HelloWorld.png").deploy();

		System.out.println(deployment);

	}

	/**
	 * 流程定义 会报错 生成SQL如此？select distinct RES.* from ACT_RE_PROCDEF RES order by
	 * order by RES.ID_ asc LIMIT ? OFFSET ?
	 * 
	 * attention: mybatis版本不能最新，现使用3.2.8；否则所有的查询都会出错
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
	 * 启动流程 -- 开始 -- 下个执行人为(第一步)
	 * */
	@Test
	public void startProcess() {
		// 实例
		ProcessInstance pi = processEngine.getRuntimeService()
				.startProcessInstanceByKey("HelloWorld");

		System.out.println("pid:" + pi.getId() + " activitiID:"
				+ pi.getActivityId() + " pdID:" + pi.getProcessDefinitionId());
	}

	/**
	 * 办理人查询自己的办理任务
	 * */
	@Test
	public void queryMyTask() {
		String assignee = "第三步";
		
		// 返回的是列表 对个人
		List<Task> list = processEngine.getTaskService().createTaskQuery()
				.taskAssignee(assignee).orderByTaskCreateTime().asc().list();
		
		for (Task task : list) {
			System.out.println(" id:" + task.getId());// 任务ID
			System.out.println(" id:" + task.getName());
			System.out.println(" id:" + task.getCreateTime());
			System.out.println(" id:" + task.getAssignee());
		}
	}
	
	/**
	 * 办理任务
	 * */
	@Test
	public void handleTask() {
		//String taskID = "104"; // 此ID可以由上一步查得
		//String taskID = "5102";
		String taskID = "15004";
		// 办理任务
		processEngine.getTaskService().complete(taskID);		
	}
	

}
