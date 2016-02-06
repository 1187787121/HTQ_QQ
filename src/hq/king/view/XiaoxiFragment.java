package hq.king.view;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.MemoryHandler;

import hq.king.activity.ChatActivity;
import hq.king.activity.R;
import hq.king.adapter.RecentChatAdapter;
import hq.king.app.MyApplication;
import hq.king.client.Client;
import hq.king.client.ClientInputThread;
import hq.king.client.MessageListener;
import hq.king.entity.RecentChatEntity;
import hq.king.entity.User;
import hq.king.transport.TransportObject;
import hq.king.util.Constants;
import hq.king.util.MyDate;
import hq.king.view.MyListView.OnDeleteListener;
import android.R.integer;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class XiaoxiFragment extends Fragment {
private Context mContext;
private View mBaseView;
private RelativeLayout relative_w,relative_l;
private TextView w_textView,l_textView;
private MyListView mRecentListView;
private MyApplication application;
private static int xiaoxiNum=0;
private RecentChatAdapter mRecentAdapter;
private LinkedList<RecentChatEntity> mRecentList;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_xiaoxi, null);
		initRelative();
		return mBaseView;
	}
	
	void initRelative()
	{
//����Fragment�ж���Ŀؼ�����ʹ��findViewById(),�����Ƚ�fragment�Ĳ���
//mBaseView=inflater.inflate����������Ȼ�� mBaseView.findViewById().		
		w_textView=(TextView) mBaseView.findViewById(R.id.fragment_xiaoxi_name);
		l_textView=(TextView) mBaseView.findViewById(R.id.fragment_xiaoxi_name_l);
		
		//������Ҳ���Կ�������application��������ȫ�ֶ���ĺô����������ڶ����ͬ�����֮�䴦��ö���
		//���磬�˴�listView������adapter���ڸ�fragment�д���ģ�����listview��ˢ������MainActivity
		//�д���ģ�������ChatActivity�е��û����send��ť�󣬷��ص�xiaoxiFragment����ʱ��Ӧ������ʾ�û�
		//����Ϣ������ChatActivity��ҲҪ����RecentListView�����ݣ����ڶ�����֮��Ҫ������Ϣ�����԰�RencentAdapter
		//����Application���ȫ�ֵ��������б���
		application=(MyApplication) getActivity().getApplication();
		mRecentListView=(MyListView) mBaseView.findViewById(R.id.fragment_xiaoxi_recent_chat_listView);
		mRecentListView.setAdapter(application.getmRecentAdapter());
		mRecentListView.setOnDeleteListener(new OnDeleteListener() {
			
			public void onDelete(int index) {
				// TODO Auto-generated method stub
				application.getmRecentList().remove(index);
				application.getmRecentAdapter().notifyDataSetChanged();
			}
		});
		
		
		
		
		relative_w=(RelativeLayout) mBaseView.findViewById(R.id.xiaoxi_relative_w);
		relative_l=(RelativeLayout) mBaseView.findViewById(R.id.xiaoxi_relative_l);
		relative_w.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String friend_name=w_textView.getText().toString();
				Intent intent=new Intent(getActivity(),ChatActivity.class);
				User user=new User();
				user.setName(friend_name);
				intent.putExtra("user",user);
				startActivity(intent);
			}
		});
		relative_l.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String friend_name=l_textView.getText().toString();
				Intent intent=new Intent(getActivity(),ChatActivity.class);
				User user=new User();
				user.setName(friend_name);
				intent.putExtra("user",user);
				startActivity(intent);
				
			}
		});
		
	}
