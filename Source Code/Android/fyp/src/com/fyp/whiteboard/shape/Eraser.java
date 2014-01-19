package com.fyp.whiteboard.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.fyp.whiteboard.memento.ShapeMemento;

public class Eraser extends _Shape{
	int thickness;
	
	
	public Eraser(){
		super();
		layerLevel=0;
		//paint.setStyle(Paint.Style.STROKE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setPaint(Paint paint) {
		this.paint = paint;
		paint.setStyle(Paint.Style.STROKE);
		
	}


	@Override
	public void draw(Canvas canvas) {   
    	
        canvas.drawPath(path, paint);
    	
    }
    
 

	@Override
	public void undo() {
        //Todo this would be changed later
    }

	@Override
	public void preview(Canvas canvas) {
		// TODO Auto-generated method stub
		//paint.setAlpha(125);
		canvas.drawPath(path, paint);
		
	}



	@Override
	public boolean isFocus(int x, int y) {
		// TODO Auto-generated method stub
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
