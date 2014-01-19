package com.curtis.fyp.lesson;

import afzkl.development.mColorPicker.ColorPickerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;
import android.view.ViewGroup;

import com.curtis.fyp.Login;
import com.curtis.fyp.R;
import com.fyp.whiteboard.ChangeHandler;
import com.fyp.whiteboard.Slide;
import com.fyp.whiteboard.changeTextDialog;
import com.fyp.whiteboard.command.Command;
import com.fyp.whiteboard.command.CreateShapeCommand;
import com.fyp.whiteboard.command.MovePositionCommand;
import com.fyp.whiteboard.command.MoveToBgCommand;
import com.fyp.whiteboard.control.*;
import com.fyp.whiteboard.shape.Circle;
import com.fyp.whiteboard.shape.LinePath;
import com.fyp.whiteboard.shape.Rectangle;
import com.fyp.whiteboard.shape._Shape;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;

import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class LessonMainRightWhiteBoard extends Fragment implements
		View.OnTouchListener {
	public static Slide drawingSurface;
	private static _Shape currentDrawingPath;
	private static Button redoBtn;
	private static Button undoBtn;
	public static Control currentControl;
	public static Hashtable<String, Control> controlTable;
	private static Button cirBtn;
	private static Button recBtn;
	private static Button pathBtn;
	private static Button eraseBtn;
	private static Button lineBtn;
	private static Button norBtn;
	private static Button botBtn;
	private static Button topBtn;
	private static Button txtBtn;
	private static Button drawSettingBtn;
	private static Button puttoBgBtn;

	private static Button shapeBtn;
	private static Button drawBtn;
	private static Button saveBtn;
	private static Button bgBtn;

	private static Button addTextBtn;

	private static Button editTextBtn;

	private static SeekBar eraserSb;
	private static TextView eraserTitle, eraserNo;

	private static CheckBox cbDrawInBg, cbShapeStroke;

	/* Control class joining */
	public static final String cCircle = "1";
	public static final String cLine = "2";
	public static final String cPath = "3";
	public static final String cRect = "4";
	public static final String cEraser = "5";
	public static final String cNormal = "6";
	public static final String cText = "7";
	public static final String cTextEdit = "8";

	public static float scaleFontSize = 1;

	private AlertDialog.Builder alert;
	private File APP_FILE_PATH = new File("/sdcard/FYP_whiteboard");

	private static Gson _gson = new Gson();
	private static PrintStream _ps = null;

	

	/* Background */
	Uri outputFileUri;
	private static final int ACTIVITY_SELECT_IMAGE = 100;
	private static final int ACTIVITY_TAKE_IMAGE = 101;
	String selectedImagePath;
	// ADDED
	String filemanagerstring;
	TextView txt;
	EditText et;
	static boolean change = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View result = inflater.inflate(R.layout.drawing_activity, container);
		drawingSurface = (Slide) result.findViewById(R.id.drawingSurface);
		if(LessonMain.isTeacher())
		drawingSurface.setOnTouchListener(this);

		currentControl = new CreationPathControl();
		setButton(result);
		init();

		controlTable = new Hashtable<String, Control>();
		controlTable.put(cCircle, new CreationCirControl());
		controlTable.put(cLine, new CreationLineControl());
		controlTable.put(cPath, new CreationPathControl());
		controlTable.put(cRect, new CreationRecControl());
		controlTable.put(cEraser, new EraserControl());
		controlTable.put(cNormal, new NormalControl());
		controlTable.put(cText, new TextControl());
		controlTable.put(cTextEdit, new TextEditControl());

		scaleFontSize = 1;

		return result;
	}

	public void changeBackground(InputStream imageStream) {
		drawingSurface.setBackground(BitmapFactory.decodeStream(imageStream));

	}
	
	public void changeBackground2(File imgFile) {
		drawingSurface.setBackground(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));

	}
	
	/*

	
	  @Override public void onActivityResult(int requestCode, int resultCode,
	  Intent data) { super.onActivityResult(requestCode, resultCode, data);
	  
	  switch (requestCode) { case ACTIVITY_SELECT_IMAGE: if (resultCode ==
	  RESULT_OK) {
	  
	  Uri selectedImage = data.getData(); InputStream imageStream = null; try {
	  imageStream = getContentResolver().openInputStream( selectedImage); }
	  catch (FileNotFoundException e) { // TODO Auto-generated catch block
	  e.printStackTrace(); }
	 * 
	 * drawingSurface.setBackground(BitmapFactory .decodeStream(imageStream));
	 * 
	 * } break;
	 * 
	 * case ACTIVITY_TAKE_IMAGE: if (resultCode == RESULT_OK) { File imgFile =
	 * new File("/sdcard/image.jpg");
	 * 
	 * if (imgFile.exists()) {
	 * 
	 * Bitmap myBitmap = BitmapFactory.decodeFile(imgFile .getAbsolutePath());
	 * drawingSurface.setBackground(BitmapFactory
	 * .decodeFile(imgFile.getAbsolutePath()));
	 * 
	 * }
	 * 
	 * break; } } }
	 */
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().managedQuery(uri, projection, null, null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public static void setControl(String cname) {
		Log.d("Control:", cname);

		currentControl = (Control) controlTable.get(cname);
		init();

	}

	public void setButton(View view) {
		eraserTitle = (TextView) view.findViewById(R.id.txtEraser);
		eraserNo = (TextView) view.findViewById(R.id.txtStroke);

		redoBtn = (Button) view.findViewById(R.id.redoBtn);
		redoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Toast.makeText(getActivity().getApplicationContext(), "redo",
						Toast.LENGTH_SHORT).show();
				redo();
				ChangeHandler mp = null;
				mp = new SlideRedo();
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

			}
		});
		undoBtn = (Button) view.findViewById(R.id.undoBtn);
		undoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Toast.makeText(getActivity().getApplicationContext(), "undo",
						Toast.LENGTH_SHORT).show();
				undo();
				ChangeHandler mp = null;
				mp = new SlideUndo();
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

				startDownload("");

			}
		});
		
		cirBtn = (Button) view.findViewById(R.id.CircBtn);
		cirBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity().getApplicationContext(), "drawing circle....",
						Toast.LENGTH_SHORT).show();
				currentControl = new CreationCirControl();
				init();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cCircle);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));

			}
		});


		recBtn = (Button) view.findViewById(R.id.RectBtn);
		recBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentControl = new CreationRecControl();
				init();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cRect);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));
			}
		});

		pathBtn = (Button) view.findViewById(R.id.PathBtn);
		pathBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentControl = new CreationPathControl();
				init();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cPath);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));
			}
		});

		lineBtn = (Button) view.findViewById(R.id.LineBtn);
		lineBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				currentControl = new CreationLineControl();
				init();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cLine);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));
			}
		});
		norBtn = (Button) view.findViewById(R.id.NorBtn);
		norBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showNormalBtn();
				currentControl = new NormalControl();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cNormal);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));
			}
		});

		eraseBtn = (Button) view.findViewById(R.id.clearBtn);
		eraseBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentControl = new EraserControl();
				init();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cEraser);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));
				showEraserSeekBar();
			}
		});

		topBtn = (Button) view.findViewById(R.id.topBtn);
		topBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * drawingSurface.changeOrder(drawingSurface.getSelectedShape(),
				 * 1);
				 */
				moveToTop();
				ChangeHandler mp = null;
				mp = new moveToTop();
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

			}
		});

		botBtn = (Button) view.findViewById(R.id.botBtn);
		botBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				moveToBottom();
				ChangeHandler mp = null;
				mp = new moveToBottom();
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));
			}
		});

		puttoBgBtn = (Button) view.findViewById(R.id.PuttoBgBtn);
		puttoBgBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				putToBg();
				ChangeHandler mp = null;
				mp = new putToBg();
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

			}
		});

		txtBtn = (Button) view.findViewById(R.id.textBtn);
		txtBtn.setVisibility(View.GONE);
		txtBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentControl = new TextEditControl();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cTextEdit);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));
				showTextBtn();
			}
		});

		addTextBtn = (Button) view.findViewById(R.id.addTextBtn);
		addTextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentControl = new TextControl();
				ChangeControl ch = new ChangeControl();
				ch.setControlClass(cText);
				sendCommand(LessonMainRightWhiteBoard.toJSON(ch));

				final changeTextDialog d2 = new changeTextDialog(getActivity()
						);
				d2.setButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						((TextControl) currentControl).setBold(d2.getBold());
						((TextControl) currentControl).setMsg(d2.getMsg());
						((TextControl) currentControl).setFontSize((int) (d2
								.getFontSize()));

					}
				});

				d2.setButton2("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				d2.show();
			}
		});

		editTextBtn = (Button) view.findViewById(R.id.editTextBtn);
		editTextBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		shapeBtn = (Button) view.findViewById(R.id.ShapeBtn);
		shapeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showShapeBtn();

			}
		});

		drawBtn = (Button) view.findViewById(R.id.drawBtn);
		drawBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDrawBtn();

			}
		});

		drawSettingBtn = (Button) view.findViewById(R.id.settingBtn);
		drawSettingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ColorPickerDialog d2 = new ColorPickerDialog(
						getActivity(), currentControl
								.getColour(), currentControl.getThickness());
				d2.setAlphaSliderVisible(true);

				d2.setButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setColorThickness(d2.getColor(), d2.getThickness());
						init();
						ChangeHandler mp = null;
						mp = new ChangeColour();
						((ChangeColour) mp).setting(d2.getColor(),
								d2.getThickness());
						sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

					}
				});

				d2.setButton2("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});

				d2.show();

			}
		});

		saveBtn = (Button) view.findViewById(R.id.saveBtn);
		
		

		bgBtn = (Button) view.findViewById(R.id.BgBtn);
		bgBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, ACTIVITY_SELECT_IMAGE);

			}
		});

		cbDrawInBg = (CheckBox) view.findViewById(R.id.cbDrawInBg);

		cbDrawInBg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

				ChangeHandler mp = null;
				mp = new DrawToBg();
				((DrawToBg) mp).setCb(arg1);
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

			}
		});

		cbShapeStroke = (CheckBox) view.findViewById(R.id.strokeCb);
		cbShapeStroke.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub

				ChangeHandler mp = null;
				mp = new ShapeStroke();
				((ShapeStroke) mp).setCb(arg1);
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

			}
		});

		eraserSb = (SeekBar) view.findViewById(R.id.sbEraser);
		eraserSb.setMax(50);
		eraserSb.setProgress(20);
		eraserNo.setText("20");

		eraserSb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				setEraserThickness(arg1);
				eraserNo.setText(arg1 + "");

				if (arg1 < 15) {
					setEraserThickness(15);
					eraserNo.setText("15");
				}

				ChangeHandler mp = null;
				mp = new changeEraser();
				((changeEraser) mp).setThickness(eraserSb.getProgress());
				sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

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

		redoBtn.setEnabled(false);
		undoBtn.setEnabled(false);

		showNormalBtn();
		
		if(!LessonMain.isTeacher())
		hideAllButton();

	}

	public static void shapeStroke(boolean b) {
		cbShapeStroke.setChecked(b);

	}

	public static void drawToBg(boolean b) {
		cbDrawInBg.setChecked(b);

	}

	public static void putToBg() {
		if (drawingSurface.getSelectedShape() != null) {
			MoveToBgCommand mpc = new MoveToBgCommand(
					drawingSurface.getSelectedShape());

			drawingSurface.exeCommand(mpc);
			drawingSurface.addCommand(mpc);
		}
		drawingSurface.updateSelectedShapeStatus();

	}

	public static void moveToTop() {
		if (drawingSurface.getSelectedShape() != null) {
			MovePositionCommand mpc = new MovePositionCommand(
					drawingSurface.getSelectedShape(), 1);

			drawingSurface.exeCommand(mpc);
			drawingSurface.addCommand(mpc);
		}
		drawingSurface.updateSelectedShapeStatus();

	}

	public static void moveToBottom() {
		if (drawingSurface.getSelectedShape() != null) {
			MovePositionCommand mpc = new MovePositionCommand(
					drawingSurface.getSelectedShape(), -1);

			drawingSurface.exeCommand(mpc);
			drawingSurface.addCommand(mpc);
		}
		drawingSurface.updateSelectedShapeStatus();

	}

	public static void setColorThickness(int c, int t) {
		currentControl.setColour(c);
		currentControl.setThickness(t);

	}

	public static void setEraserThickness(int t) {
		currentControl.setEraserThickness(t);

	}

	public static void setSelectedShape(int x, int y) {
		drawingSurface.setSelectedShape(drawingSurface.getShape(x, y));

	}

	public static void setSelectedShapeState(boolean top, boolean bot,
			boolean inBg) {
		puttoBgBtn.setEnabled(inBg);
		topBtn.setEnabled(top);
		botBtn.setEnabled(bot);
	}

	public void showNormalBtn() {
		int show = View.GONE;
		cirBtn.setVisibility(show);
		recBtn.setVisibility(show);

		pathBtn.setVisibility(show);
		lineBtn.setVisibility(show);

		drawSettingBtn.setVisibility(show);
		cbDrawInBg.setVisibility(show);

		eraserSb.setVisibility(show);
		eraserTitle.setVisibility(show);
		eraserNo.setVisibility(show);

		cbShapeStroke.setVisibility(show);

		addTextBtn.setVisibility(show);

		editTextBtn.setVisibility(show);

		show = View.VISIBLE;
		botBtn.setVisibility(show);
		topBtn.setVisibility(show);
		bgBtn.setVisibility(show);
		saveBtn.setVisibility(show);
		puttoBgBtn.setVisibility(show);
		bgBtn.setVisibility(View.GONE);
		
		saveBtn.setVisibility(View.GONE);

	}

	public void showShapeBtn() {
		int show = View.VISIBLE;
		cirBtn.setVisibility(show);
		recBtn.setVisibility(show);
		drawSettingBtn.setVisibility(show);
		cbDrawInBg.setVisibility(show);
		cbShapeStroke.setVisibility(show);

		show = View.GONE;
		pathBtn.setVisibility(show);
		lineBtn.setVisibility(show);

		botBtn.setVisibility(show);
		topBtn.setVisibility(show);
		bgBtn.setVisibility(show);
		saveBtn.setVisibility(show);
		puttoBgBtn.setVisibility(show);

		eraserSb.setVisibility(show);
		eraserTitle.setVisibility(show);
		eraserNo.setVisibility(show);

		addTextBtn.setVisibility(show);

		editTextBtn.setVisibility(show);
		bgBtn.setVisibility(View.GONE);
		
		saveBtn.setVisibility(View.GONE);
		
		
	}

	public void showDrawBtn() {
		int show = View.GONE;
		cirBtn.setVisibility(show);
		recBtn.setVisibility(show);
		cbShapeStroke.setVisibility(show);
		addTextBtn.setVisibility(show);

		editTextBtn.setVisibility(show);

		show = View.VISIBLE;
		pathBtn.setVisibility(show);
		lineBtn.setVisibility(show);
		drawSettingBtn.setVisibility(show);

		show = View.GONE;
		botBtn.setVisibility(show);
		topBtn.setVisibility(show);
		bgBtn.setVisibility(show);
		saveBtn.setVisibility(show);
		puttoBgBtn.setVisibility(show);

		eraserSb.setVisibility(show);
		eraserTitle.setVisibility(show);
		eraserNo.setVisibility(show);
		
		bgBtn.setVisibility(View.GONE);
		
		saveBtn.setVisibility(View.GONE);
	}

	public void showTextBtn() {
		int show = View.GONE;
		cirBtn.setVisibility(show);
		recBtn.setVisibility(show);

		pathBtn.setVisibility(show);
		lineBtn.setVisibility(show);

		drawSettingBtn.setVisibility(show);
		cbDrawInBg.setVisibility(show);

		eraserSb.setVisibility(show);
		eraserTitle.setVisibility(show);
		eraserNo.setVisibility(show);

		cbShapeStroke.setVisibility(show);
		bgBtn.setVisibility(show);
		saveBtn.setVisibility(show);

		show = View.VISIBLE;
		botBtn.setVisibility(show);
		topBtn.setVisibility(show);
		puttoBgBtn.setVisibility(show);
		addTextBtn.setVisibility(show);

		editTextBtn.setVisibility(show);
		
bgBtn.setVisibility(View.GONE);
		
		saveBtn.setVisibility(View.GONE);

	}

	public void showEraserSeekBar() {
		int show = View.GONE;
		cirBtn.setVisibility(show);
		recBtn.setVisibility(show);

		cbShapeStroke.setVisibility(show);

		pathBtn.setVisibility(show);
		lineBtn.setVisibility(show);
		drawSettingBtn.setVisibility(show);

		cbDrawInBg.setVisibility(show);

		botBtn.setVisibility(show);
		topBtn.setVisibility(show);
		bgBtn.setVisibility(show);
		saveBtn.setVisibility(show);
		puttoBgBtn.setVisibility(show);

		show = View.VISIBLE;
		eraserSb.setVisibility(show);
		eraserTitle.setVisibility(show);
		eraserNo.setVisibility(show);
		
bgBtn.setVisibility(View.GONE);
		
		saveBtn.setVisibility(View.GONE);
	}	
	
	public void hideAllButton() {
		int show = View.GONE;
		cirBtn.setVisibility(show);
		recBtn.setVisibility(show);

		cbShapeStroke.setVisibility(show);

		pathBtn.setVisibility(show);
		lineBtn.setVisibility(show);
		drawSettingBtn.setVisibility(show);

		cbDrawInBg.setVisibility(show);

		botBtn.setVisibility(show);
		topBtn.setVisibility(show);
		bgBtn.setVisibility(show);
		saveBtn.setVisibility(show);
		puttoBgBtn.setVisibility(show);
	
		eraserSb.setVisibility(show);
		eraserTitle.setVisibility(show);
		eraserNo.setVisibility(show);
		
		undoBtn.setVisibility(show);
		redoBtn.setVisibility(show);
		norBtn.setVisibility(show);
		shapeBtn.setVisibility(show);
		drawBtn.setVisibility(show);
		txtBtn.setVisibility(show);
		eraseBtn.setVisibility(show);
		
		
		
	}


	public static void init() {
		currentControl.setStroke(cbShapeStroke.isChecked());
		currentDrawingPath = currentControl.createProduct();
		drawingSurface.previewPath = currentControl.createProduct();

		currentControl.initial(currentDrawingPath, drawingSurface);

		currentDrawingPath.set_border(!cbShapeStroke.isChecked());
		drawingSurface.previewPath.set_border(!cbShapeStroke.isChecked());

	}

	public static int getFontSize() {

		// String s = (String) _chSize.getSelectedItem();
		int nSize = 24;
		/*
		 * if (s != null) { try { nSize = Integer.parseInt(s); } catch
		 * (NumberFormatException ex) { nSize = 24; } }
		 */
		return nSize;
	}

	public static int getFontStyle() {
		/*
		 * String s = (String) _chStyle.getSelectedItem(); int nStyle =
		 * Font.PLAIN; if (s != null) { if (s.equals("Bold")) nStyle =
		 * Font.BOLD; if (s.equals("Italic")) nStyle = Font.ITALIC; } return
		 * nStyle;
		 */
		return 0;
	}

	public static String getFontFace() {
		/*
		 * String s = (String) _chFace.getSelectedItem(); if (s!=null) return s;
		 */
		return "Dialogue";
	}

	public static String toJSON(Object o) {
		String msg = "";
		if (o != null) {
			msg = _gson.toJson(o);
			Log.d("Test", msg);

		}

		return msg;
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
/*
	public void handleMsg(String message) {
		ChangeHandler ch = (ChangeHandler) LessonMainRightWhiteBoard
				.fromJSON(message);
		ch.handle();
		Log.d("Message handle by WhiteBoard : ", message);

	}
	*/

	public void loadJsonFromIM(String s) {
		if (!LessonMain.isTeacher()) {
		ChangeHandler ch = (ChangeHandler) LessonMainRightWhiteBoard
				.fromJSON(s);
		ch.handle();
		}

	}

	// Teacher
	public void sendCommand(String text) {
		// TODO Need to change to Main SYstem connection
		// Message msg = new Message(to, Message.Type.chat);
		// msg.setBody(text);
		// connection.sendPacket(msg);
		// messages.add(connection.getUser() + ":");
		// messages.add(text);

		// Message msg2 = new Message("tester1@yiu-pc", Message.Type.chat);
		// msg2.setBody(text);
		// connection.sendPacket(msg2);
		// messages.add(connection.getUser() + ":");
		// messages.add(text);
		
		if (LessonMain.isTeacher()&&LessonMain.isLive()) {
			Log.d("teacherSend", text);
			try {
				LessonMainLeft.muc.sendMessage("<whb>" + text);
			} catch (Exception e) {
			}
		}

	}

	public boolean onTouch(View view, MotionEvent motionEvent) {
		int x = (int) motionEvent.getX();
		int y = (int) motionEvent.getY();
		ChangeHandler mp = null;

		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			mousePressed(x, y);
			mp = new MousePressed();
			((MousePressed) mp).setMouseEvent(x, y);

		} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
			mouseDragged((int) motionEvent.getX(), (int) motionEvent.getY());
			mp = new MouseDragged();
			((MouseDragged) mp).setMouseEvent(x, y);

		} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			mouseReleased((int) motionEvent.getX(), (int) motionEvent.getY());
			mp = new MouseReleased();
			((MouseReleased) mp).setMouseEvent(x, y);

		}

		sendCommand(LessonMainRightWhiteBoard.toJSON(mp));

		return true;
	}

	public static void mousePressed(int x, int y) {
		drawingSurface.setSelectedShape(null);

		//drawingSurface.setSelectedShape(drawingSurface.getShape(x, y));
		init();
		drawingSurface.previewPath.setPath(new Path());

		drawingSurface.isDrawing = true;
		currentControl.mousePressed(drawingSurface.previewPath.getPath(), x, y);
		if (currentControl.isCreateControl())
			currentControl.mousePressed(currentDrawingPath.getPath(), x, y);
	}

	public static void mouseDragged(int x, int y) {
		drawingSurface.isDrawing = true;
		currentControl.mouseDragged(drawingSurface.previewPath.getPath(), x, y);

		if (currentControl.isCreateControl())
			currentControl.mouseDragged(currentDrawingPath.getPath(), x, y);

	}

	public static void mouseReleased(int x, int y) {
		currentControl
				.mouseReleased(drawingSurface.previewPath.getPath(), x, y);

		if (currentControl.isCreateControl())
			currentControl.mouseReleased(currentDrawingPath.getPath(), x, y);

		if (currentControl.isCreateControl()) {
			currentDrawingPath.setID();
			currentDrawingPath.setPoint();

			if (cbDrawInBg.isChecked()) {
				currentDrawingPath.setLayerLevel(0);

			}

			/* New */
			Command c = new CreateShapeCommand(currentDrawingPath);
			drawingSurface.addCommand(c);
			drawingSurface.previewPath = null;

		}

		if (currentControl.backToNoraml())
			setControl(cTextEdit);

		undoBtn.setEnabled(true);
		redoBtn.setEnabled(false);
	}

	public static void undo() {

		drawingSurface.undo();
		if (drawingSurface.hasMoreUndo() == false) {
			undoBtn.setEnabled(false);
		}
		redoBtn.setEnabled(true);

	}

	public static void redo() {
		drawingSurface.redo();
		if (drawingSurface.hasMoreRedo() == false) {
			redoBtn.setEnabled(false);
		}
		undoBtn.setEnabled(true);

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.saveBtn:
			final Activity currentActivity = getActivity();
			Handler saveHandler = new Handler() {
				@Override
				public void handleMessage(android.os.Message msg) {
					final AlertDialog alertDialog = new AlertDialog.Builder(
							currentActivity).create();
					alertDialog.setTitle("Saved 1");
					alertDialog.setMessage("Your drawing had been saved :)");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					alertDialog.show();
				}
			};
			new ExportBitmapToFile(getActivity(),
					saveHandler, drawingSurface.getBitmap()).execute();
		}

	}

	/*
	 * Intent intent = new Intent(
	 * android.provider.MediaStore.ACTION_IMAGE_CAPTURE);//
	 * §Q¥Îintent¥h¶}±Òandroid¥»¨­ªº·Ó¬Û¤¶­±
	 * 
	 * File tmpFile = new File(Environment .getExternalStorageDirectory(),
	 * "image.jpg"); outputFileUri = Uri.fromFile(tmpFile);
	 * intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	 * startActivityForResult(intent, ACTIVITY_TAKE_IMAGE);
	 */

	/*
	 * @Override // NEED TO CHANGE public boolean onCreateOptionsMenu(Menu menu)
	 * { // TODO Auto-generated method stub menu.add(0, 0, 0, "Save");
	 * menu.add(0, 1, 1, "Bg-browser"); menu.add(0, 2, 2, "Bg-cam"); menu.add(0,
	 * 3, 3, "Color"); menu.add(0, 4, 4, "Stroke");
	 * 
	 * return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub switch (item.getItemId()) { case (0): final
	 * Activity currentActivity = this; Handler saveHandler = new Handler() {
	 * public void handleMessage(Message msg) { final AlertDialog alertDialog =
	 * new AlertDialog.Builder( currentActivity).create();
	 * alertDialog.setTitle("Saved 1");
	 * alertDialog.setMessage("Your drawing had been saved :)");
	 * alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int which) { return; } });
	 * alertDialog.show(); Toast.makeText(currentActivity, "OK!",
	 * Toast.LENGTH_LONG); }
	 * 
	 * }; new ExportBitmapToFile(this, saveHandler,
	 * drawingSurface.getBitmap()).execute(); break; case (1):
	 * 
	 * Intent i = new Intent( Intent.ACTION_PICK,
	 * android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	 * startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
	 * 
	 * break; case (2): Intent intent = new Intent(
	 * android.provider.MediaStore.ACTION_IMAGE_CAPTURE);//
	 * þý©ç”¨intentþý»éþýþýŸandroidþý¬èº«þý„ç…§þý¸äþýþýþý File tmpFile = new
	 * File(Environment.getExternalStorageDirectory(), "image.jpg");
	 * outputFileUri = Uri.fromFile(tmpFile);
	 * intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	 * startActivityForResult(intent, ACTIVITY_TAKE_IMAGE);
	 * 
	 * break; case (3): final ColorPickerDialog d2 = new ColorPickerDialog(this,
	 * currentControl.getColour()); d2.setAlphaSliderVisible(true);
	 * 
	 * d2.setButton("Ok", new DialogInterface.OnClickListener() {
	 * 
	 * public void onClick(DialogInterface dialog, int which) {
	 * currentControl.setColour(d2.getColor()); ChangeHandler mp = null; mp =
	 * new ChangeColour(); // mp.setColour(d2.getColor());
	 * 
	 * } });
	 * 
	 * d2.setButton2("Cancel", new DialogInterface.OnClickListener() {
	 * 
	 * public void onClick(DialogInterface dialog, int which) {
	 * 
	 * } });
	 * 
	 * d2.show();
	 * 
	 * break; case (4):
	 * 
	 * alert = new AlertDialog.Builder(this);
	 * 
	 * alert.setTitle("Stroke"); alert.setMessage("Thickness:" +
	 * currentControl.getThickness());
	 * 
	 * SeekBar seekBar = new SeekBar(this); seekBar.setMax(25);
	 * seekBar.setProgress(currentControl.getThickness());
	 * 
	 * alert.setView(seekBar);
	 * 
	 * seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	 * 
	 * public void onProgressChanged(SeekBar seekBar, int progress, boolean
	 * fromUser) { currentControl.setThickness(progress); }
	 * 
	 * public void onStartTrackingTouch(SeekBar seekBar) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * public void onStopTrackingTouch(SeekBar seekBar) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 * 
	 * }); alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	 * public void onClick(DialogInterface dialog, int whichButton) {
	 * 
	 * // Do something with value! } });
	 * 
	 * alert.show(); break; } return true; }
	 */

	private class ExportBitmapToFile extends AsyncTask<Intent, Void, Boolean> {
		private Context mContext;
		private Handler mHandler;
		private Bitmap nBitmap;

		public ExportBitmapToFile(Context context, Handler handler,
				Bitmap bitmap) {
			mContext = context;
			nBitmap = bitmap;
			mHandler = handler;
		}

		@Override
		protected Boolean doInBackground(Intent... arg0) {
			try {
				if (!APP_FILE_PATH.exists()) {
					APP_FILE_PATH.mkdirs();
				}

				final FileOutputStream out = new FileOutputStream(new File(
						APP_FILE_PATH + "/test.png"));
				nBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.flush();
				out.close();

				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// mHandler.post(completeRunnable);
			return false;
		}

		@Override
		protected void onPostExecute(Boolean bool) {
			super.onPostExecute(bool);
			if (bool) {
				mHandler.sendEmptyMessage(1);

			}
		}
	}

	class BackgroundUploader extends AsyncTask<Void, Integer, Void> implements
			DialogInterface.OnCancelListener {

		private ProgressDialog progressDialog;
		private String url;
		private File file;

		public BackgroundUploader(String url, File file) {
			this.url = url;
			this.file = file;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(getContext());
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("Uploading...");
			progressDialog.setCancelable(false);
			progressDialog.setMax((int) file.length());
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... v) {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection connection = null;
			String fileName = file.getName();
			try {
				connection = (HttpURLConnection) new URL(url).openConnection();
				connection.setRequestMethod("POST");
				String boundary = "---------------------------boundary";
				String tail = "\r\n--" + boundary + "--\r\n";
				connection.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + boundary);
				connection.setDoOutput(true);

				String metadataPart = "--"
						+ boundary
						+ "\r\n"
						+ "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
						+ "" + "\r\n";

				String fileHeader1 = "--"
						+ boundary
						+ "\r\n"
						+ "Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
						+ fileName + "\"\r\n"
						+ "Content-Type: application/octet-stream\r\n"
						+ "Content-Transfer-Encoding: binary\r\n";

				long fileLength = file.length() + tail.length();
				String fileHeader2 = "Content-length: " + fileLength + "\r\n";
				String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
				String stringData = metadataPart + fileHeader;

				long requestLength = stringData.length() + fileLength;
				connection.setRequestProperty("Content-length", ""
						+ requestLength);
				connection.setFixedLengthStreamingMode((int) requestLength);
				connection.connect();

				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				out.writeBytes(stringData);
				out.flush();

				int progress = 0;
				int bytesRead = 0;
				byte buf[] = new byte[1024];
				BufferedInputStream bufInput = new BufferedInputStream(
						new FileInputStream(file));
				while ((bytesRead = bufInput.read(buf)) != -1) {
					// write output
					out.write(buf, 0, bytesRead);
					out.flush();
					progress += bytesRead;
					// update progress bar
					publishProgress(progress);
				}

				// Write closing boundary and close stream
				out.writeBytes(tail);
				out.flush();
				out.close();

				// Get server response
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String line = "";
				StringBuilder builder = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

			} catch (Exception e) {
				// Exception
			} finally {
				if (connection != null)
					connection.disconnect();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			progressDialog.setProgress((int) (progress[0]));
		}

		@Override
		protected void onPostExecute(Void v) {
			progressDialog.dismiss();
			sendCommand("download " + Login.webServerIp + "/uploads/" + file.getName());
			//TODO
		}

		@Override
		public void onCancel(DialogInterface dialog) {
			cancel(true);
			dialog.dismiss();
		}
	}
	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private Button startBtn;
	private ProgressDialog mProgressDialog;

	private void startDownload(String url) {

		new DownloadFileAsync().execute(url);
	}
	/*
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(getActivity());
			mProgressDialog.setMessage("Downloading file..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}*/

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream("/sdcard/temp.jpg");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			//dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			File imgFile = new File("/sdcard/temp.jpg");

			if (imgFile.exists()) {

				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());
				drawingSurface.setBackground(BitmapFactory.decodeFile(imgFile
						.getAbsolutePath()));

			}
		}
	}

	public Context getContext() {
		// TODO Auto-generated method stub

		return getActivity().getApplicationContext();
	}

	// ChangeHandler
	class ChangeControl implements ChangeHandler {
		private String _cname;
		private String _controlClass = null;

		public ChangeControl() {
			_cname = this.getClass().getName();
		}

		public void setControlClass(String className) {
			_controlClass = className;
		}

		@Override
		public void handle() {

			LessonMainRightWhiteBoard.setControl(_controlClass);

		}
	}

	class MousePressed implements ChangeHandler {
		private String _cname;
		int x, y;

		public MousePressed() {
			this._cname = this.getClass().getName();
		}

		public void setMouseEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.mousePressed(x, y);
		}
	}

	class MouseDragged implements ChangeHandler {
		private String _cname;
		int x, y;

		public MouseDragged() {
			this._cname = this.getClass().getName();
		}

		public void setMouseEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.mouseDragged(x, y);
		}
	}

	class MouseReleased implements ChangeHandler {
		private String _cname;
		int x, y;

		public MouseReleased() {
			this._cname = this.getClass().getName();
		}

		public void setMouseEvent(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.mouseReleased(x, y);
		}
	}

	class SlideClear implements ChangeHandler {
		private String _cname;

		public SlideClear() {
			this._cname = this.getClass().getName();
		}

		@Override
		public void handle() {

		}
	}

	class SlideUndo implements ChangeHandler {
		private String _cname;

		public SlideUndo() {
			this._cname = this.getClass().getName();
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.undo();
		}
	}

	class SlideRedo implements ChangeHandler {
		private String _cname;

		public SlideRedo() {
			this._cname = this.getClass().getName();
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.redo();
		}
	}

	class ChangeColour implements ChangeHandler {
		private String _cname;
		private int color;
		private int thickness;

		public ChangeColour() {
			this._cname = this.getClass().getName();
		}

		public void setting(int color, int thickness) {
			this.color = color;
			this.thickness = thickness;
		}

		@Override
		public void handle() {
			// TODO Auto-generated method stub
			LessonMainRightWhiteBoard.setColorThickness(color, thickness);
		}
	}

	class moveToTop implements ChangeHandler {
		private String _cname;

		public moveToTop() {
			this._cname = this.getClass().getName();
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.moveToTop();
		}
	}

	class moveToBottom implements ChangeHandler {
		private String _cname;

		public moveToBottom() {
			this._cname = this.getClass().getName();
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.moveToBottom();
		}
	}

	class putToBg implements ChangeHandler {
		private String _cname;

		public putToBg() {
			this._cname = this.getClass().getName();
		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.putToBg();

		}

	}

	class changeEraser implements ChangeHandler {
		private String _cname;
		private int thickness;

		public changeEraser() {
			this._cname = this.getClass().getName();
		}

		public void setThickness(int i) {
			thickness = i;

		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.setEraserThickness(thickness);

		}

	}

	class DrawToBg implements ChangeHandler {
		private String _cname;
		private boolean cb;

		public DrawToBg() {
			this._cname = this.getClass().getName();
		}

		public void setCb(boolean t) {
			cb = t;

		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.drawToBg(cb);

		}

	}

	class ShapeStroke implements ChangeHandler {
		private String _cname;
		private boolean cb;

		public ShapeStroke() {
			this._cname = this.getClass().getName();
		}

		public void setCb(boolean t) {
			cb = t;

		}

		@Override
		public void handle() {
			LessonMainRightWhiteBoard.shapeStroke(cb);
		}

	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}

}