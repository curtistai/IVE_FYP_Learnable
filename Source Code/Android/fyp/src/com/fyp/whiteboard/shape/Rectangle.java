package com.fyp.whiteboard.shape;

import com.fyp.whiteboard.memento.ShapeMemento;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathMeasure;

public class Rectangle  extends _Shape{
	

    public Rectangle() {
		super();
		// TODO Auto-generated constructor stub
	}

	
    
    @Override
	public void setPoint(){
    	PathMeasure pm = new PathMeasure(path, false);
        //coordinates will be here
        float aCoordinates[] = {0f, 0f};
        //get point from the middle
        pm.getPosTan(pm.getLength() * 0f, aCoordinates, null);
        setPoint1((int)aCoordinates[0],(int)aCoordinates[1]);
        pm.getPosTan(pm.getLength() * 1f, aCoordinates, null);
        setPoint2((int)aCoordinates[0],(int)aCoordinates[1]);
        
       
    }

	@Override
	public void draw(Canvas canvas) {   
    	
        canvas.drawRect(left, top, right, bottom, paint);
        if(_border)
        	canvas.drawRect(left, top, right, bottom, B_paint);
        
        /*
        
        canvas.save();
		canvas.translate(left+10, top+10);
        final TextPaint tp = new TextPaint();
		tp.setColor(Color.BLACK);
        
        StaticLayout sl = new StaticLayout("This is used by widgets to control text layout. You should not need to use this class directly unless you are implementing your own widget or custom display object, or would be tempted to call Canvas.drawText() directly.", tp, Math.abs(left-right), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
		sl.draw(canvas);
		canvas.restore();
		*/
    	
    }
    
    public void setPoint1(int x,int y){
    	left = x;
    	top = y;
    	
    	
    }

    public void setPoint2(int x,int y){
    	right=x;
    	bottom = y;
    	
    }

	@Override
	public void undo() {
        //Todo this would be changed later
    }

	@Override
	public void preview(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint vpaint = new Paint();
		vpaint.setColor(Color.GREEN);
		vpaint.setStyle(Style.FILL);
		setPoint();
		canvas.drawRect(left, top, right, bottom, paint); 
		 if(_border)
		canvas.drawRect(left, top, right, bottom, B_paint);
 
		
	}

	@Override
	public boolean isFocus(int x, int y) {
		if((left<x&&x<right&&top<y&&y<bottom)||(left>x&&x>right&&top>y&&y>bottom)||
				(left>x&&x>right&&top<y&&y<bottom)||(left<x&&x<right&&top>y&&y>bottom))
			return true;
		else
		return false;
	}
	
	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub
		left+=x;
		top+=y;
		right+=x;
		bottom+=y;
		
	}



	@Override
	public ShapeMemento createMemento() {
		
		return new ShapeMemento(this);
	}



	@Override
	public void resize(int x, int y) {
		
		int ox, oy, ow, oh;
		/*
		ox = mem.getX();
		oy = mem.getY();
		ow = mem.getWidth();
		oh = mem.getHeight();*/
		int newx, newy;
		
		switch (_selectedCorner) {
			case TL:
				top+=y;
				left+=x;				
				break;
			case TR:
				top+=y;
				right+=x;								
				break;
			case BL:
				bottom+=y;
				left+=x;					
				break;
			case BR:
				bottom+=y;
				right+=x;
				break;	
			case TM:
				top+=y;				
				break;
			case BM:
				bottom+=y;			
				break;		
			case LM:
				left+=x;			
				break;
			case RM:
				right+=x;					
				break;	
			default:
				
				break;
		}
		
	}


}
