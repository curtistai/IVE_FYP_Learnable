package com.curtis.fyp.lesson;

import com.curtis.fyp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TeacherPrepareStartLesson extends Activity implements
		OnClickListener {
	EditText lessonID;
	Button go;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teacherpreparelesson);

		lessonID = (EditText) findViewById(R.id.teacher_prepare_lesson_editText);
		go = (Button) findViewById(R.id.teacher_prepare_lesson_go);
		go.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, LessonMain.class);
		intent.putExtra("lessonID", lessonID.getText().toString()); // TODO need
																	// to
		// change
		intent.putExtra("live", "1");
		intent.putExtra("position", "teacher");
		this.startActivity(intent);

		this.finish();

	}
}