package com.curtis.fyp.lesson;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LessonMainRightPowerpoint extends Fragment {
	String downloadpath = Login.webServerIp + "/ppt/" + LessonMain.lessonID
			+ ".txt";
	

	private static Gson _gson = new Gson();

	static ImageView imageView;
	static Gallery ga;
	InputStream in;
	BufferedReader reader;
	String line;
	InputStream instream;
	static Vector<Bitmap> slide;

	Button nextBtn, preBtn, goBtn;
	static EditText pageNumber;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.lesson_main_right_powerpoint,
				container);
		slide = new Vector<Bitmap>();
		download(downloadpath, "/temp.txt");

		try {
			// open the file for reading
			instream = new FileInputStream(
					Environment.getExternalStorageDirectory() + "/temp.txt");

			// if file the available for reading
			if (instream != null) {
				// prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);

				String line;

				// read every line of the file into the line-variable, on line
				// at the time
				int i = 1;
				while ((line = buffreader.readLine()) != null) {
					// line = buffreader.readLine();
					download(Login.webServerIp+line, "/page" + i + ".png");

					File imgFile = new File(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/page" + i + ".png");
					if (imgFile.exists()) {

						Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
								.getAbsolutePath());
						Bitmap newBitmap = myBitmap.copy(Bitmap.Config.ARGB_8888, true);
						slide.add(newBitmap);

					}
					i++;

					// do something with the line
				}
				instream.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// print stack trace.
		} finally {
			// close the file.

		}

		pageNumber = (EditText) result
				.findViewById(R.id.lesson_main_right_powerpoint_editText1);
		imageView = (ImageView) result
				.findViewById(R.id.lesson_main_right_powerpoint_ImageView01);
		ga = (Gallery) result
				.findViewById(R.id.lesson_main_right_powerpoint_Gallery01);
		ga.setAdapter(new ImageAdapter(getActivity()));

		ga.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (LessonMain.isTeacher()||!LessonMain.isLive()) {
					imageView.setImageBitmap(slide.get(ga.getSelectedItemPosition()));
					pageNumber.setText(ga.getSelectedItemPosition() + 1 + "");
					pageChange();
				}

			}

		});

		nextBtn = (Button) result
				.findViewById(R.id.lesson_main_right_powerpoint_nextBtn);

		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ga.getSelectedItemPosition() < ga.getCount() - 1) {
					ga.setSelection(ga.getSelectedItemPosition() + 1, true);
					imageView.setImageBitmap(slide.get(ga
							.getSelectedItemPosition()));
					pageChange();

				}
				pageNumber.setText(ga.getSelectedItemPosition() + 1 + "");
			}
		});

		preBtn = (Button) result
				.findViewById(R.id.lesson_main_right_powerpoint_previousBtn);

		preBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ga.getSelectedItemPosition() > 0) {
					ga.setSelection(ga.getSelectedItemPosition() - 1, true);
					imageView.setImageBitmap(slide.get(ga
							.getSelectedItemPosition()));
					pageNumber.setText(ga.getSelectedItemPosition() + 1 + "");
					pageChange();
				}
			}
		});

		goBtn = (Button) result
				.findViewById(R.id.lesson_main_right_powerpoint_jumpBtn);

		goBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int newPageNo = Integer.parseInt(pageNumber.getText()
						.toString()) - 1;

				if (newPageNo < ga.getCount() - 1 && newPageNo > 0) {
					ga.setSelection(newPageNo, true);
					imageView.setImageBitmap(slide.get(newPageNo));
					pageNumber.setText(ga.getSelectedItemPosition() + 1 + "");
					pageChange();

				}

			}
		});

		ga.setSelection(0);
		// imageView.setImageBitmap(slide.get(0));
		pageNumber.setText(ga.getSelectedItemPosition() + 1 + "");
		
		if(!LessonMain.isTeacher()&&LessonMain.isLive()){
			nextBtn.setVisibility(View.GONE);
			preBtn.setVisibility(View.GONE);
			goBtn.setVisibility(View.GONE);
			pageNumber.setEnabled(false);
			
		}
		imageView.setImageBitmap(slide.get(ga
				.getSelectedItemPosition()));

		return result;
	}

	public void pageChange() {
		ChangeHandler mp = null;
		mp = new PageChange();
		((PageChange) mp).setPage(ga.getSelectedItemPosition());
		sendCommand(toJSON(mp));
	}

	public static Object fromJSON(String json) {
		Log.d("Json", json);
		int i = json.indexOf("_cname");
		int j = json.indexOf(',');
		if (j < 0)
			j = json.length() - 1;
		// get the class name
		String className = json.substring(i + 9, j - 1);
		try {
			Class c = Class.forName(className);
			return _gson.fromJson(json, c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean download(String url, String filename) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		try {
			File root = Environment.getExternalStorageDirectory();
			BufferedOutputStream bout = new BufferedOutputStream(
					new FileOutputStream(root.getAbsolutePath() + filename));

			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			// textView1.append("status.getStatusCode(): " +
			// status.getStatusCode() + "\n");
			Log.d("Test", "Statusline: " + status);
			Log.d("Test", "Statuscode: " + status.getStatusCode());

			HttpEntity entity = response.getEntity();
			// textView1.append("length: " + entity.getContentLength() + "\n");
			// textView1.append("type: " + entity.getContentType() + "\n");
			Log.d("Test", "Length: " + entity.getContentLength());
			Log.d("Test", "type: " + entity.getContentType());

			entity.writeTo(bout);

			bout.flush();
			bout.close();
			// textView1.append("OK");
			return true;

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			// textView1.append("URISyntaxException");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			// textView1.append("ClientProtocolException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// textView1.append("IOException");
		}
		return false;
	}

	public static String toJSON(Object o) {
		String msg = "";
		if (o != null) {
			msg = _gson.toJson(o);
			Log.d("Test", msg);
		}

		return msg;
	}

	public void sendCommand(String s) {
		
		if (LessonMain.isTeacher()&&LessonMain.isLive()) {
			Log.d("teacherSend", s);
			try {
				LessonMainLeft.muc.sendMessage("<ppt>" + s);
			} catch (Exception e) {
			}
		}
	};

	public void handleJson(String s) {
		System.out.println(s);
		if (!LessonMain.isTeacher()) {
			Log.d("studentReceive", s);
			ChangeHandler ch = (ChangeHandler) fromJSON(s);
			ch.handle();
		}
	}

	public static void setPage(int page) {
		ga.setSelection(page, true);
		imageView.setImageBitmap(slide.get(page));
		pageNumber.setText(page + 1 + "");

	}

	public class ImageAdapter extends BaseAdapter {

		private Context ctx;
		int imageBackground;

		public ImageAdapter(Context c) {
			ctx = c;
			TypedArray ta = getActivity().obtainStyledAttributes(
					R.styleable.Gallery1);
			imageBackground = ta.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 1);
			ta.recycle();
		}

		@Override
		public int getCount() {

			return slide.size();
		}

		@Override
		public Object getItem(int arg0) {

			return arg0;
		}

		@Override
		public long getItemId(int arg0) {

			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ImageView iv = new ImageView(ctx);
			iv.setImageBitmap(slide.get(arg0));
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setLayoutParams(new Gallery.LayoutParams(150, 120));
			iv.setBackgroundResource(imageBackground);
			return iv;
		}

	}

	interface ChangeHandler {
		public void handle();
	}

	class PageChange implements ChangeHandler {
		private String _cname;
		private int page;

		public PageChange() {
			_cname = this.getClass().getName();
		}

		public void setPage(int p) {
			page = p;
		}

		@Override
		public void handle() {
			// TODO Auto-generated method stub
			LessonMainRightPowerpoint.setPage(page);

		}

	}

}
