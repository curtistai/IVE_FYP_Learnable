package com.fyp.whiteboard.control;

import com.fyp.whiteboard.shape.Line;
import com.fyp.whiteboard.shape._Shape;

import android.graphics.Path;

public class CreationLineControl extends Control{
    @Override
	public void mousePressed(Path path, int x, int y) {
    	//super.mousePressed(path, x, y);
        path.moveTo( x, y );
        path.lineTo(x, y);
        
      
    }

    @Override
	public void mouseDragged(Path path, int x, int y) {
    	//super.mouseDragged(path, x, y);
        path.setLastPoint(x, y);
        
    }

    @Override
	public void mouseReleased(Path path, int x, int y) {
    	//super.mouseReleased(path, x, y);
        //path.rMoveTo( x, y );
    	 path.setLastPoint(x, y);
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public _Shape createProduct() {
		// TODO Auto-generated method stub
		return new Line();
	}
}
