package shop.ui;

public interface UI {
  public void processMenu(UIDisplay menu);
  public String[] processForm(UIDisplay form);
  public void displayMessage(String message);
  public void displayError(String message);
}
