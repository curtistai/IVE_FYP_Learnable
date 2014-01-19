package com.fyp.whiteboard.control;

import android.graphics.Path;

import com.fyp.whiteboard.shape.Circle;
import com.fyp.whiteboard.shape._Shape;

public class CreationCirControl extends Control{
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
			return new Circle();
		}
}
