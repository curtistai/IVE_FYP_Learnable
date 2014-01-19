package com.fyp.whiteboard.shape;

import com.fyp.whiteboard.memento.CircleMemento;
import com.fyp.whiteboard.memento.ShapeMemento;

import android.graphics.Canvas;
import android.graphics.PathMeasure;
import android.util.Log;

public class Circle extends _Shape{
    
    int r=0 , x=0 ,y=0;
   
    
    public Circle(){
    	super();
    	layerLevel=2;
    }
    
    @Override
	public void setPoint(){
    	PathMeasure pm = new PathMeasure(path, false);
        float aCoordinates[] = {0f, 0f};
        int fx ,fy , ex ,ey;
        
        pm.getPosTan(pm.getLength() * 0f, aCoordinates, null);
        fx = (int)aCoordinates[0];
        fy=(int)aCoordinates[1];
        pm.getPosTan(pm.getLength() * 1f, aCoordinates, null);
        ex=(int)aCoordinates[0];
        ey=(int)aCoordinates[1];
        
        x = (fx + ex ) / 2 ;
        y = (fy + ey ) / 2 ;
        r = (int) Math.sqrt((x-fx)*(x-fx)+(y-fy)*(y-fy));
        left= fx;
		top = fy;
		right = ex;
		bottom = ey;
    }

	@Override
	public void draw(Canvas canvas) {   
    	
        canvas.drawCircle(x, y, r, paint);  
        if(_border)
        	canvas.drawCircle(x, y, r,B_paint);  
        
    }
    

	@Override
	public void undo() {
        //Todo this would be changed later
    }



	@Override
	public void preview(Canvas canvas) {
		// TODO Auto-generated method stub
		//paint.setAlpha(125);
		setPoint();
        canvas.drawCircle(x, y, r, paint); 	
        if(_border)
        	canvas.drawCircle(x, y, r,B_paint);
	}

	@Override
	public boolean isFocus(int x, int y) {
		// TODO Auto-generated method stub
		int d = (int) Math.sqrt((x-this.x)*(x-this.x)+(y-this.y)*(y-this.y));
		if(d<=r)
			return true;
		else
			return false;
	}

	@Override
	public void move(int _x, int _y) {
		// TODO Auto-generated method stub
		x+=_x;
		y+=_y;
		
	}

	@Override
	public ShapeMemento createMemento() {
		// TODO Auto-generated method stub
		return new CircleMemento(this);
	}
	
	@Override
	public void drawCorners(Canvas c) {
		// draw the corners for resize
		int hw = _cornerSize/2;
		//top
		c.drawRect(x-hw, y-r-hw, x+hw, y-r+hw,cpaint);
		//middle
		c.drawRect(x-r-hw, y-hw, x-r+hw, y+hw,cpaint);
		c.drawRect(x-hw, y-hw, x+hw, y+hw,cpaint);
		c.drawRect(x+r-hw, y-hw, x+r+hw, y+hw,cpaint);
		//bottom
		c.drawRect(x-hw, y+r-hw, x+hw, y+r+hw,cpaint);	
		//top
		c.drawRect(x-hw, y-r-hw, x+hw, y-r+hw,B_cpaint);
		//middle
		c.drawRect(x-r-hw, y-hw, x-r+hw, y+hw,B_cpaint);
		c.drawRect(x-hw, y-hw, x+hw, y+hw,B_cpaint);
		c.drawRect(x+r-hw, y-hw, x+r+hw, y+hw,B_cpaint);
		//bottom
		c.drawRect(x-hw, y+r-hw, x+hw, y+r+hw,B_cpaint);
		
		
	}

	@Override
	public void resize(int _x, int _y) {
					
			int ox, oy, ow, oh;
			/*
			ox = mem.getX();
			oy = mem.getY();
			ow = mem.getWidth();
			oh = mem.getHeight();*/
			int newx, newy;
			Log.d("s",_selectedCorner+"" );
			switch (_selectedCorner){
			
			case TL:
				r-=_y;
							
				break;
			case TR:
												
				break;
			case BL:
				r+=_y;	
								
				break;
			case BR:
				
				break;	
			case TM:
								
				break;
			case BM:
						
				break;		
			case LM:
				r-=_x;			
				break;
			case RM:
				r+=_x;					
				break;	
			default:
				
				break;
		}
			
			
		
		
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	/*
	@Override
	public boolean checkCorner(int x, int y) { 
		_selectedCorner = -1;
		int hw = _cornerSize/2;
		if(contain(x,y,(left+right)/2-hw, top-hw, _cornerSize, _cornerSize))
			_selectedCorner = TM;
		else if (contain(x,y,left-hw, (top+bottom)/2-hw, _cornerSize, _cornerSize))
			_selectedCorner = LM;
		else if (contain(x,y,right-hw, (top+bottom)/2-hw, _cornerSize, _cornerSize))
			_selectedCorner = RM;
		else if (contain(x,y,(left+right)/2-hw, bottom-hw, _cornerSize, _cornerSize))
			_selectedCorner = BM;

		//else if (this.contains(x, y))
		//	_selectedCorner = C;
		
		Log.d("Corner", " "+_selectedCorner);
		
		retur
*/
	

	
}
