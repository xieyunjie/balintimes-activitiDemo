package spring;

import org.activiti.engine.delegate.DelegateExecution;

public class AuditsExecution {
	
	public void dosomeExe(DelegateExecution execution){
		System.out.println("AuditsExecution AuditsExecution AuditsExecution");
		System.out.println(execution);
		System.out.println("");
	}

}
