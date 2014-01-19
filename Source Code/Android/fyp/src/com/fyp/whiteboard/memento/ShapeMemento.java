package com.fyp.whiteboard.memento;

import com.fyp.whiteboard.shape._Shape;

public class ShapeMemento implements Memento{
	
	

	protected int left=0, top=0, right = 0 , bottom = 0;
	protected _Shape shape;
	
	public ShapeMemento(_Shape shape) {
		super();
		this.shape = shape;
		left = shape.getLeft();
		top = shape.getTop();
		right = shape.getRight();
		bottom = shape.getBottom();
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		shape.setTop(top);
		shape.setBottom(bottom);
		shape.setLeft(left);
		shape.setRight(right);
		
	}

}
