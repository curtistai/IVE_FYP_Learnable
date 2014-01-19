package com.fyp.whiteboard.control;

import android.graphics.Path;
import android.util.Log;

import com.curtis.fyp.lesson.LessonMainRightWhiteBoard;
import com.fyp.whiteboard.Slide;
import com.fyp.whiteboard.command.MoveShapeCommand;
import com.fyp.whiteboard.memento.ShapeMemento;
import com.fyp.whiteboard.shape.Normal;
import com.fyp.whiteboard.shape._Shape;

public class NormalControl extends Control {
	protected int move_x, move_y;
	protected ShapeMemento sm;
	protected boolean change;

	@Override
	public void initial(_Shape currentDrawingPath, Slide drawingSurface) {

		// drawingSurface=this.drawingSurface;

	}

	@Override
	public void mousePressed(Path path, int x, int y) {
		super.mousePressed(path, x, y);
		
		LessonMainRightWhiteBoard.setSelectedShape(x , y);
		
		change = false;

		move_x = x;
		move_y = y;

		String msg = (LessonMainRightWhiteBoard.drawingSurface.getSelectedShape() == null) ? "none"
				: "id = "
						+ LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().getId();
		Log.d("Normal", msg);

		if (LessonMainRightWhiteBoard.drawingSurface.getSelectedShape() != null) {
			sm = LessonMainRightWhiteBoard.drawingSurface.getSelectedShape()
					.createMemento();
			LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().checkCorner(move_x,
					move_y);

		}

	}

	@Override
	public void mouseDragged(Path path, int x, int y) {
		super.mouseDragged(path, x, y);

		if (LessonMainRightWhiteBoard.drawingSurface.getSelectedShape() != null) {
			
			if(x!=move_x&&y!=move_y){
				change = true;
				
			}
			
			if (!LessonMainRightWhiteBoard.drawingSurface.getSelectedShape()
					.isSelectedCorner()) {
				LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().move(x - move_x,
						y - move_y);
			} else {
				LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().resize(
						x - move_x, y - move_y);
			}
			move_x = x;
			move_y = y;
		}

	}

	@Override
	public void mouseReleased(Path path, int x, int y) {
		super.mouseReleased(path, x, y);

		if (LessonMainRightWhiteBoard.drawingSurface.getSelectedShape() != null) {
			if (!LessonMainRightWhiteBoard.drawingSurface.getSelectedShape()
					.isSelectedCorner()) {
				LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().move(x - move_x,
						y - move_y);
			} else {
				LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().resize(
						x - move_x, y - move_y);
			}
			
			if(change){
				MoveShapeCommand c = new MoveShapeCommand(LessonMainRightWhiteBoard.drawingSurface.getSelectedShape(), sm);
				LessonMainRightWhiteBoard.drawingSurface.addCommand(c);
				
			}
			
			
			move_x = x;
			move_y = y;

			move_x = 0;
			move_y = 0;
			
			
		}

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public _Shape createProduct() {
		// TODO Auto-generated method stub
		return new Normal();
	}

	@Override
	public boolean isCreateControl() {
		return false;
	}

}
