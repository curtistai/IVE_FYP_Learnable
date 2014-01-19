package com.fyp.whiteboard.shape;

import com.fyp.whiteboard.Slide;
import com.fyp.whiteboard.memento.ShapeMemento;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

public abstract class _Shape{
	
	protected int _selectedCorner = -1;
	public static final int TL = 0; // top left
	public static final int TR = 1; // top right 
	public static final int TM = 2; // top middle 
	public static final int LM = 3; // left middle
	public static final int RM = 4; // right middle
	public static final int BL = 5; // buttom left
	public static final int BR = 6; // buttom right
	public static final int BM = 7; // buttom middle
	public static final int C = 8; // body of the shape

	protected static int _idCounter = 0;
	protected Slide _slide;
	
	public int layerLevel=2;
	
	
	
	/*border*/
	protected boolean _border = true;
	protected int _bColor = Color.BLACK;
	protected int _bThickness =3;
	
	protected boolean _selected = false;
	protected boolean _enableCorner = true;
	protected int _cornerSize = 35;
	
	/*Shape property*/
	public Path path;
    public Paint paint;
    public Paint B_paint;
    protected int left=0, top=0, right = 0 , bottom = 0;
    
    protected int _fillColor = 0xFFFF0000;
	protected Bitmap _fillImage = null;
    
    /*ID*/
	public static int next_id =1;
	int _id=0;
	
	/*Corner*/
	public Paint cpaint;
    public Paint B_cpaint;
    
    
	
	
	public _Shape(){
		B_paint = new Paint();
		B_paint.setDither(true);
		B_paint.setColor(_bColor);
		B_paint.setStyle(Paint.Style.STROKE);
		B_paint.setStrokeJoin(Paint.Join.ROUND);
		B_paint.setStrokeCap(Paint.Cap.ROUND);
		B_paint.setStrokeWidth(_bThickness);
		
		cpaint = new Paint();
		cpaint.setDither(true);
		cpaint.setColor(Color.WHITE);
		cpaint.setStyle(Paint.Style.FILL);
		
		B_cpaint = new Paint();
		B_cpaint.setDither(true);
		B_cpaint.setColor(Color.BLACK);
		B_cpaint.setStyle(Paint.Style.STROKE);
		B_cpaint.setStrokeJoin(Paint.Join.ROUND);
		B_cpaint.setStrokeCap(Paint.Cap.ROUND);
		B_cpaint.setStrokeWidth(2);
		
		
	}
	
	/*Draw shape*/

    public abstract void draw(Canvas canvas);
    public abstract void preview(Canvas canvas);
    
	public void drawCorners(Canvas c) {
		// draw the corners for resize
		int hw = _cornerSize/2;
		//top
		c.drawRect(left-hw, top-hw, left+hw, top+hw,cpaint);
		c.drawRect((left+right)/2-hw, top-hw, (left+right)/2+hw, top+hw,cpaint);
		c.drawRect(right-hw, top-hw, right+hw, top+hw,cpaint);
		//middle
		c.drawRect(left-hw, (top+bottom)/2-hw, left+hw, (top+bottom)/2+hw,cpaint);
		//c.drawRect((left+right)/2-hw, (top+bottom)/2-hw, (left+right)/2+hw, (top+bottom)/2+hw,cpaint);
		c.drawRect(right-hw, (top+bottom)/2-hw, right+hw, (top+bottom)/2+hw,cpaint);
		//bottom
		c.drawRect(left-hw, bottom-hw, left+hw, bottom+hw,cpaint);
		c.drawRect((left+right)/2-hw, bottom-hw, (left+right)/2+hw, bottom+hw,cpaint);
		c.drawRect(right-hw, bottom-hw, right+hw, bottom+hw,cpaint);
		//top
		c.drawRect(left-hw, top-hw, left+hw, top+hw,B_cpaint);
		c.drawRect((left+right)/2-hw, top-hw, (left+right)/2+hw, top+hw,B_cpaint);
		c.drawRect(right-hw, top-hw, right+hw, top+hw,B_cpaint);
		//middle
		
		c.drawRect(left-hw, (top+bottom)/2-hw, left+hw, (top+bottom)/2+hw,B_cpaint);
		//c.drawRect((left+right)/2-hw, (top+bottom)/2-hw, (left+right)/2+hw, (top+bottom)/2+hw,B_cpaint);
		c.drawRect(right-hw, (top+bottom)/2-hw, right+hw, (top+bottom)/2+hw,B_cpaint);
		
		//bottom
		c.drawRect(left-hw, bottom-hw, left+hw, bottom+hw,B_cpaint);
		c.drawRect((left+right)/2-hw, bottom-hw, (left+right)/2+hw, bottom+hw,B_cpaint);
		c.drawRect(right-hw, bottom-hw, right+hw, bottom+hw,B_cpaint);		
		
		
		
		
	}
    
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void setLocation(int x, int y) {
		left = x;
		top = y;
	}

