package leaveflow;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class LeaderCompleteHandler implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		System.out.println("LeaderCompleteHandlerLeaderCompleteHandlerLeaderCompleteHandlerLeaderCompleteHandler");
		
	}

}
