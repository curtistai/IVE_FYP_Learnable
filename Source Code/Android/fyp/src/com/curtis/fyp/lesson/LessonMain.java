package com.curtis.fyp.lesson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.json.JSONArray;
import org.json.JSONObject;

import com.asmack.ActivityMain;
import com.curtis.fyp.Login;
import com.curtis.fyp.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class LessonMain extends Activity {
	Uri outputFileUri;
	private static final int ACTIVITY_SELECT_IMAGE = 100;
	private static final int ACTIVITY_TAKE_IMAGE = 101;
	public static String lessonID = "";
	public static String live = ""; // 1- live 2 -record
	public static String position = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Set Information
		lessonID = getIntent().getStringExtra("lessonID");
		live = getIntent().getStringExtra("live");
		// position = getIntent().getStringExtra("position");
		position = Login.loginPosition;

		// Create View
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_main);

		// Control Fragment Display
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.animator.fade_in,
				android.R.animator.fade_out);
		ft.hide(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
		ft.hide(fm.findFragmentById(R.id.LessonMainRightChat));
		ft.show(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
		ft.hide(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
		ft.hide(fm.findFragmentById(R.id.LessonMainRightAttendance));
		ft.commit();

		markAttend();
		sendLessonStatus();

	}

	public static boolean isTeacher() {
		// return true;//TODO Testing Purpose
		return position.equals("teacher");
	}

	public static boolean isLive() {
		return live.equals("1");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if (isTeacher()) {
			inflater.inflate(R.menu.teacherlesson, menu);
		} else {
			inflater.inflate(R.menu.lesson, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.animator.fade_in,
				android.R.animator.fade_out);

		switch (item.getItemId()) {
		case R.id.askQuestion:
			ft.show(fm.findFragmentById(R.id.LessonMainLeft));
			ft.show(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightChat));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAttendance));
			ft.commit();
			return true;
		case R.id.Chat: // Cast to individual Chat
			ft.show(fm.findFragmentById(R.id.LessonMainLeft));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
			ft.show(fm.findFragmentById(R.id.LessonMainRightChat));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAttendance));
			ft.commit();
			return true;
		case R.id.whiteBoard:
			ft.show(fm.findFragmentById(R.id.LessonMainLeft));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightChat));
			ft.show(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAttendance));
			ft.commit();
			return true;
		case R.id.powerPoint:
			ft.show(fm.findFragmentById(R.id.LessonMainLeft));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightChat));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
			ft.show(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAttendance));
			ft.commit();
			return true;
		case R.id.attendance:
			ft.show(fm.findFragmentById(R.id.LessonMainLeft));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightChat));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
			ft.hide(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
			ft.show(fm.findFragmentById(R.id.LessonMainRightAttendance));
			ft.commit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onDestroy() {
		markLeave();
		live = "2";
		sendLessonStatus();
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("hello", "Get Result");

		switch (requestCode) {
		case ACTIVITY_SELECT_IMAGE:
			if (resultCode == RESULT_OK) {

				Uri selectedImage = data.getData();
				InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(
							selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				LessonMainRightWhiteBoard myFragment = (LessonMainRightWhiteBoard) getFragmentManager()
						.findFragmentById(R.id.LessonMainRightWhiteBoard);
				myFragment.changeBackground(imageStream);
				// TODO
			}
			break;
		case ACTIVITY_TAKE_IMAGE:
			if (resultCode == RESULT_OK) {
				File imgFile = new File("/sdcard/image.jpg");

				if (imgFile.exists()) {

					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());
					// drawingSurface.setBackground(BitmapFactory
					// .decodeFile(imgFile.getAbsolutePath()));
					LessonMainRightWhiteBoard myFragment = (LessonMainRightWhiteBoard) getFragmentManager()
							.findFragmentById(R.id.LessonMainRightWhiteBoard);
					myFragment.changeBackground2(imgFile);

				}
			}
			break;

		}
	}

	private void markAttend() {
		sendAttendent(1);
	}

	private void markLeave() {
		sendAttendent(2);
	}

	private void sendAttendent(int status) {
		if (isTeacher()) {
			// No need to Send Attendance
		} else {
			try {
				HttpPost httppost = new HttpPost(
						Login.webServerIp
								+ "/Controller/studentAttendLessonMarkdown.php?myusername="
								+ Login.loginID + "&mylesson=" + lessonID
								+ "&attendstatus=" + status);
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httppost);
				String retSrc = EntityUtils.toString(httpResponse.getEntity());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private void sendLessonStatus() {
		if (isTeacher()) {
			try {
				HttpPost httppost = new HttpPost(Login.webServerIp
						+ "/Controller/setlessonOn.php?mylesson=" + lessonID
						+ "&status=" + live);
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httppost);
				String retSrc = EntityUtils.toString(httpResponse.getEntity());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
