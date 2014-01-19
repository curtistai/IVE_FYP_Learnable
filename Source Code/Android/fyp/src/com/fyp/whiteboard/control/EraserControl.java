package com.fyp.whiteboard.control;

import android.graphics.Path;
import android.util.Log;

import com.fyp.whiteboard.Slide;
import com.fyp.whiteboard.shape.Eraser;
import com.fyp.whiteboard.shape._Shape;

public class EraserControl extends Control{
	@Override
	public void initial(_Shape currentDrawingPath,Slide drawingSurface){
		
		drawingSurface.previewPath.setPaint(EraserSetting() );
		drawingSurface.previewPath.setPath(new Path());		
		currentDrawingPath.setPaint(EraserSetting() );
		currentDrawingPath.setPath(new Path());
		
		Log.d("Paint",currentDrawingPath.getPaint().toString());
		Log.d("Path",currentDrawingPath.getPath().toString());
	}
	
    @Override
	public void mousePressed(Path path, int x, int y) {
    	//super.mousePressed(path, x, y);
        path.moveTo( x, y );
        path.lineTo(x, y);
    }

    @Override
	public void mouseDragged(Path path, int x, int y) {
    	//super.mouseDragged(path, x, y);
        path.lineTo( x, y );
    }

    @Override
	public void mouseReleased(Path path, int x, int y) {
    	//super.mouseReleased(path, x, y);
        path.lineTo( x, y );
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public _Shape createProduct() {
		// TODO Auto-generated method stub
		return new Eraser();
	}
}

