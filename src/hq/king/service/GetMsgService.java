package hq.king.service;

import java.io.IOException;
import java.io.ObjectStreamConstants;
import java.net.UnknownHostException;





import hq.king.app.MyApplication;
import hq.king.client.Client;
import hq.king.client.ClientInputThread;
import hq.king.client.ClientOutputThread;
import hq.king.client.MessageListener;
import hq.king.entity.User;
import hq.king.transport.TranObjectType;
import hq.king.transport.TransportObject;
import hq.king.util.Constants;
import hq.king.util.SharePreferenceUserInfoUtil;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class GetMsgService extends Service {
	private Client client;
	private boolean isStart;
	private SharePreferenceUserInfoUtil util;
	private ClientInputThread cit;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		client=((MyApplication) getApplication()).getClient();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		util = new SharePreferenceUserInfoUtil(getApplicationContext(),
				Constants.SAVE_USER);
	   
		new Thread(){
			   public void run()
			   {
					try {
						isStart=client.create();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			//�ڷ����н������Է������˵���Ϣ��Ȼ��ͨ���㲥����ʽ���ݸ���Ӧ��Activity�������ܷ������˵���Ϣһ����
			//�����У���Ϊ��������ں�̨һֱ����
									
				if(isStart)
				{
					cit=client.getClientInputThread();
					if(cit!=null)
					{
						cit.setMessageListener(new MessageListener() {
							
							public void getMessage(TransportObject msg) {
								
								if(msg!=null&&msg instanceof TransportObject)
								{
									//ͨ���㲥��Activity������Ϣ
									Intent intent=new Intent();
									intent.setAction(Constants.ACTION_MSG);
									intent.putExtra(Constants.MSG, msg);
								    sendBroadcast(intent);
								}
							}
						});
					}
					else {
						Log.i("GetMsgService","��������������ʱ����");
					//	Toast.makeText(getApplicationContext(), "��������������ʱ�������Ժ����ԣ�",0).show();
					}
				}
				   
			   }
		   }.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ClientOutputThread out=client.getClientOutputThread();
		TransportObject<User> msg=new TransportObject<User>(TranObjectType.LOGOUT);
		User user=new User();
		user.setId(Integer.parseInt(util.getId()));
		msg.setObject(user);
		out.setMsg(msg);
		//�رշ���ʱ�ر�client
		out.setStart(false);
		client.getClientInputThread().setStart(false);
		
		
		
	}
	
	
	
	

}
