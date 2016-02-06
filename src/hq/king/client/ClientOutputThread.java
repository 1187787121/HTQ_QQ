package hq.king.client;

import hq.king.transport.TransportObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.util.Log;

public class ClientOutputThread extends Thread {

	private Socket mSocket;
	private ObjectOutputStream oos;
	private TransportObject msg;
	private boolean isStart;
	public ClientOutputThread(Socket socket) throws IOException
	{
		mSocket=socket;
		oos=new ObjectOutputStream(socket.getOutputStream());
		
	}
	public void setMsg(TransportObject msg)
	{

		this.msg=msg;
	}
	public void setStart(boolean start)
	{
		isStart =start;
		
	}
	public void run()
	{
		while(isStart)
		{
			//ע�⣬�����Ҳ����д��whileѭ���У���Ϊ���̵߳Ŀ�������client���п����ģ�����setMsg����
			//������Ҫʱ�ŵ��õģ��������ø��߳�һֱ���У�һֱ����д����һ���пյ���������������Ƿ�д
			if(msg!=null)
			{
				try {
					oos.writeObject(msg);
					oos.flush();
					msg=null;//�����һ������ȥ��������oos.writeObject��msg������һֱִ��
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			//Log.i("ClientOutputThread","this print is after while loop");
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	

}
