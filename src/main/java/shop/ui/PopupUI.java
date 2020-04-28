package shop.ui;

import javax.swing.JOptionPane;
//import java.io.IOException;

final class PopupUI implements UI {
  PopupUI() {}

  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(null,message);
  }

  public void displayError(String message) {
    JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
  }

  public void processMenu(UIDisplay menu) {
    StringBuffer b = new StringBuffer();
    b.append(menu.getHeading());
    b.append("\n");
    b.append("Enter choice by number:");
    b.append("\n");

    for (int i = 1; i < menu.size(); i++) {
      b.append("  " + i + ". " + menu.getPrompt(i));
      b.append("\n");
    }

    String response = JOptionPane.showInputDialog(b.toString());
    int selection;
    try {
      selection = Integer.parseInt(response, 10);
      if ((selection < 0) || (selection >= menu.size()))
        selection = 0;
    } catch (NumberFormatException e) {
      selection = 0;
    }

    menu.runAction(selection);
  }

  public String[] processForm(UIDisplay form) {
    // TODO  
	 // String response = JOptionPane.showInputDialog(form.getPrompt(i));
	  displayMessage(form.getHeading());
	  String[] response = new String[form.size()];
	  
	  for (int i = 0; i < form.size(); i++) {
		  //displayMessage(form.getPrompt(i));
		  response[i] = JOptionPane.showInputDialog(form.getPrompt(i));
		  if (!form.checkInput(i, response[i])) {
			  displayError("Invalid Input: \"" + response[i] + "\"");
			  i--;
		  }
	  }
	  
	 return response;
  }
}
