package hq.king.broadcast;



import hq.king.util.CommonPreferenceUtil;
import hq.king.util.HttpUtil;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkChangeReceiver extends BroadcastReceiver {

	public NetworkChangeReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
		if(networkInfo==null||!networkInfo.isAvailable())
		 {
			new AlertDialog.Builder(context)
			.setTitle("��ܰ��ʾ")
			.setMessage("��ǰ���粻���ã�������������")
			.setPositiveButton("ǰ����",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog,
								int which) {
							Intent intent = new Intent(
									android.provider.Settings.ACTION_WIRELESS_SETTINGS);
							context.startActivity(intent);
						}
					}).setNegativeButton("ȡ��", null).create().show();
		//	Toast.makeText(context, "��ǰ���粻���ã�������������",Toast.LENGTH_SHORT).show();
		}
		else {
			if(HttpUtil.getNetTypeName(context).equals("WIFI"))
			{
				CommonPreferenceUtil.setString(context, "NET", "WIFI");
				//CommonPreferenceUtil.setBoolean(context,"WIFI",true);
		//	Toast.makeText(context,HttpUtil.getIp(context),Toast.LENGTH_SHORT).show();
			}
			else {
				CommonPreferenceUtil.setString(context, "NET", "MOBILE");
				//CommonPreferenceUtil.setBoolean(context,"Mobile",true);
		//		Toast.makeText(context,HttpUtil.getMobileIP(),Toast.LENGTH_SHORT).show();
			}
		}
	
	}

}
