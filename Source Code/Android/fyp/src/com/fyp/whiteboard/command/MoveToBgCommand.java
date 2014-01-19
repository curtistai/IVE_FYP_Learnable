package com.fyp.whiteboard.command;

import com.fyp.whiteboard.shape._Shape;

public class MoveToBgCommand implements Command{
	
	private _Shape _shape;

	
	public MoveToBgCommand(_Shape shape) {
		_shape = shape;
	}
	

	@Override
	public void execute(CommandManager cm) {
		cm.addBgShape(_shape);
		cm.removeShape(_shape);
		
	}

	@Override
	public void undo(CommandManager cm) {
		cm.addShape(_shape);
		cm.removeBgShape(_shape);
		
	}



}
