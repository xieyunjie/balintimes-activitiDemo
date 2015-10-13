package springtx.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.TaskService;

import springtx.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
 
	@Resource 
	private TaskService taskService;
	
	public void completeTask(String taskId,String user) {
		// TODO Auto-generated method stub
		taskService.claim(taskId, user);
		taskService.complete(taskId); 
	}

}
