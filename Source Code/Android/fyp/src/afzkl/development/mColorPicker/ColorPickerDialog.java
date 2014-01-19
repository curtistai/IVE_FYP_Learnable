/*

 * Copyright (C) 2010 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package afzkl.development.mColorPicker;



import com.curtis.fyp.R;

import afzkl.development.mColorPicker.views.ColorPanelView;
import afzkl.development.mColorPicker.views.ColorPickerView;
import afzkl.development.mColorPicker.views.ColorPickerView.OnColorChangedListener;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class ColorPickerDialog extends AlertDialog implements
		ColorPickerView.OnColorChangedListener {

	private ColorPickerView mColorPicker;

	private ColorPanelView mOldColor;
	private ColorPanelView mNewColor;
	
	private SeekBar stroke;
	private TextView strokeNo;
	private int thickness;

	private OnColorChangedListener mListener;

	public ColorPickerDialog(Context context, int initialColor,int thickness) {
		super(context);

		init(initialColor,thickness);
	}

	private void init(int color,int thickness) {
		// To fight color branding.
		getWindow().setFormat(PixelFormat.RGBA_8888);

		setUp(color,thickness);

	}

	private void setUp(int color,int thickness) {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_color_picker, null);

		setView(layout);

		setTitle("Pick a Color");
		
		
		// setIcon(android.R.drawable.ic_dialog_info);

		mColorPicker = (ColorPickerView) layout
				.findViewById(R.id.color_picker_view);
		mOldColor = (ColorPanelView) layout.findViewById(R.id.old_color_panel);
		mNewColor = (ColorPanelView) layout.findViewById(R.id.new_color_panel);
		
		

		((LinearLayout) mOldColor.getParent()).setPadding(Math
				.round(mColorPicker.getDrawingOffset()), 0, Math
				.round(mColorPicker.getDrawingOffset()), 0);

		mColorPicker.setOnColorChangedListener(this);

		mOldColor.setColor(color);
		mColorPicker.setColor(color, true);
		
		stroke = (SeekBar)layout.findViewById(R.id.seekBar1);
		stroke.setMax(25);
		stroke.setProgress(thickness);
		
		stroke.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				setThickness(progress);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

		});
		
		strokeNo = (TextView) layout.findViewById(R.id.strokeNo);
		strokeNo.setText(((thickness<10)?" ":"")+thickness);
		

	}

	@Override
	public void onColorChanged(int color) {

		mNewColor.setColor(color);

		if (mListener != null) {
			mListener.onColorChanged(color);
		}

	}

	public void setAlphaSliderVisible(boolean visible) {
		mColorPicker.setAlphaSliderVisible(visible);
	}

	public int getColor() {
		return mColorPicker.getColor();
	}
	
	public int getThickness(){
		return thickness;
		
	}
	
	public void setThickness(int t){
		thickness = t;
		strokeNo.setText(((t<10)?" ":"")+t);
	}

}
