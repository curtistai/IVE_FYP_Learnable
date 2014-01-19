package com.asmack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author zhanghaitao
 * @date 2011-7-5
 * @version 1.0
 */
/*
public class ActivityMultiChat extends Activity {

	private ListView listview;
	private HostRoomAdapter hostRoomAdapter;

	private List<HostedRoom> roominfos = new ArrayList<HostedRoom>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.multichat);

		listview = (ListView) findViewById(R.id.listview);
		roominfos.clear();
		hostRoomAdapter = new HostRoomAdapter(this);
		listview.setAdapter(hostRoomAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

				HostedRoom room = roominfos.get(position);
				System.out.print("Join Room");
				Intent intent = new Intent(ActivityMultiChat.this, ActivityMultiRoom.class);
				intent.putExtra("jid", room.getJid());
				startActivity(intent);
			}
		});
		updateHostRoom();
	}

	public final int MENU_NEWROOM = 1;
	public final int MENU_REFRESHROOM = 2;
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, MENU_NEWROOM, Menu.NONE, "New Room");
		menu.add(1, MENU_REFRESHROOM, Menu.NONE, "Refresh Room");

		return super.onCreateOptionsMenu(menu);
	}
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 // TODO Auto-generated method stub
		 switch(item.getItemId()){
		 	case (MENU_NEWROOM):
		 		startCreateRoomDialog();			
		 		break;
		 	case (MENU_REFRESHROOM):
				roominfos.clear();
		 		updateHostRoom();
		 }
		 return true;
	 }
	 
	 private static final int DIALOG_CREATE_ROOM = 0;
	 private void startCreateRoomDialog() {
		 Intent intent = new Intent(this,DialogCreateRoom.class);
		 startActivityForResult(intent, DIALOG_CREATE_ROOM);
	 }
	 
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);
		 String roomName = "";
		 switch(requestCode) {
			 case (DIALOG_CREATE_ROOM) : {
				 if (resultCode == Activity.RESULT_OK) {			
					 roomName = data.getStringExtra(DialogCreateRoom.ROOM_NAME);			
				 }
				 createRoom(roomName);
				 break;
			 }
		 }
	 }
	 
	 public void createRoom(String roomName){
		 try {
				MultiUserChat muc = new MultiUserChat(ActivityMain.connection, roomName+"@conference.fyp.mikecheung.hk");  
				muc.create(roomName);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
				Toast.makeText(listview.getContext(), "Chat Room "+roomName+" is created successfully", Toast.LENGTH_SHORT).show();
				roominfos.clear();
				updateHostRoom();
	 }
	 
	private void updateHostRoom() {
		Collection<HostedRoom> hostrooms;
		try {
			hostrooms = MultiUserChat.getHostedRooms(ActivityMain.connection, "conference.fyp.mikecheung.hk");
			for (HostedRoom entry : hostrooms) {
				System.out.println(entry.getName() + " - " + entry.getJid());
				roominfos.add(entry);
			}
			hostRoomAdapter.notifyDataSetChanged();
		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}

	private class HostRoomAdapter extends BaseAdapter {
		private Context context;

		public HostRoomAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return roominfos.size();
		}

		@Override
		public Object getItem(int position) {
			return roominfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private class ViewHolder {
			TextView name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			HostedRoom room = roominfos.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LinearLayout layout = new LinearLayout(context);

				holder.name = new TextView(context);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.addView(holder.name);
				convertView = layout;
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(room.getName());

			return convertView;
		}

	}

}*/
