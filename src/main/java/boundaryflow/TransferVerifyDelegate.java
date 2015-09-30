package boundaryflow;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class TransferVerifyDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		boolean result = (Boolean)execution.getVariable("result");
		if (result) {
			System.out.println("转账成功");
		} else {
			System.out.println("转账失败，抛出错误"); 
			throw new BpmnError("transferError");
		}
	}

}
