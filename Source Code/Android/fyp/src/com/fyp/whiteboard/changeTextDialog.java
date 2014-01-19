package com.fyp.whiteboard;

import com.curtis.fyp.R;
import com.fyp.whiteboard.shape.text;

import afzkl.development.mColorPicker.views.ColorPanelView;
import afzkl.development.mColorPicker.views.ColorPickerView;
import afzkl.development.mColorPicker.views.ColorPickerView.OnColorChangedListener;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView.OnEditorActionListener;

public class changeTextDialog extends AlertDialog {
	TextView txtMsg;
	CheckBox cbBold;
	String s[]=new String[] {"16","20","26","32","38","44","50","54","62"};
	int fontSize;


	// EditText txtFontSize;

	public changeTextDialog(Context context) {
		super(context);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_text, null);

		setView(layout);

		setTitle("Edit Text");

		txtMsg = (EditText) layout.findViewById(R.id.txtMessage);
		cbBold = (CheckBox) layout.findViewById(R.id.cbtxtBold);
		// txtFontSize=(EditText)layout.findViewById(R.id.txtFontSize);
		Spinner spinner = (Spinner) layout.findViewById(R.id.spinner_txtFs);
		// å»ºçşıä¸şışıArrayAdapterşı©ä»¶ï¼Œä¸¦şı¾ç½®ä¸‹æşışı¸å–®şı„å…§å®şı		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item,s);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		// è¨­åşışı…ç›®è¢«é¸şı–äşıå¾Œçşışı•äşı
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				setFontSize(Integer.parseInt(s[arg2]));				
				txtMsg.setTextSize(Integer.parseInt(s[arg2]));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});


	}



	public String getMsg() {
		return txtMsg.getText().toString().equals("")?"Empty":txtMsg.getText().toString();
	}

	public Boolean getBold() {
		return cbBold.isChecked();
	}

	public int getFontSize() { 

		return fontSize;// Integer.parseInt(txtFontSize.getText().toString());
	}
	
	public void setFontSize(int i) {

		fontSize= i;
	}

}
