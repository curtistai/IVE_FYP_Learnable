package com.curtis.fyp.lesson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class LessonChoiceLeftListView extends ListFragment {
	String jsonLink = Login.webServerIp
			+ "/Controller/lessonAttendCat.php?myusername=" + Login.loginID;
	ListView list;
	String[] listItems;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.lesson_choice_left_list_view,
				container);
		return result;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getJSON();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setItemChecked(position, true);

		LessonChoiceMiddleListView middle = (LessonChoiceMiddleListView) getFragmentManager()
				.findFragmentById(R.id.LessonChoiceMiddleListView);
		middle.reloadList(listItems[position]);
	}

	private void getJSON() {
		Toast.makeText(getActivity().getApplicationContext(),
				"Updating Your Lesson List ... Please be Patient",
				Toast.LENGTH_SHORT).show();

		try {

			HttpPost httppost = new HttpPost(jsonLink);
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httppost);
			String retSrc = EntityUtils.toString(httpResponse.getEntity());

			JSONObject jsonObject = new JSONObject(retSrc);
			JSONArray jsonArray = jsonObject.getJSONArray("lesson_cat");
			listItems = new String[jsonArray.length()];
			for (int i = 0; i < listItems.length; i++) {
				JSONObject json_data = jsonArray.getJSONObject(i);
				listItems[i] = json_data.getString(i + 1 + "");
			}
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, listItems));

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity().getApplicationContext(),
					"Error downloading your lesson list ", Toast.LENGTH_SHORT)
					.show();

		}
	}

}
