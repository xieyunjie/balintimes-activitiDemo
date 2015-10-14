package springtx;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;

public class LeaveFlowListener {

	public void getAudits(DelegateTask task) { 
		
		List<String> audits = new ArrayList<String>(3);
		audits.add("小E");
		audits.add("小D"); 
		
		task.setVariableLocal("audits", audits);
	}

}
