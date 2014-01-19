package com.fyp.whiteboard;

//import android.R;

import java.io.File;

import android.content.Context;
import android.graphics.*;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.curtis.fyp.R;
import com.fyp.whiteboard.command.Command;
import com.fyp.whiteboard.command.CommandManager;
import com.fyp.whiteboard.shape.Circle;
import com.fyp.whiteboard.shape.LinePath;
import com.fyp.whiteboard.shape._Shape;
import com.fyp.whiteboard.shape.text;

public class Slide extends SurfaceView implements SurfaceHolder.Callback {
	private Boolean _run;
	protected DrawThread thread;
	private Bitmap mBitmap;
	public boolean isDrawing = true;
	public _Shape previewPath = new LinePath();
	int bg = R.drawable.bg;
	Bitmap bgp;
	private _Shape _selectedShape;
	private text _selectedText;
	// private Font _font;
	private int _fontSize = 12;
	// private int _fontStyle = Font.PLAIN;
	private String _fontFace = "Dialog";

	protected int panelWidth;
	protected int panelHeight;

	Uri outputFileUri;

	private CommandManager commandManager;

	public Slide(Context context, AttributeSet attrs) {
		super(context, attrs);

		getHolder().addCallback(this);

		previewPath = new Circle();

		commandManager = new CommandManager();
		thread = new DrawThread(getHolder());

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		panelWidth = getWidth();
		panelHeight = getHeight();

	}

	public void setSelectedShape(_Shape s) {
		_selectedShape = s;
		updateSelectedShapeStatus();

	}

	public _Shape getSelectedShape() {
		return _selectedShape;
	}

	public void setSelectedText(text s) {
		_selectedText = s;

	}

	public text getSelectedText() {
		return _selectedText;
	}

	public _Shape getShape(int x, int y) {
		return commandManager.isFocus(x, y);
	}

	private Handler previewDoneHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			isDrawing = false;
		}
	};

	class DrawThread extends Thread {
		private SurfaceHolder mSurfaceHolder;

		public DrawThread(SurfaceHolder surfaceHolder) {
			mSurfaceHolder = surfaceHolder;

		}

		public void setRunning(boolean run) {
			_run = run;
		}

		@Override
		public void run() {
			Canvas canvas = null;

			while (_run) {

				if (isDrawing == true) {
					try {
						try {

							canvas = mSurfaceHolder.lockCanvas(null);
							if (mBitmap == null) {
								mBitmap = Bitmap.createBitmap(1, 1,
										Bitmap.Config.ARGB_8888);
							}
							final Canvas c = new Canvas(mBitmap);
							Bitmap vBitmap = BitmapFactory.decodeResource(
									getResources(), bg);

							c.drawColor(0, PorterDuff.Mode.CLEAR);
							canvas.drawColor(0, PorterDuff.Mode.CLEAR);

							commandManager.executeAll(c, previewPath);

							if (_selectedShape != null)
								_selectedShape.drawCorners(c);

							if (bgp != null) {
								Bitmap bgp_ = Bitmap.createScaledBitmap(bgp,
										panelWidth, panelHeight, false);
								canvas.drawBitmap(bgp_, 0, 0, null);
							} else {
								Bitmap _vBitmap = Bitmap.createScaledBitmap(vBitmap,
										panelWidth, panelHeight, false);
								canvas.drawBitmap(_vBitmap, 0, 0, null);
							}

							canvas.drawBitmap(mBitmap, 0, 0, null);
						} finally {
							mSurfaceHolder.unlockCanvasAndPost(canvas);
						}
					} finally {
					}

				}

			}

		}
	}

	public void addCommand(Command c) {
		c.execute(commandManager);
		commandManager.addCommand(c);

	}

	public void changeById(int shapeId, int actionId) {
		commandManager.changeById(shapeId, actionId);

	}

	public boolean hasMoreRedo() {
		return commandManager.hasMoreRedo();
	}

	public void redo() {
		isDrawing = true;
		commandManager.redo();
	}

	public void undo() {
		isDrawing = true;
		commandManager.undo();
	}

	public boolean hasMoreUndo() {
		return commandManager.hasMoreUndo();
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void updateSelectedShapeStatus() {
		commandManager.setButton(_selectedShape);
	}

	public void setBackground(Bitmap b) {
		bgp = b;

	}

	public void exeCommand(Command c) {
		c.execute(commandManager);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (thread.getState() == Thread.State.TERMINATED) {
			thread = new DrawThread(getHolder());
			thread.setRunning(true);
			thread.start();
		} else {
			thread.setRunning(true);
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}

	public void clear() {
		// TODO Auto-generated method stub

	}

}
