package com.asmack;

import java.util.Date;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.DelayInformation;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author zhanghaitao
 * @date 2011-7-11
 * @version 1.0
 */
/*
public class ActivityMultiRoom extends Activity implements OnClickListener,
		OnMessageListener {
	private String TAG = "chat";
	private Thread mThread;

	private final int RECEIVE = 1;

	private Button send;
	private EditText record, etMessage;
	private TextView chatTitle;

	private MultiUserChat muc;
	private MessageReceiver mUpdateMessage;

	private String jid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_main_left);

		send = (Button) findViewById(R.id.lesson_main_left_send);
		record = (EditText) findViewById(R.id.lesson_main_left_record);
		etMessage = (EditText) findViewById(R.id.lesson_main_left_message);
	//	chatTitle = (TextView)findViewById(R.id.chatTitle);

		send.setOnClickListener(this);

		jid = getIntent().getStringExtra("jid"); //TODO
		jid = "testroom@conference.kiu-nb-7";

		String multiUserRoom = jid;
		try {
			muc = new MultiUserChat(ActivityMain.connection, multiUserRoom);
			muc.join(Login.mCurrentAccount);

			Log.v(TAG, "join success");

		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ChatPacketListener chatListener = new ChatPacketListener(muc);
		muc.addMessageListener(chatListener);
		chatTitle.setText("Chat in room " + multiUserRoom.split("@")[0]);
		// mUpdateMessage = new MessageReceiver(multiUserRoom);
		// mUpdateMessage = new MessageReceiver("admin");
		// mUpdateMessage.setOnMessageListener(this);
		// mThread = new Thread(mUpdateMessage);
		// mThread.start();

	}

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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.lesson_main_left_send: {
			String msg = etMessage.getText().toString();
			if (!"".equals(msg)) {
				try {
					muc.sendMessage(msg);
				} catch (Exception e) {
				}
			}
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void processMessage(Message message) {
		receiveMsg(message.getFrom(), message.getBody());
	}

	private void receiveMsg(String from, String msg) {

		Log.v(TAG, "receiveMsg():from=" + from.split("/")[1]);
		Log.v(TAG, "receiveMsg():msg=" + msg);

		record.setText(record.getText() + from + ":" + msg + "\n");

	}

	@Override
	protected void onDestroy() {
		// try {
		// muc.destroy(null, null);
		// mUpdateMessage.flag = false;
		// mUpdateMessage.mCollector.cancel();
		// } catch (XMPPException e) {
		// // TODO Auto-generated catch block
		// Log.v(TAG, "Exception:", e);
		// }
		super.onDestroy();
	}

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

				Log.w(TAG, "Receive old message: date="
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
}
*/