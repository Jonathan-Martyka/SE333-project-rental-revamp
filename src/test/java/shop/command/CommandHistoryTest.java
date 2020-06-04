package shop.command;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class CommandHistoryTest {

  CommandHistoryObj h;

  @BeforeEach
  private void setupCommandHistory() {
    h = new CommandHistoryObj();
  }

  @Test
  @DisplayName("Test fresh Undo stack top is null")
  public void testEmptyUndo() {
    assertNull(h.topUndoCommand());
  }

  @Test
  @DisplayName("Test fresh Redo stack top is null")
  public void testEmptyRedo() {
    assertNull(h.topRedoCommand());
  }

  @Test
  @DisplayName("Test fresh Undo stack top is false when run")
  public void testRunEmptyUndo() {
    assertFalse(h.getUndo().run());
  }

  @Test
  @DisplayName("Test fresh Redo stack top is false when run")
  public void testRunEmptyRedo() {
    assertFalse(h.getRedo().run());
  }

  //checks top of Undo Stack and Redo Stack
  private void checkStacks(UndoableCommand topUndo, UndoableCommand topRedo) {
    assertSame(topUndo, h.topUndoCommand());
    assertSame(topRedo, h.topRedoCommand());
  }


  @Test
  @DisplayName("Test that one undo + one redo results in correct stack tops")
  public void testThatTopIsSetByOneAddUndoAndRedo() {
    UndoableCommand x1 = mock(UndoableCommand.class);

    h.add(x1);
    checkStacks(x1, null);
    h.getUndo().run();
    checkStacks(null, x1);
    h.getRedo().run();
    checkStacks(x1, null);
  }

  @Test
  @DisplayName("Test that two undo + two redo results in correct stack tops")
  public void testThatTopIsSetByTwoAddUndoAndRedo() {
    UndoableCommand x1 = mock(UndoableCommand.class);
    UndoableCommand x2 = mock(UndoableCommand.class);

    h.add(x1);
    h.getUndo().run();
    h.getRedo().run();

    h.add(x2);
    checkStacks(x2, null);
    h.getUndo().run();
    checkStacks(x1, x2);
    h.getUndo().run();
    checkStacks(null, x1);
    h.getRedo().run();
    checkStacks(x1, x2);
    h.getRedo().run();
    checkStacks(x2, null);
  }

  @Test
  @DisplayName("Test that three undo + three redo results in correct stack tops")
  public void testThatTopIsSetByThreeAddUndoAndRedo() {
    UndoableCommand x1 = mock(UndoableCommand.class);
    UndoableCommand x2 = mock(UndoableCommand.class);
    UndoableCommand x3 = mock(UndoableCommand.class);

    h.add(x1);
    h.getUndo().run();
    h.getRedo().run();

    h.add(x2);
    h.getUndo().run();
    h.getUndo().run();
    h.getRedo().run();
    h.getRedo().run();

    h.getUndo().run();
    checkStacks(x1, x2);
    h.add(x3);
    checkStacks(x3, null);
    h.getUndo().run();
    checkStacks(x1, x3);
    h.getUndo().run();
    checkStacks(null, x1);
    h.getRedo().run();
    checkStacks(x1, x3);
    h.getRedo().run();
    checkStacks(x3, null);
  }

  @Test
  @DisplayName("Test that three undo + three redo results in correct stack tops on new history")
  public void testThatTopIsSetByThreeAddUndoAndRedoNewHistory() {
    UndoableCommand x1 = mock(UndoableCommand.class);
    UndoableCommand x2 = mock(UndoableCommand.class);
    UndoableCommand x3 = mock(UndoableCommand.class);

    h.add(x1);
    h.getUndo().run();
    h.getRedo().run();

    h.add(x2);
    h.getUndo().run();
    h.getUndo().run();
    h.getRedo().run();
    h.getRedo().run();

    h.getUndo().run();
    h.add(x3);
    h.getUndo().run();
    h.getUndo().run();
    h.getRedo().run();
    h.getRedo().run();

    h = new CommandHistoryObj();
    h.add(x1);
    checkStacks(x1, null);
    h.add(x2);
    checkStacks(x2, null);
    h.getUndo().run();
    checkStacks(x1, x2);
    h.getRedo().run();
    checkStacks(x2, null);
    h.add(x3);
    checkStacks(x3, null);
    h.getUndo().run();
    checkStacks(x2, x3);
    h.getUndo().run();
    checkStacks(x1, x2);
  }

  @Test
  @DisplayName("Test undo command was performed")
  public void testUndoPerformed() {
    UndoableCommand mockCommand = mock(UndoableCommand.class);

    h.add(mockCommand);
    h.getUndo().run();
    verify(mockCommand).undo();
  }

  @Test
  @DisplayName("Test redo command was performed")
  public void testRedoPerformed() {
    UndoableCommand mockCommand = mock(UndoableCommand.class);

    h.add(mockCommand);
    h.getUndo().run();
    h.getRedo().run();
    verify(mockCommand).redo();
  }
}
