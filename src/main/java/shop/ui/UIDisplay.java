package shop.ui;

/**
 * @see UIFormBuilder
 */
public final class UIDisplay {
  private final String _heading;
  private final Pair[] _display;

  static final class Pair <V>{
    final String prompt;
    final V _action;

    Pair(String thePrompt, V action) {
      prompt = thePrompt;
      _action = action;
    }
  }
  
  UIDisplay(String heading, Pair[] menu) {
    _heading = heading;
    _display = menu;
  }
  public int size() {
    return _display.length;
  }
  public String getHeading() {
    return _heading;
  }
  public String getPrompt(int i) {
    return _display[i].prompt;
  }
  public void runAction(int i) {
	((UIMenuAction) _display[i]._action).run();
  }
  public boolean checkInput(int i, String input) {
    if (null == _display[i])
      return true;
    return ((UIFormTest) _display[i]._action).run(input);
  }
}
