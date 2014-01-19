package com.curtis.fyp.lesson;

import java.text.ParseException;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smack.packet.Message;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;
import com.example.android.sip.IncomingCallReceiver;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LessonMainRightChat extends Fragment implements
		View.OnTouchListener {

	/********** For SIP System *********/
	Chat chat = null;
	public String sipAddress = null;
	public SipManager manager = null;
	public SipProfile me = null;
	public SipAudioCall call = null;
	public IncomingCallReceiver callReceiver;

	private EditText textMsg;
	private EditText inputMsg;
	private Button sendButton;
	private TextView labelView;

	private static final int CALL_ADDRESS = 1;
	private static final int SET_AUTH_INFO = 2;
	private static final int UPDATE_SETTINGS_DIALOG = 3;
	private static final int HANG_UP = 4;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.lesson_main_right_chat,
				container);
		ToggleButton pushToTalkButton = (ToggleButton) result
				.findViewById(R.id.lesson_main_right_groupchat_pushToTalk);
		pushToTalkButton.setOnTouchListener(this);

		// Set up the intent filter. This will be used to fire an
		// IncomingCallReceiver when someone calls the SIP address used by this
		// application.
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.SipDemo.INCOMING_CALL");
		callReceiver = new IncomingCallReceiver();
		getActivity().registerReceiver(callReceiver, filter);

		// "Push to talk" can be a serious pain when the screen keeps turning
		// off.
		// Let's prevent that.
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		textMsg = (EditText) result
				.findViewById(R.id.lesson_main_right_groupchat_textMsg);
		inputMsg = (EditText) result
				.findViewById(R.id.lesson_main_right_groupchat_inputMsg);
		sendButton = (Button) result
				.findViewById(R.id.lesson_main_right_groupchat_send);
		labelView = (TextView) result
				.findViewById(R.id.lesson_main_right_groupchat_sipsysmsg);

		initializeManager();
		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					if (chat == null)
						throw new Exception();
					chat.sendMessage(inputMsg.getText().toString());
					textMsg.setText("You:" + inputMsg.getText().toString()
							+ "\n" + textMsg.getText().toString());
					inputMsg.setText("");
				} catch (XMPPException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("No user has been selected");
				}
			}

		});
		return result;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (call != null) {
			call.close();
		}

		closeLocalProfile();

		if (callReceiver != null) {
			getActivity().unregisterReceiver(callReceiver);
		}
	}

	public void initializeManager() {
		if (manager == null) {
			manager = SipManager.newInstance(getActivity());
		}

		initializeLocalProfile();
	}

	/**
	 * Logs you into your SIP provider, registering this device as the location
	 * to send SIP calls to for your SIP address.
	 */
	public void initializeLocalProfile() {
		if (manager == null) {
			return;
		}

		if (me != null) {
			closeLocalProfile();
		}

		String username = Login.loginID;
		String domain = Login.sipIp;
		String password = Login.loginPassword;

		try {
			SipProfile.Builder builder = new SipProfile.Builder(username,
					domain);
			builder.setPassword(password);
			me = builder.build();

			Intent i = new Intent();
			i.setAction("android.SipDemo.INCOMING_CALL");
			PendingIntent pi = PendingIntent.getBroadcast(getActivity()
					.getApplicationContext(), 0, i, Intent.FILL_IN_DATA);
			manager.open(me, pi, null);

			// This listener must be added AFTER manager.open is called,
			// Otherwise the methods aren't guaranteed to fire.

			manager.setRegistrationListener(me.getUriString(),
					new SipRegistrationListener() {
						public void onRegistering(String localProfileUri) {
							updateStatus("Registering with SIP Server...");
						}

						public void onRegistrationDone(String localProfileUri,
								long expiryTime) {
							updateStatus("Ready");
						}

						public void onRegistrationFailed(
								String localProfileUri, int errorCode,
								String errorMessage) {
							updateStatus("Registration failed.  Please check settings.");
						}
					});
		} catch (ParseException pe) {
			updateStatus("Connection Error.");
		} catch (SipException se) {
			updateStatus("Connection error.");
		}
	}

	/**
	 * Closes out your local profile, freeing associated objects into memory and
	 * unregistering your device from the server.
	 */
	public void closeLocalProfile() {
		if (manager == null) {
			return;
		}
		try {
			if (me != null) {
				manager.close(me.getUriString());
			}
		} catch (Exception ee) {
			Log.d("WalkieTalkieActivity/onDestroy",
					"Failed to close local profile.", ee);
		}
	}

	/**
	 * Make an outgoing call.
	 */
	public void initiateCall() {

		updateStatus(sipAddress);

		try {
			SipAudioCall.Listener listener = new SipAudioCall.Listener() {
				// Much of the client's interaction with the SIP Stack will
				// happen via listeners. Even making an outgoing call, don't
				// forget to set up a listener to set things up once the call is
				// established.
				@Override
				public void onCallEstablished(SipAudioCall call) {
					call.startAudio();
					call.setSpeakerMode(true);
					call.toggleMute();
					updateStatus(call);
				}

				@Override
				public void onCallEnded(SipAudioCall call) {
					updateStatus("Ready.");
				}
			};

			call = manager.makeAudioCall(me.getUriString(), sipAddress,
					listener, 30);

		} catch (Exception e) {
			Log.i("WalkieTalkieActivity/InitiateCall",
					"Error when trying to close manager.", e);
			if (me != null) {
				try {
					manager.close(me.getUriString());
				} catch (Exception ee) {
					Log.i("WalkieTalkieActivity/InitiateCall",
							"Error when trying to close manager.", ee);
					ee.printStackTrace();
				}
			}
			if (call != null) {
				call.close();
			}
		}
	}

	/**
	 * Updates the status box at the top of the UI with a messege of your
	 * choice.
	 * 
	 * @param status
	 *            The String to display in the status box.
	 */
	public void updateStatus(final String status) {
		// Be a good citizen. Make sure UI changes fire on the UI thread.
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				TextView labelView = (TextView) getActivity().findViewById(
						R.id.lesson_main_right_groupchat_sipsysmsg);
				labelView.setText(status);
			}
		});
	}

	/**
	 * Updates the status box with the SIP address of the current call.
	 * 
	 * @param call
	 *            The current, active call.
	 */
	public void updateStatus(SipAudioCall call) {
		String useName = call.getPeerProfile().getDisplayName();
		if (useName == null) {
			useName = call.getPeerProfile().getUserName();
		}
		updateStatus("Voice Chating with " + useName);
	}

	/**
	 * Updates whether or not the user's voice is muted, depending on whether
	 * the button is pressed.
	 * 
	 * @param v
	 *            The View where the touch event is being fired.
	 * @param event
	 *            The motion to act on.
	 * @return boolean Returns false to indicate that the parent view should
	 *         handle the touch event as it normally would.
	 */
	public boolean onTouch(View v, MotionEvent event) {
		if (call == null) {
			return false;
		} else if (event.getAction() == MotionEvent.ACTION_DOWN && call != null
				&& call.isMuted()) {
			call.toggleMute();
		} else if (event.getAction() == MotionEvent.ACTION_UP
				&& !call.isMuted()) {
			call.toggleMute();
		}
		return false;
	}

	public void startByLessonMainLeft(String callNumber, MultiUserChat muc) {
		sipAddress = callNumber + "@" + Login.sipIp; // TODO to be confrm
		initiateCall();
		labelView.setText("You are talking to \"" + callNumber
				+ "\" privately.");
		textMsg.setText("");

		chat = muc.createPrivateChat(muc.getRoom() + "/" + callNumber,
				new MessageListener() {
					@Override
					public void processMessage(Chat chat, Message message) {
						// System.out
						// .println("Private Chat: Received message from "
						// + message.getFrom() + "-"
						// + message.getBody());
						receiveMsg(message.getFrom(), message.getBody());

					}

					private void receiveMsg(final String from, final String msg) {
						String[] fromString = from.split("/");
						if (fromString.length == 1) {
							// Filter out Useless Message
						} else
							getActivity().runOnUiThread(new Runnable() {
								public void run() {
									EditText textMsg = (EditText) getActivity()
											.findViewById(
													R.id.lesson_main_right_groupchat_textMsg);
									textMsg.setText(from + " : " + msg + "\n"
											+ textMsg.getText().toString());
								}
							});

					}
				});

	}

}
