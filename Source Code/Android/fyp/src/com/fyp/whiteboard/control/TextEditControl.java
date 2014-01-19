package com.fyp.whiteboard.control;

import android.graphics.Path;
import android.util.Log;

import com.curtis.fyp.lesson.LessonMainRightWhiteBoard;
import com.fyp.whiteboard.changeTextDialog;
import com.fyp.whiteboard.command.MoveShapeCommand;
import com.fyp.whiteboard.shape.Normal;
import com.fyp.whiteboard.shape._Shape;

public class TextEditControl extends NormalControl {

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(Path path, int x, int y) {
		super.mousePressed(path, x, y);
		
		LessonMainRightWhiteBoard.setSelectedShape(x, y);
		
		if(LessonMainRightWhiteBoard.drawingSurface.getSelectedShape() != null){
			if(!LessonMainRightWhiteBoard.drawingSurface.getSelectedShape().isText())
				LessonMainRightWhiteBoard.drawingSurface.setSelectedShape(null);
				
			
		}
		
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
	public void mouseReleased(Path path, int x, int y) {
		super.mouseReleased(path, x, y);
		
		


	}
	

}
