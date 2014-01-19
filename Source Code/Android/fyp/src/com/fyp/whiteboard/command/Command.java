package com.fyp.whiteboard.command;

public interface Command {
	public void execute(CommandManager cm);
	public void undo(CommandManager cm);
}
