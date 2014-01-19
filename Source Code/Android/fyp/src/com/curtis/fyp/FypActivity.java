package com.curtis.fyp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FypActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		if (Login.loginPosition.equals("teacher")) {
			inflater.inflate(R.menu.teachermenu, menu);

		} else {
			inflater.inflate(R.menu.menu, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.lesson_menu:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this,
					com.curtis.fyp.lesson.LessonChoice2.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.teacher_lesson_menu:
			Intent intent2 = new Intent(this,
					com.curtis.fyp.lesson.TeacherPrepareStartLesson.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);

			return true;
		case R.id.logout:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}