	public void setSize(int width, int height) {
		this.right = width;
		this.bottom = height;
	}
	
    /*Focus or select*/
    
    public abstract boolean isFocus(int x , int y);
    
	public void select() {
		_selected = true;
	}
	
	public void unselect() {
		_selected = false;
	}
	
	public boolean checkCorner(int x, int y) { 
		_selectedCorner = -1;
		int hw = _cornerSize/2;
		if (contain(x,y,left-hw, top-hw, _cornerSize, _cornerSize)) 
			_selectedCorner = TL;
		else if (contain(x,y,(left+right)/2-hw, top-hw, _cornerSize, _cornerSize))
			_selectedCorner = TM;
		else if (contain(x,y,right-hw, top-hw, _cornerSize, _cornerSize))
			_selectedCorner = TR;
		else if (contain(x,y,left-hw, (top+bottom)/2-hw, _cornerSize, _cornerSize))
			_selectedCorner = LM;
		else if (contain(x,y,right-hw, (top+bottom)/2-hw, _cornerSize, _cornerSize))
			_selectedCorner = RM;
		else if (contain(x,y,left-hw, bottom-hw, _cornerSize, _cornerSize))
			_selectedCorner = BL;
		else if (contain(x,y,(left+right)/2-hw, bottom-hw, _cornerSize, _cornerSize))
			_selectedCorner = BM;
		else if (contain(x,y,right-hw, bottom-hw, _cornerSize, _cornerSize))
			_selectedCorner = BR;
		//else if (this.contains(x, y))
		//	_selectedCorner = C;
		
		Log.d("Corner", " "+_selectedCorner);
		
		return _selectedCorner >= 0;
	}
	
	public boolean contain(int x, int y, int x2, int y2, int w, int h) {
		return (x >= x2-10 && y >= y2-10 && x <=x2 + w+10 && y <= y2	+ h+10);
	}
	
	public boolean isSelectedCorner(){
		return _selectedCorner >= 0;
	}
	
	public boolean isText(){
		return false;		
	}
	

    
	
	
	/*change*/
	public abstract void move(int x , int y);
	
	public abstract void resize(int x, int y);
	
	
	public void changePaint(Paint p){
		paint = p;	
		
	}
	
	public void setFillColor(int c){
		paint.setColor(c);
		
	}
	
	public void setFillImage(){
		//canvas cannot draw bitmap???
		
	}
	
	
	
	/*id*/
	public int getId(){
		return _id;
	}
	
	
	public void setID(){
		_id = next_id;
    	next_id++;
	}
	
	/*for rectangle and circle only*/
	public void setPoint(){
		
	}
	
	/*Memento*/
	public abstract ShapeMemento createMemento();
	public abstract void undo();

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getLayerLevel() {
		return layerLevel;
	}

	public void setLayerLevel(int layerLevel) {
		this.layerLevel = layerLevel;
	}

	public int get_bColor() {
		return _bColor;
	}

	public void set_bColor(int _bColor) {
		this._bColor = _bColor;
	}

	public boolean is_border() {
		return _border;
	}

	public void set_border(boolean _border) {
		this._border = _border;
	}
    
}
