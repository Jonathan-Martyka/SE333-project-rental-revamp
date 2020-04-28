package shop.command;
import java.util.Stack;

final class CommandHistoryObj implements CommandHistory {
  Stack<UndoableCommand> _undoStack = new Stack<UndoableCommand>();
  Stack<UndoableCommand> _redoStack = new Stack<UndoableCommand>();
  RerunnableCommand _undoCmd = new RerunnableCommand () {
      public boolean run () {
        boolean result = !_undoStack.empty();
        if (result) {
          // Undo
        	UndoableCommand uC = _undoStack.pop();
        	uC.undo();
        	_redoStack.push(uC);
        } // else throw new EmptyStackException();
        return result;
      }
    };
  RerunnableCommand _redoCmd = new RerunnableCommand () {
      public boolean run () {
        boolean result = !_redoStack.empty();
        if (result) {
          // Redo
        	UndoableCommand uC = _redoStack.pop();
        	uC.redo();
        	_undoStack.push(uC);
        } // else throw new EmptyStackException();
        return result;
      }
    };

  public void add(UndoableCommand cmd) {
    _undoStack.add(cmd);
    if (!_redoStack.empty())
    	_redoStack.clear();
  }
  
  public RerunnableCommand getUndo() {
    return _undoCmd;
  }
  
  public RerunnableCommand getRedo() {
    return _redoCmd;
  }
  
  // For testing
  UndoableCommand topUndoCommand() {
    if (_undoStack.empty())
      return null;
    else
      return _undoStack.peek();
  }
  // For testing
  UndoableCommand topRedoCommand() {
    if (_redoStack.empty())
      return null;
    else
      return _redoStack.peek();
  }
}