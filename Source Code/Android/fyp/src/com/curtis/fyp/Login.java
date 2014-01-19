package com.curtis.fyp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import com.asmack.ActivityMain;
import com.asmack.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	public static String defaultIP = "192.168.1.108";
	// Server Address
	public static String webServerIp = "http://" + defaultIP + "/learnable";
	public static String mediaServerIp = "rtsp://" + defaultIP + ":1935";
	public static String vodServerIP = "http://" + defaultIP + "/learnable/vod";
	public static String imIp = defaultIP;
	public static String sipIp = defaultIP;
	public static String imDomain = "kiu-nb-7"; // TODO

	// End Server Address
	public static String loginID = "";
	public static String loginPassword = "";
	public static String loginPosition = "";

	Button loginButton;
	EditText ipTextBox, userIDTextBox, userPassWordTextBox;
	
	private final String ACCOUNT_KEY = "login_account";
	private final String PASSWORD_KEY = "login_password";
	
	public static Util util;
	public static String mCurrentAccount;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Login.util = new Util(this);
		
		ipTextBox = (EditText) findViewById(R.id.ipTextBox);
		userIDTextBox = (EditText) findViewById(R.id.userIDTextBox);
		userPassWordTextBox = (EditText) findViewById(R.id.userPassWordTextBox);
		loginButton = (Button) findViewById(R.id.loginButton);
		
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					loginID = userIDTextBox.getText().toString();
					loginPassword = userPassWordTextBox.getText().toString();
					defaultIP = ipTextBox.getText().toString();

					System.out.println("getSessionID()");
					getSessionID();
					
					System.out.println("loginToIM()");
					loginToIM();
					
					Intent intent = new Intent(Login.this, FypActivity.class);
					startActivity(intent);

				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(
							getApplicationContext(),
							"Login is not successful. Please check you password.",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
	}

	public void getSessionID() throws ClientProtocolException, IOException,
			JSONException {
		Toast.makeText(getApplicationContext(), "Logging in",
				Toast.LENGTH_SHORT).show();

		HttpPost httppost = new HttpPost(webServerIp
				+ "/Controller/loginController.php");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("myusername", userIDTextBox
				.getText().toString()));
		nameValuePairs.add(new BasicNameValuePair("mypassword",
				userPassWordTextBox.getText().toString()));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
		String t;
		HttpResponse httpResponse = new DefaultHttpClient().execute(httppost);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpResponse.getEntity().getContent()));
		String retSrc = reader.readLine();
		while ((t = reader.readLine()) != null) {
			retSrc += t;
		}
		reader.close();
		System.out.println(retSrc);
		
		JSONObject result = new JSONObject(retSrc);
		String sessionId = (String) result.get("sessionid");
		String validation = (String) result.get("validation");
		loginPosition = (String) result.get("position");
	}

	public void loginToIM() throws XMPPException {
		String address = imIp;
		int port = Integer.parseInt(getResources().getString(R.string.imPort));
		ActivityMain.setConn(address, port);

		ActivityMain.connection.connect(); // connect
		String account = userIDTextBox.getText().toString();
		String password = userPassWordTextBox.getText().toString();

		Login.util.saveString(ACCOUNT_KEY, account);
		Login.util.saveString(PASSWORD_KEY, password);

		ActivityMain.connection.login(account, password);// login

		System.out.println("login success");
		Login.mCurrentAccount = account;
		System.out.println(ActivityMain.connection.getUser());
		Presence presence = new Presence(Presence.Type.available);
		ActivityMain.connection.sendPacket(presence);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
