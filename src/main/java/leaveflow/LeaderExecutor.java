package leaveflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class LeaderExecutor implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		// TODO Auto-generated method stub
		
		System.out.println(" ### LeaderExecutor ### process creator is " + delegateTask.getVariable("creator"));
		String assignee = "A主管";
		
		
		// 指定主管执行人，此处可以查找当前人主管
		delegateTask.setAssignee(assignee);
	}

}
