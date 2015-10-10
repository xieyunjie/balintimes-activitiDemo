package spring;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;

public class AuditsTask {

	public void dosomeTask(DelegateTask task) { 
		
		System.out.println("AuditsTask AuditsTask AuditsTask");
		System.out.println(task);
		System.out.println("");
		
	}
	
	public void dosomeExe(DelegateExecution execution){
		System.out.println("AuditsTask execution AuditsTask execution");
		System.out.println(execution);
		System.out.println("");
	}
}
