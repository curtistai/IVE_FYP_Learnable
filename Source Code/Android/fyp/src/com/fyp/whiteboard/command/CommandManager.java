package com.fyp.whiteboard.command;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Vector;

import com.curtis.fyp.lesson.LessonMainRightWhiteBoard;
import com.fyp.whiteboard.shape._Shape;

public class CommandManager {
	private List<_Shape> currentStack;
	private List<_Shape> drawStack;

	private Vector<Command> _commandStack = new Vector<Command>();
	private Vector<Command> _redoStack = new Vector<Command>();

	public CommandManager() {
		currentStack = Collections.synchronizedList(new ArrayList<_Shape>());
		drawStack = Collections.synchronizedList(new ArrayList<_Shape>());
	}

	public void addShape(_Shape s) {
		s.setLayerLevel(1);
		currentStack.add(s);
	}

	public void removeShape(_Shape s) {
		currentStack.remove(s);
	}
	
	public void addBgShape(_Shape s) {
		s.setLayerLevel(0);
		drawStack.add(s);
	}

	public void removeBgShape(_Shape s) {
		
		drawStack.remove(s);
	}

	public void changePosition(_Shape s, int pos) {
		int position = currentStack.indexOf(s) + pos;

		if (position >= 0 && position < currentStack.size()) {
			boolean temp = currentStack.remove(s);
			if (temp) {

				currentStack.add(position, s);

			}

		}

	}

	/* New method */
	public void addCommand(Command command) {
		_commandStack.add(command);
		_redoStack.clear();
	}

	public void undo() {
		final int length = _commandStack.size();

		if (length > 0) {
			final Command undoCommand = _commandStack.remove(length - 1);
			undoCommand.undo(this);
			_redoStack.add(undoCommand);
		}

	}

	public void redo() {
		final int length = _redoStack.size();
		if (length > 0) {
			final Command redoCommand = _redoStack.remove(length - 1);
			redoCommand.execute(this);
			_commandStack.add(redoCommand);
		}
	}

	/*
	 * public void redo() {
	 * 
	 * final int length = redoStack.toArray().length; if (length > 0) { final
	 * _Shape redoCommand = redoStack.get(length - 1); redoStack.remove(length -
	 * 1); currentStack.add(redoCommand); } } public void undo() { final int
	 * length = currentStackLength();
	 * 
	 * if (length > 0) { final _Shape undoCommand = currentStack.get(length -
	 * 1); currentStack.remove(length - 1); undoCommand.undo();
	 * redoStack.add(undoCommand); } }
	 */
	/*
	 * public int currentStackLength() { final int length =
	 * currentStack.toArray().length; return length; }
	 */

	public void executeAll(Canvas canvas, _Shape s) {
		if (drawStack != null) {
			synchronized (drawStack) {
				final Iterator i = drawStack.iterator();

				while (i.hasNext()) {
					final _Shape drawingPath = (_Shape) i.next();

					drawingPath.draw(canvas);
					// doneHandler.sendEmptyMessage(1);
				}

			}
		}
		
		if(s!=null)
			if(s.getLayerLevel()==0)
			s.preview(canvas);
			

		if (currentStack != null) {
			synchronized (currentStack) {
				final Iterator i = currentStack.iterator();

				while (i.hasNext()) {
					final _Shape drawingPath = (_Shape) i.next();

					drawingPath.draw(canvas);
					// doneHandler.sendEmptyMessage(1);
				}

			}
		}

		if(s!=null)
		if(s.getLayerLevel()>0)
		s.preview(canvas);
		//s.drawCorners(canvas);

	}

	public boolean hasMoreRedo() {
		return _redoStack.size() > 0;
	}

	public boolean hasMoreUndo() {
		return _commandStack.size() > 0;
	}

	public _Shape isFocus(int x, int y) {
		_Shape s = null;
		if (currentStack != null) {
			synchronized (currentStack) {
				final Iterator i = currentStack.iterator();

				while (i.hasNext()) {
					final _Shape drawingPath = (_Shape) i.next();
					if (drawingPath.isFocus(x, y)){
						s = drawingPath;
						Log.d("Shape ID:", s.getId()+"");
						
					}
						
					// doneHandler.sendEmptyMessage(1);

				}
				return s;
			}
		} else {
			return s;
		}
	}

	public void setButton(_Shape drawingPath) {

		if (drawingPath != null) {
			boolean top, bottom, inBg;

			bottom = (currentStack.indexOf(drawingPath) != 0);
			top = (currentStack.indexOf(drawingPath) != currentStack
					.toArray().length - 1);
			inBg = drawingPath.getLayerLevel() != 0;

			Log.d("123",
					currentStack.indexOf(drawingPath) + " "
							+ currentStack.toArray().length + " "
							+ drawingPath.getLayerLevel());

			LessonMainRightWhiteBoard.setSelectedShapeState(top, bottom, inBg);

		} else {
			LessonMainRightWhiteBoard.setSelectedShapeState(false, false, false);

		}

	}
	
	public _Shape getShapeFromId(int id ){
		_Shape s = null;
		if (currentStack != null) {
			synchronized (currentStack) {
				final Iterator i = currentStack.iterator();

				while (i.hasNext()) {
					final _Shape drawingPath = (_Shape) i.next();
					if (drawingPath.getId()==id)
						s = drawingPath;
				}
				return s;
			}
		} else {
			return s;
		}
	}
	
	public void changeById(int shapeId,int actionId){
		_Shape s = getShapeFromId(shapeId);
		
		if(s!=null){
			switch(actionId){
			case 0:
				changePosition(s,1);				
				break;
			case 1:
				changePosition(s, -1);
				break;
			case 2:
				addBgShape(s);
				removeShape(s);
				break;
			}
		}
		
		
		
		
	}
	
	

}
