package hq.king.activity;

import hq.king.transport.TransportObject;
import hq.king.util.Constants;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

	
	
	@Override//�������ж���Ҫ�õ��Ķ����ʵ��������onCreate()�У���ΪonCreateֻ��ִ��һ�Σ����Ǹ�Activity������Ȼ�����´�����
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override//�˴��Ұѹ㲥��ע�����onStart()�У�����onCreate()��Ҳ���ԣ�����Ҫ��ҵ���߼�
	//��Ϊ�˴��Ĺ㲥��Ҫ��Ϊ����Activity�н�������MsgService�е���Ϣ������һ���ǵ���Activity����
	//���û�����ʱ����getMessage()��������÷���onStart()�У���Ϊ����Activity����ǰ̨ʱ�����onStart()
	//ע����ʱ��һ���ڶ�Ӧ�ķ������onCreate()---onDestroy(),onStart()--onStop().
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(Constants.ACTION_MSG);
		registerReceiver(MsgReceiver, intentFilter);
	}

	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		unregisterReceiver(MsgReceiver);
	}



	//�㲥�����ߣ����ڽ�������MsgService����Ϣ��Activity��service��ͨ��ͨ������
	//�㲥�����߻�bindService�ķ�ʽ�������ActivityҪ����ͬһ��Service����Ϣ������ù㲥�����߸���
	//�˴����õľ��ǹ㲥������
	BroadcastReceiver MsgReceiver=new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			TransportObject msg=(TransportObject)intent.getSerializableExtra(Constants.MSG);
			getMessage(msg);
			
		}};
	protected abstract void getMessage(TransportObject msg);

}
