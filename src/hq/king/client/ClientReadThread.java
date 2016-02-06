package hq.king.client;

import hq.king.transport.TranObjectType;
import hq.king.transport.TransportObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.net.UnknownHostException;












import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class ClientReadThread extends Thread {
	private Handler mHandler;
    public Handler mReceiveHandler;//���ģʽ�����ڲ�����Ϊ�ⲿ��ĳ�Ա������ͨ���ⲿ�����Ϳ��Է��ʸ��ڲ������ķ���
    private InputStream mInputStreamReader;
	private BufferedReader mReader;
    private ObjectInputStream ois;
    private TransportObject  tranMsg;
   public ClientReadThread(Handler handler,InputStream inputStreamReader) throws StreamCorruptedException, IOException {
		// TODO Auto-generated constructor stub
		mHandler=handler;
		mInputStreamReader=inputStreamReader;
	//	ois=new ObjectInputStream(mInputStreamReader);
		//ע�⵱����ois��ʵ������䵥���ŵ�һ���߳���ʱ���Ǿ�Ҫע��ͻ������������ois��oos�������Ⱥ�˳��
		//��Ϊһ���ͻ���������������ȴ�����ois����ͻ��˵�oisʵ�����������̣߳�������ð�ois/oos��ʵ����
		//�����ŵ�һ���߳���
	}
    
	public void run()
	{
		try {
			ois=new ObjectInputStream(mInputStreamReader);
			//ע�⵱��������oosδ����ʱ�����������߳�
			//ע���������д��һ���߳��У���Ϊ�������ж�ȡ��Ϣ���ܻ��������
			//һ��������������ChatActivity�е��߳�Ҳ�ᱻ����������
			//���߳���mClientThread.start();�Ժ����䶼����ִ��
			//��ֱ��ִ��ChatActivity���߳��еĴ��룬����ChatActivity�д���
			//���߳�֮��Ĵ���
		} catch (StreamCorruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	new Thread(){
			public void run()
			{
			try {
					while((tranMsg=(TransportObject) ois.readObject()) != null)
					{
						if(tranMsg.getType()==TranObjectType.MESSAGE)
						{
							Message msg=new Message();
							msg.what=0x12;
							msg.obj=tranMsg;
							mHandler.sendMessage(msg);
						}else if(tranMsg.getType()==TranObjectType.LOGIN){
							Message msg=new Message();
							msg.what=0x34;
							msg.obj=tranMsg;
							mHandler.sendMessage(msg);
						}
					}
				} catch (OptionalDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
			
		}.start();
	}

}
