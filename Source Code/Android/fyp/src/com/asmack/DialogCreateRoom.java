package com.asmack;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
/*
public class DialogCreateRoom extends Activity{

public static final String ROOM_NAME = "ROOM_NAME";
private Button btnCreate;
private Button btnCancel;
private EditText editRoomName;

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.room_name_input);
	editRoomName = (EditText)findViewById(R.id.roomName);
	btnCreate = (Button)findViewById(R.id.create);
	btnCreate.setOnClickListener(new OnClickListener(){
		public void onClick(View v) {
			returnRoomName();
		}
	});
	btnCancel = (Button)findViewById(R.id.cancel);
	btnCancel.setOnClickListener(new OnClickListener(){
		public void onClick(View v) {
			cancelDialog();
		}
	});
}

private void returnRoomName(){
	Intent resultIntent = new Intent(this, ActivityMultiChat.class);
	resultIntent.putExtra(ROOM_NAME, editRoomName.getText().toString());
	setResult(Activity.RESULT_OK, resultIntent);
	finish();
}

	private void cancelDialog(){
		finish();
	}
}*/