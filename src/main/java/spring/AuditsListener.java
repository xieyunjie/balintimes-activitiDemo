package spring;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;   

public class AuditsListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5830299443001023710L;
	
	private Expression creatorEx;
	private Expression creatorX;
	private Expression creatorY;
	
	private Expression employeeServiceExpression;
	
//	private EmployeeService employeeService;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		Object auditObject = delegateTask.getVariable("audit"); 
		
		delegateTask.setAssignee(auditObject.toString()); 
		
		System.out.println("creatorEx === " + this.creatorEx.getValue(delegateTask).toString());
		System.out.println("creatorX === " + this.creatorX.getValue(delegateTask).toString());
		System.out.println("creatorY === " + this.creatorY.getValue(delegateTask).toString());
		EmployeeService employeeService = (EmployeeService) this.employeeServiceExpression.getValue(delegateTask);
		System.out.println("AuditsListener inject " + employeeService.getMsg()); 

		System.out.println("AuditsListener:" + auditObject);
		System.out.println("");
	}  

}
