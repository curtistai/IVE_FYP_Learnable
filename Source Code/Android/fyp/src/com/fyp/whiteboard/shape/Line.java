package com.fyp.whiteboard.shape;

import com.fyp.whiteboard.memento.ShapeMemento;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line extends _Shape {
	public Line() {
		super();
	}


	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawPath(path, paint);

	}

	@Override
	public void undo() {
		// Todo this would be changed later
	}

	@Override
	public void setPaint(Paint paint) {
		this.paint = paint;
		paint.setStyle(Paint.Style.STROKE);
	}

	@Override
	public void preview(Canvas canvas) {
		// TODO Auto-generated method stub
		//paint.setAlpha(125);
		canvas.drawPath(path, paint);
		
	}

	@Override
	public boolean isFocus(int x, int y) {
		
		return false;
	}


	
	@Override
	public void move(int _x, int _y) {
		// TODO Auto-generated method stub
		path.offset(_x, _y);
		
	}


	@Override
	public ShapeMemento createMemento() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void resize(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
