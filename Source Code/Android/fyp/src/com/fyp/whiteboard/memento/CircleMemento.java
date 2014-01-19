package com.fyp.whiteboard.memento;

import com.fyp.whiteboard.shape.Circle;



public class CircleMemento extends ShapeMemento {
	protected int r=0 , x=0 ,y=0;
	protected Circle shape;
	
	public CircleMemento(Circle shape) {
		super(shape);
		r = shape.getR();
		x = shape.getX();
		y = shape.getY();
		this.shape = shape;
	}

	@Override
	public void restore() {
		shape.setR(r);
		shape.setX(x);
		shape.setY(y);
		
		
	}

}
