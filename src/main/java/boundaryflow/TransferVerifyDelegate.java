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
			System.out.println("ת�˳ɹ�");
		} else {
			System.out.println("ת��ʧ�ܣ��׳�����"); 
			throw new BpmnError("transferError");
		}
	}

}
