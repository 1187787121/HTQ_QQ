package hq.king.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.integer;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class HttpUtil {
	
	
	public static boolean uploadFile(File file,String url) throws ClientProtocolException, IOException
	{
		 HttpPost httpPost = new HttpPost(url); 
		 HttpClient httpClient=new DefaultHttpClient();
		 FileEntity fileEntity=new FileEntity(file, "binary/octet-stream");
		 httpPost.setEntity(fileEntity);
		 HttpResponse response=httpClient.execute(httpPost);
		 if(response != null&&response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
		    return true;
		 else
		   return false;
	}
	/**
	 * 
	 * @param url      
	 * @param saveFile ���صĴ�������ļ���λ��
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static boolean downloadFile(String url,File saveFile) throws ClientProtocolException, IOException
	{
		 HttpGet httpGet = new HttpGet(url); 
		 HttpClient httpClient=new DefaultHttpClient();
		 HttpResponse response=httpClient.execute(httpGet);
		 HttpEntity httpEntity=response.getEntity();
		 FileOutputStream os=new FileOutputStream(saveFile);
		 if(httpEntity!=null)
		 {
			 InputStream is=httpEntity.getContent();
			 byte [] b=new byte[1024*3];
			 int len=0;
             while((len=is.read(b))!=-1){
		       os.write(b,0,len);
		     }
             os.close();
             is.close();
            return true;
			 
		 }
		return false;

		
	}
	public static String getNetTypeName(Context context) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (connectivityManager != null) {
	        NetworkInfo activeNetworkInfo = connectivityManager
	                .getActiveNetworkInfo();
	        if (activeNetworkInfo == null) {
	            return "������";
	        }
	        if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
	            return activeNetworkInfo.getTypeName();
	        } else {
	            String typeName = activeNetworkInfo.getSubtypeName();
	            //Log.i("��������:", typeName);
	            if (typeName == null || typeName.length() == 0) {
	                return "δ֪����";
	            } else if (typeName.length() > 3) {
	                return activeNetworkInfo.getSubtypeName().substring(0, 4);
	            } else {
	                return activeNetworkInfo.getSubtypeName().substring(0,
	                        typeName.length());
	            }
	        }
	    } else {
	        return "������";
	    }
	}
	//��ȡwifi����ip��ַ
	public static String getIp(Context context) {
	    WifiManager wifiManager = (WifiManager) context
	            .getSystemService(Context.WIFI_SERVICE);
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	    int ipAddress = wifiInfo.getIpAddress();

	// ��ʽ��IP address�����磺��ʽ��ǰ��1828825280����ʽ����192.168.1.109
	    String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff),
	            (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
	            (ipAddress >> 24 & 0xff));
	    return ip;
	}
	
	
	//��ȡGSMip��ַ
	public static String getMobileIP() {
		  try {
		      for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
		          NetworkInterface intf = (NetworkInterface) en.nextElement();
		          for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
		              InetAddress inetAddress = enumIpAddr.nextElement();
		              if (!inetAddress.isLoopbackAddress()) {
		                  String ipaddress = inetAddress.getHostAddress().toString();
		                  return ipaddress;
		              }
		          }
		      }
		  } catch (SocketException ex) {
		      Log.e("getMobileIP", "Exception in Get IP Address: " + ex.toString());
		  }
		  return "";
		}

}
