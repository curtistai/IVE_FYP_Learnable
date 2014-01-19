package com.curtis.fyp.lesson;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LessonChoiceMiddleListView extends ListFragment {
	String jsonLink = Login.webServerIp;
	String jsonLink1 = "/Controller/lesson_attend.php?myusername="
			+ Login.loginID;
	String jsonLink2 = "&status=";
	String jsonLink3 = "&courseName=";

	ListView list;
	List<String> lessonList;
	List<String> lessonId;
	ArrayAdapter<String> adatper;
	String live; // 1- live 2-record

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.lesson_choice_left_list_view,
				container);
		return result;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lessonList = new ArrayList<String>();
		lessonId = new ArrayList<String>();
		adatper = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, lessonList);
		setListAdapter(adatper);
		live = "1";
		getJSON(jsonLink + jsonLink1 + jsonLink2 + "1" + jsonLink3, "");

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setItemChecked(position, true);

		if (lessonId.get(position) == "0") {
			Toast.makeText(getActivity().getApplicationContext(),
					"Lesson is not avaiable", Toast.LENGTH_SHORT).show();

		} else {
			Intent intent = new Intent(getView().getContext(), LessonMain.class);
			intent.putExtra("lessonID", lessonId.get(position)); // TODO need to
																	// change
			intent.putExtra("live", live);
			intent.putExtra("position", "student");

			getView().getContext().startActivity(intent);

			getActivity().finish();
		}
	}

	private void getJSON(String jsonLink1, String link2) {
		lessonList.clear();
		lessonId.clear();
		Toast.makeText(getActivity().getApplicationContext(),
				"Updating Your Lesson List ... Please be Patient",
				Toast.LENGTH_SHORT).show();

		try {
			System.out.println(jsonLink1
					+ java.net.URLEncoder.encode(link2, "utf8"));
			HttpPost httppost = new HttpPost(jsonLink1
					+ java.net.URLEncoder.encode(link2, "utf8"));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httppost);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());

			JSONObject jsonObject = new JSONObject(retSrc);
			JSONArray codeArray = jsonObject.getJSONArray("lesson");
			// listItems = new String[codeArray.length()];
			for (int i = 0; i < codeArray.length(); i++) {
				JSONObject lesson = codeArray.getJSONObject(i);
				// listItems[i] = lesson.getString("lessonName");
				lessonList.add(lesson.getString("lessonName"));
				lessonId.add(lesson.getString("lessonID"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			lessonList.clear();
			lessonId.clear();

			lessonList.add("No Lesson Available at this moment");
			lessonId.add("0");
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					adatper.notifyDataSetChanged();

				}
			});

		}
	}

	public void reloadList(String selected) {
		if (selected.equals("Live")) {
			live = "1";
			getJSON(jsonLink + jsonLink1 + jsonLink2 + "1" + jsonLink3, "");
		} else {
			live = "2";
			getJSON(jsonLink + jsonLink1 + jsonLink2 + "2" + jsonLink3,
					selected);
		}
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				adatper.notifyDataSetChanged();

			}
		});

	}

}
