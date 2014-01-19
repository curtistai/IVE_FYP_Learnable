package com.fyp.whiteboard.command;

import com.fyp.whiteboard.shape._Shape;

public class MovePositionCommand implements Command{
	
	private _Shape _shape;
	private int i;
	
	public MovePositionCommand(_Shape shape,int i) {
		_shape = shape;
		this.i=i;
	}
	

	@Override
	public void execute(CommandManager cm) {
		cm.changePosition(_shape, i);
		
	}

	@Override
	public void undo(CommandManager cm) {
		cm.changePosition(_shape, 0-i);
		
	}



}