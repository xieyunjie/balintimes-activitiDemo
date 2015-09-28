package groupflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class GroupAuditListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7737777127448428325L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
		delegateTask.addCandidateUser("A管理员");
		delegateTask.addCandidateUser("B管理员"); 
		
	}

}
