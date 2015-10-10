package spring;

import org.activiti.engine.delegate.DelegateExecution;

public interface EmployeeService {

	String getMsg();

	String getExecutionMsg(DelegateExecution execution);

}
