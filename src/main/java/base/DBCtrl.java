package base;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class DBCtrl {
	
	@Test
	public void createDB(){
		
		
		
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
		
		System.out.println(processEngine);
	}

}
