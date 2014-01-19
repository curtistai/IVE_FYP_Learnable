package com.curtis.fyp.lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInformation;
import org.json.JSONArray;
import org.json.JSONObject;

import com.asmack.ActivityMain;
import com.asmack.MessageReceiver;
import com.asmack.OnMessageListener;
import com.curtis.fyp.Login;
import com.curtis.fyp.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.view.View.OnClickListener;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

public class LessonMainLeft extends Fragment implements OnClickListener,
		OnMessageListener, AdapterView.OnItemClickListener {
	VideoView videoview;
	ListView listview;
	String[] listItems;
	ToggleButton toggleButton;
	int toggleClick = 1;
	String url = Login.mediaServerIp + "/live/" + LessonMain.lessonID;
	String vodUrl = Login.vodServerIP + "/" + LessonMain.lessonID + ".mp4";

	// Chat
	private String TAG = "chat";
	private Thread mThread;

	private final int RECEIVE = 1;

	private Button send;
	private EditText record, etMessage;

	ArrayAdapter<String> buddylist;
	ArrayList<String> occpantsArray;

	public static MultiUserChat muc;
	private MessageReceiver mUpdateMessage;

	public static String multiUserRoom = LessonMain.lessonID + "@conference."
			+ Login.imDomain;// TODO to be confirm

	/************************** life cycle ********************************/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.lesson_main_left, container);
		initUserInterFace(result);

		occpantsArray = new ArrayList<String>();
		muc_accessRoom();
		updateListView(); // Update User list in the chat room
		loadVideo();
		return result;
	}

	public void onDestroy() {
		muc_leaveRoom();
		super.onDestroy();
	}

	/******************** UI Config ***********************************/
	private void initUserInterFace(View result) {
		videoview = (VideoView) result
				.findViewById(R.id.lesson_main_left_videoView);
		listview = (ListView) result
				.findViewById(R.id.lesson_main_left_buddyList);
		send = (Button) result.findViewById(R.id.lesson_main_left_send);
		record = (EditText) result.findViewById(R.id.lesson_main_left_record);
		etMessage = (EditText) result
				.findViewById(R.id.lesson_main_left_message);
		toggleButton = (ToggleButton) result
				.findViewById(R.id.lesson_main_left_toggleButton);

		// / listview.setAdapter(ActivityMain.getRosterToListView(getActivity()
		// .getApplicationContext()));
		// ActivityMain.setUp();

		record.setVisibility(result.GONE);
		send.setOnClickListener(this);
		toggleButton.setOnClickListener(this);
	}

	private void loadVideo() {
		// TODO to be confirm
		if (LessonMain.live.equals("1")) { // Live
			videoview.setVideoURI(Uri.parse(url));
		} else { // Vod
			videoview.setVideoURI(Uri.parse(vodUrl));
			MediaController mediaController = new MediaController(getActivity());
			mediaController.setAnchorView(videoview);
		}
		videoview.requestFocus();
		videoview.start();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.lesson_main_left_send: {
			String msg = etMessage.getText().toString();
			if (!"".equals(msg)) {
				try {
					muc.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		}
			break;
		case R.id.lesson_main_left_toggleButton: {
			if (toggleClick % 2 == 0) {
				record.setVisibility(view.GONE);
				listview.setVisibility(view.VISIBLE);
				toggleClick = 0;
			} else {
				record.setVisibility(view.VISIBLE);
				listview.setVisibility(view.GONE);
			}
			toggleClick++;
		}
			break;
		default:
			break;
		}
		updateListView();

	}

	private void updateListView() {
		occpantsArray.clear();
		Iterator<String> occupants = muc.getOccupants();

		String name = "";
		System.out.println("Number of occupants in ChatRoom"
				+ muc.getOccupantsCount());
		while (occupants.hasNext()) {
			String tempName = occupants.next();
			System.out.println("occupants name : " + name);
			if (tempName.equals(Login.loginID)) {
				continue;
			}
			occpantsArray.add(tempName.substring(tempName.indexOf("/") + 1));
		}
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					buddylist.notifyDataSetChanged();
				}
			});
		} catch (NullPointerException e) {
			occpantsArray.add("No People.");
			// buddylist.notifyDataSetChanged();

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// Click User Name to Open Private Chat
		LessonMainRightChat lessonMainRightGroupChat = (LessonMainRightChat) getFragmentManager()
				.findFragmentById(R.id.LessonMainRightChat);
		lessonMainRightGroupChat.startByLessonMainLeft(occpantsArray.get(arg2),
				muc);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.animator.fade_in,
				android.R.animator.fade_out);
		ft.show(fm.findFragmentById(R.id.LessonMainLeft));
		ft.hide(fm.findFragmentById(R.id.LessonMainRightAskQuestion));
		ft.show(fm.findFragmentById(R.id.LessonMainRightChat));
		ft.hide(fm.findFragmentById(R.id.LessonMainRightWhiteBoard));
		ft.hide(fm.findFragmentById(R.id.LessonMainRightPowerpoint));
		ft.commit();
	}

	/******************** MUC Handler ***********************************/

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case RECEIVE: {
				Bundle bd = msg.getData();
				String from = bd.getString("from");
				String body = bd.getString("body");
				receiveMsg(from, body);
			}
				break;
			default:
				break;
			}
		}
	};

	class ChatPacketListener implements PacketListener {
		private String _number;
		private Date _lastDate;
		private MultiUserChat _muc;
		private String _roomName;

		public ChatPacketListener(MultiUserChat muc) {
			_number = "0";
			_lastDate = new Date(0);
			_muc = muc;
			_roomName = muc.getRoom();
		}

		@Override
		public void processPacket(Packet packet) {
			Message message = (Message) packet;
			String from = message.getFrom();

			if (message.getBody() != null) {
				DelayInformation inf = (DelayInformation) message.getExtension(
						"x", "jabber:x:delay");
				Date sentDate;
				if (inf != null) {
					sentDate = inf.getStamp();
				} else {
					sentDate = new Date();
				}

				Log.w(TAG,
						"Receive old message: date="
								+ sentDate.toLocaleString() + " ; message="
								+ message.getBody());

				android.os.Message msg = new android.os.Message();
				msg.what = RECEIVE;
				Bundle bd = new Bundle();
				bd.putString("from", from);
				bd.putString("body", message.getBody());
				msg.setData(bd);
				handler.sendMessage(msg);
			}
		}
	}

	@Override
	public void processMessage(Message message) {
		receiveMsg(message.getFrom(), message.getBody());
	}

	private void receiveMsg(String from, String msg) {
		Log.v(TAG, "receiveMsg():msg=" + msg);
		String[] fromString = from.split("/");
		if (fromString.length == 1) {
			// Filter out Useless Message
			// record.setText(record.getText() + "System Message" + " : " + msg
			// + "\n");
		} else if (!(msg.length() < 5)) {
			// Json Handler
			if (msg.substring(0, 5).equals("<ppt>")) { // Powerpoint
				try {
					LessonMainRightPowerpoint ppt = (LessonMainRightPowerpoint) getFragmentManager()
							.findFragmentById(R.id.LessonMainRightPowerpoint);
					ppt.handleJson(msg.substring(5));
				} catch (Exception e) {
					System.out
							.println("PowerPoint cannot handle since it was not created");
				}

			} else if (msg.substring(0, 5).equals("<whb>")) {
				try {

					LessonMainRightWhiteBoard whb = (LessonMainRightWhiteBoard) getFragmentManager()
							.findFragmentById(R.id.LessonMainRightWhiteBoard);
					whb.loadJsonFromIM(msg.substring(5));
				} catch (Exception e) {
					System.out
							.println("White Board cannot handle since it was not created");
				}

			} else {
				record.setText(fromString[1] + " : " + msg + "\n"
						+ record.getText());
			}
		} else {
			record.setText(fromString[1] + " : " + msg + "\n"
					+ record.getText());

		}
	}

	/*****************
	 * MUC Setting
	 * 
	 * @throws XMPPException
	 **************/
	public void muc_accessRoom() {
		try {
			muc = new MultiUserChat(ActivityMain.connection, multiUserRoom);
			try {
				if (LessonMain.isTeacher()) {
					muc_createRoom(muc);
				}
			} catch (Exception e) {
				Log.i("groupChat", "Room may exist already");
			}
			muc.join(Login.mCurrentAccount);
			occpantsArray.add("No People");
			buddylist = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, occpantsArray);
			listview.setAdapter(buddylist);
			listview.setOnItemClickListener(this);
			PacketListener peopleListener = new PacketListener() {

				@Override
				public void processPacket(Packet packet) {
					if (packet
							.getClass()
							.toString()
							.equalsIgnoreCase(
									"class org.jivesoftware.smack.packet.Presence")) {
						Presence p = (Presence) packet;

						try {
							System.out.println("Presence from: " + p.getFrom());
							System.out.println("Presence :" + p.toString());
							updateListView();

							String[] username = p.getFrom().split("/");
							String t[] = username[0].split("@");
							if (p.toString().equalsIgnoreCase("available")) {
								getActivity().runOnUiThread(new Runnable() {
									public void run() {
										record.setText("** Welcome new Student attend the room **"
												+ "\n" + record.getText());
									}
								});

							} else if (p.toString().equalsIgnoreCase(
									"unavailable")) {
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
			muc.addParticipantListener(peopleListener);
			Log.v(TAG, "join success");

		} catch (XMPPException e) {
			e.printStackTrace();
		}

		ChatPacketListener chatListener = new ChatPacketListener(muc);
		muc.addMessageListener(chatListener);
	}

	public void muc_createRoom(MultiUserChat muc) throws XMPPException {
		muc.create(LessonMain.lessonID);
		// Send an empty room configuration form which indicates that we want
		// an instant room
		muc.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));

	}

	private void muc_leaveRoom() {
		muc.leave();
	}

}
