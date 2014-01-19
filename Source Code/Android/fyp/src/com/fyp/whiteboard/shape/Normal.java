package com.fyp.whiteboard.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.fyp.whiteboard.memento.ShapeMemento;

public class Normal extends _Shape {
	public Normal() {
		super();
	}


	@Override
	public void draw(Canvas canvas) {
		

	}

	@Override
	public void undo() {
		// Todo this would be changed later
	}

	@Override
	public void setPaint(Paint paint) {
		
	}

	@Override
	public void preview(Canvas canvas) {
	
		
	}

	@Override
	public boolean isFocus(int x, int y) {
		
		return false;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void move(int _x, int _y) {
		
		
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
