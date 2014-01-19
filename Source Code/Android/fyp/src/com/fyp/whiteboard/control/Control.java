package com.fyp.whiteboard.control;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import com.fyp.whiteboard.Slide;
import com.fyp.whiteboard.shape._Shape;

public abstract class Control  {
	/*Paint*/
	private static int currentColor = 0xFFFF0000;
	private static int currentFill = 0xFFFF0000;
	private static int pathThickness = 3;
	private static int eraserThickness = 20;
	private boolean stroke;
	
	private Slide drawingSurface;
	
	
	public Control() {};
	
	public abstract void reset();
	//public abstract boolean reset();
	public abstract _Shape createProduct();
	

	
	public boolean isCreateControl(){
		return true;
	}
	
	public void initial(_Shape currentDrawingPath,Slide drawingSurface){
		
		//drawingSurface=this.drawingSurface;
		
		drawingSurface.previewPath.setPaint(ColorSetting());
		drawingSurface.previewPath.setPath(new Path());		
		currentDrawingPath.setPaint(ColorSetting());
		currentDrawingPath.setPath(new Path());
		
		Log.d("Paint",currentDrawingPath.getPaint().toString());
		Log.d("Path",currentDrawingPath.getPath().toString());
	}
	
	public void setColour(int i){
		currentColor = i;
	}
	
	public int getColour(){
		return currentColor;
	}
	
	public void setThickness(int i){
		pathThickness = i;
	}
	public int getThickness(){
		return pathThickness;
	}
	
	protected Paint ColorSetting() {
		Paint p = new Paint();
		p.setDither(true);
		p.setColor(currentColor);
		
		if(stroke){
			p.setStyle(Paint.Style.STROKE);
			
			
		}else
			p.setStyle(Paint.Style.FILL);

		p.setStrokeJoin(Paint.Join.ROUND);
		p.setStrokeCap(Paint.Cap.ROUND);
		p.setStrokeWidth(pathThickness);
		p.setPathEffect(null);
		return p;
	}
	
	protected Paint EraserSetting() {
		Paint p = new Paint();
		p.setAlpha(0);
		p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		p.setAntiAlias(true);
		p.setDither(true);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeJoin(Paint.Join.ROUND);
		p.setStrokeCap(Paint.Cap.ROUND);
		p.setStrokeWidth(eraserThickness);
		return p;
	}

	public void mousePressed(Path path, int x , int y) {
		//if (!_slide.isFocusOwner())
		//	_slide.requestFocusInWindow(); // get the keyboard focus
		
		// output to JSON

	}

	public void mouseReleased(Path path, int x , int y) {
		// output to JSON

		
		
	}
	
	public void mouseDragged(Path path, int x , int y) {
		// output to JSON
	
	}

	public int getEraserThickness() {
		return eraserThickness;
	}

	public void setEraserThickness(int Thickness) {
		eraserThickness = Thickness;
	}

	public boolean isStroke() {
		return stroke;
	}

	public void setStroke(boolean stroke) {
		this.stroke = stroke;
	}
	
	public boolean backToNoraml(){
		return false;
	}


	
	/*
    public void keyTyped(KeyEvent e)
    {
		// output to JSON
    	KeyTyped kr = new KeyTyped();
    	kr.setKeyEvent(e);
		DrawingTool.toJSON(kr);
    }
      
    public void keyReleased(KeyEvent e)
    {
		// output to JSON
    	KeyReleased kr = new KeyReleased();
    	kr.setKeyEvent(e);
		DrawingTool.toJSON(kr);
    }
    
    public void keyPressed(KeyEvent e)
    {
		// output to JSON
    	KeyPressed kp = new KeyPressed();
    	kp.setKeyEvent(e);
		DrawingTool.toJSON(kp);
    }
    */
}