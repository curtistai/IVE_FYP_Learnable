package com.fyp.whiteboard.shape;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.fyp.whiteboard.memento.ShapeMemento;
import com.fyp.whiteboard.memento.TextMemento;

public class text extends Rectangle {
	public String content = "text";
	Paint pen;
	int x, y;
	String msg = "Please click to edit!";

	int colour = Color.BLACK;
	Boolean bold;
	int fontSize;

	public void setPen() {
		pen = new Paint();
		pen.setColor(Color.BLACK);
		pen.setStyle(Style.STROKE);

		DashPathEffect dashPath = new DashPathEffect(new float[] { 20, 5 }, 1);
		pen.setPathEffect(dashPath);
	}

	@Override
	public void draw(Canvas canvas) {

		// x=y=100;
		setPen();

		final TextPaint tp = new TextPaint();
		tp.setColor(Color.BLACK);
		
		
		tp.setTextSize(fontSize);
		if(bold)
		tp.setTypeface(Typeface.DEFAULT_BOLD);
		// tp.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

		// Save the canvas state, it might be re-used later.
		canvas.save();

		// Create a padding to the left &amp; top.
		canvas.translate(left, top);

		// Clip the bitmap now.
		// canvas.clipRect(new Rect(x,y,x+100,y+100));
		canvas.clipRect(new Rect(0, 0, Math.abs(left - right), Math.abs(top
				- bottom)));

		// Basic StaticLayout with mostly default values
		StaticLayout sl = new StaticLayout(msg, tp, Math.abs(left - right),
				Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
		sl.draw(canvas);

		// Restore canvas.
		canvas.restore();

	}

	@Override
	public void preview(Canvas canvas) {
		setPen();
		super.setPoint();
		final TextPaint tp = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		tp.setColor(Color.BLACK);
		
		tp.setTextSize(fontSize);
		
		if(bold)
		tp.setTypeface(Typeface.DEFAULT_BOLD);
		// tp.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

		// Save the canvas state, it might be re-used later.
		canvas.save();

		// Create a padding to the left &amp; top.
		canvas.translate(left, top);

		// Clip the bitmap now.
		canvas.clipRect(new Rect(0, 0, Math.abs(left - right), Math.abs(top
				- bottom)));

		// Basic StaticLayout with mostly default values
		StaticLayout sl = new StaticLayout(msg, tp, Math.abs(left - right),
				Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

		if (Math.abs(left - right) > 10) {

			sl.draw(canvas);
		}

		// Restore canvas.
		canvas.restore();
		canvas.drawRect(left, top, right, bottom, pen);

	}

	@Override
	public boolean isFocus(int x, int y) {
		// TODO Auto-generated method stub

		return super.isFocus(x, y);
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		super.move(x, y);

	}

	@Override
	public void resize(int x, int y) {
		// TODO Auto-generated method stub
		super.resize(x, y);

	}

	@Override
	public ShapeMemento createMemento() {
		// TODO Auto-generated method stub
		return new TextMemento(this);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public void drawCorners(Canvas c) {
		c.drawRect(left, top, right, bottom, pen);
		super.drawCorners(c);

	}

	@Override
	public boolean isText() {
		return true;
	}

	public Boolean getBold() {
		return bold;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize*2;
	}

}
