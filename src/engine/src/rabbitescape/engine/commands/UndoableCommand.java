package rabbitescape.engine.commands;

public interface UndoableCommand extends Command {
	void undo();
}
