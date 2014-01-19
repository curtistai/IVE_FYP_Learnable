package com.fyp.whiteboard.control;

import android.graphics.Path;

import com.fyp.whiteboard.shape._Shape;
import com.fyp.whiteboard.shape.text;

public class TextControl extends Control{
	String msg="";
	int fontSize;
	boolean bold;
	
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
    	///super.mouseReleased(path, x, y);
        path.lineTo( x, y );
        //DrawingTool.setControl(DrawingTool.cNormal);
    }

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public _Shape createProduct() {
		// TODO Auto-generated method stub
		text t = new text();
		t.setMsg(msg);
		t.setFontSize(fontSize);
		t.setBold(bold);
		return t;
	}
	
	@Override
	public boolean backToNoraml(){
		return true;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}
}