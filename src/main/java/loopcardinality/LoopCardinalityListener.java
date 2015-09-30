package loopcardinality;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class LoopCardinalityListener implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub 

		int takeCount = (int) delegateTask.getVariable("takeCount");
		String assignee = "";
		switch (takeCount) {
		case 0:
			assignee = "СB";
			break;
		case 1:
			assignee = "СC";
			break;
		case 2:
			assignee = "СD";
			break;
		case 3:
			assignee = "СE";
			break;
		case 4:
			assignee = "СF";
			break;
		default:
			assignee = "СB";
			break;
		}

		delegateTask.setAssignee(assignee);
		delegateTask.setVariable("takeCount", --takeCount);

	}

}
