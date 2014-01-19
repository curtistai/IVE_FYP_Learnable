package com.curtis.fyp.lesson;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LessonMainRightAskQuestion extends Fragment{
	WebView webView;
	String url = Login.webServerIp+"/question2answer/index.php?qa=login&to=index.php"; // TODO

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.lesson_main_right_websurfing,
				container);
		webView = (WebView) result
		.findViewById(R.id.lesson_main_right_websurfing_webView);

		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);

		return result;
	}

}
