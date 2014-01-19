package com.fyp.whiteboard.command;

import com.fyp.whiteboard.memento.Memento;
import com.fyp.whiteboard.memento.ShapeMemento;
import com.fyp.whiteboard.shape._Shape;



public class MoveShapeCommand implements Command {

	private Memento _newMem, _oldMem;
	
	public MoveShapeCommand(_Shape s, ShapeMemento omem) {
		_newMem = s.createMemento();
		_oldMem = omem;
	}
	

	@Override
	public void execute(CommandManager cm) {
		// TODO Auto-generated method stub
		_newMem.restore();
	}


	@Override
	public void undo(CommandManager cm) {
		// TODO Auto-generated method stub
		_oldMem.restore();
	}

}
