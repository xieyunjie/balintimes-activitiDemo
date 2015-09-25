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
		String assignee = "A����";
		
		
		// ָ������ִ���ˣ��˴����Բ��ҵ�ǰ������
		delegateTask.setAssignee(assignee);
	}

}
