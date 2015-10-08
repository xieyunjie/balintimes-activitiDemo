package spring;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class AuditsListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5830299443001023710L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub

		Object auditObject = delegateTask.getVariable("audit");
		delegateTask.setAssignee(auditObject.toString());

		System.out.println("AuditsListener:" + auditObject);
	}

}
