package spring;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

public class AuditsListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5830299443001023710L;
	
	@Autowired
	private EmployeeService employeeService;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		Object auditObject = delegateTask.getVariable("audit");
		delegateTask.setAssignee(auditObject.toString());
		
		System.out.println(employeeService.getMsg());

		System.out.println("AuditsListener:" + auditObject);
	} 

}