/*	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		application=(MyApplication) getActivity().getApplication();
		mRecentList = new LinkedList<RecentChatEntity>();
		mRecentAdapter = new RecentChatAdapter(getActivity().getApplicationContext(),
				mRecentList);
	//	final ArrayList<Map<String, Object>> adArrayList=new ArrayList<Map<String, Object>>();
	 //   final SimpleAdapter mAdapter=new SimpleAdapter(getActivity().getApplicationContext(), adArrayList,R.layout.current_online_listview_item,new String[]{"avatar","name","id"},new int[]{R.id.activity_current_onlie_listview_item_head,R.id.activity_current_onlie_listview_item_name,R.id.activity_current_onlie_listview_item_id});
		
		mRecentListView=(ListView) mBaseView.findViewById(R.id.fragment_xiaoxi_recent_chat_listView);
	//	mRecentListView.setAdapter(application.getmRecentAdapter());
		mRecentListView.setAdapter(mRecentAdapter);
		//mRecentListView.setAdapter(new SimpleAdapter(mContext,null,R.layout.recent_chat_item,new String[]{},new int[]{}));
		//���������Ҫ��
		final Handler mHandler=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what==0x12)
				{
					//application.getmRecentAdapter().notifyDataSetChanged();
					mRecentAdapter.notifyDataSetChanged();
					//mAdapter.notifyDataSetChanged();
					MediaPlayer.create(getActivity(), R.raw.msg).start();
					Toast.makeText(getActivity(),"�ף�recentchat����ϢŶ ", 0).show();
				}
				
			}
			
		};
		//��ò�Ҫ��recentChat����Ϣ��������⣬��Ϊ�����Ļ�����ChatActivity�е�getMessage������ͻ
		//�����û���chatActivity����ʱ���ܵ�Message��Ϣ�ᱻ���������Ϣ���߳����أ�������Ϣ������ȶ�ȡ��
		//���յ�����Ϣ������ԭ��֪����������Ϊ����ֱ�������̴߳�����û���ù㲥����ʽ�����Ի��һЩ
		
	    Client client=application.getClient();
		ClientInputThread cit=client.getClientInputThread();
		if(cit!=null)
		{
			 Log.i("xiaoxiFragmetn","this sentence is in cit!=null");
			cit.setMessageListener(new MessageListener() {
				
				public void getMessage(TransportObject msg) {
					
					if(msg!=null&&msg instanceof TransportObject)
					{
						switch (msg.getType()) {
						case MESSAGE:
				            xiaoxiNum++;
				            Log.i("xiaoxiFragmetn","this is xiaoxiNum"+String.valueOf(xiaoxiNum));
							application.setRecentNum(xiaoxiNum);// ���浽ȫ�ֱ���
							String message = (String) msg.getObject();
						//	ChatMsgEntity entity= new ChatMsgEntity("",MyDate.getDateEN(),
							//		message,-1,true);// �յ�����Ϣ
						//	messageDB.saveMsg(msg.getFromUser(), entity);// ���浽���ݿ�
							//Toast.makeText(getActivity(),"�ף�����ϢŶ " + msg.getFromUser() + ":" + message, 0).show();// ��ʾ�û�
							
							//User user = userDB.selectInfo(msg.getFromUser());// ͨ��id��ѯ��Ӧ���ݿ�ú�����Ϣ
					
							RecentChatEntity entity = new RecentChatEntity(msg.getFromUser(),
									0,xiaoxiNum, msg.getName(), MyDate.getDate(),message);
							//application.getmRecentAdapter().remove(entity);// ���Ƴ��ö���Ŀ������ӵ��ײ�
							//application.getmRecentList().addFirst(entity);// ����ӵ��ײ�
						//	mRecentAdapter.remove(entity);
							mRecentList.addFirst(entity);
							
							
							 Map<String, Object> map=new HashMap<String,Object>();
							 map.put("head",R.drawable.mine_avator);
							 map.put("name", msg.getName());
							 map.put("id",msg.getFromUser());
							 adArrayList.add(map);
							Message message2=new Message();
							message2.what=0x12;
							mHandler.sendMessage(message2);
				            break;
						}
						
						
					}
				}
			});
			 
		}
		else {
			Toast.makeText(getActivity(),"�ף����������쳣", 0).show();
		}
		
	}*/
	
}
