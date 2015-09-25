package leaveflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


public class ManagerExecutor implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
		System.out.println(" ### ManagerExecutor ### process creator is " + delegateTask.getVariable("creator"));
		
		String manager ="开发部总经理";
		delegateTask.setAssignee(manager);

	}

}
