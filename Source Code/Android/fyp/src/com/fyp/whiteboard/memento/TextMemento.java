package com.fyp.whiteboard.memento;

import org.w3c.dom.Text;

import com.fyp.whiteboard.shape._Shape;
import com.fyp.whiteboard.shape.text;

public class TextMemento extends ShapeMemento{
	protected String msg;

	public TextMemento(text shape) {
		super(shape);
		this.msg =  shape.getMsg();
		// TODO Auto-generated constructor stub
	}
	
	public void restore() {
		super.restore();
		((text)shape).setMsg(msg);
	}

}
