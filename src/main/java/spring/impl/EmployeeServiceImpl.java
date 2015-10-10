package spring.impl;

import org.activiti.engine.delegate.DelegateExecution;

import spring.EmployeeDao;
import spring.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao employeeDao;

	/**
	 * #{employeeService.getMsg()}
	 * */
	public String getMsg() {

		System.out.println("getMsg getMsg getMsg getMsg");

		return "come from EmployeeService";
	}

	/**
	 * #{employeeService.getExecutionMsg(execution)}
	 * */
	public String getExecutionMsg(DelegateExecution execution) {
		// TODO Auto-generated method stub

		System.out.println(execution);
		System.out.println("getExecutionMsg getExecutionMsg getExecutionMsg");
		employeeDao.setEmployee();
		System.out.println("");
		return "come from getExecutionMsg";
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

}
