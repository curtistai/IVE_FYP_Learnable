package com.fyp.whiteboard.command;

import com.fyp.whiteboard.shape._Shape;



public class CreateShapeCommand implements Command{
	
	private _Shape _shape;
	
	public CreateShapeCommand(_Shape shape) {
		_shape = shape;
	}
	

	@Override
	public void execute(CommandManager cm) {
		if(_shape.getLayerLevel()==0){
			cm.addBgShape(_shape);			
			
		}else
			cm.addShape(_shape);
		
	}


	@Override
	public void undo(CommandManager cm) {
	
			cm.removeBgShape(_shape);		
			cm.removeShape(_shape);
			
		
	}



}
