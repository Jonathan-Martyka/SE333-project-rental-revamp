package shop.main;

import shop.ui.UI;
import shop.ui.UIAction;
import shop.ui.UIDisplay;
import shop.ui.UIDisplayBuilder;
import shop.ui.UIMenuAction;
import shop.ui.UIError;
import shop.ui.UIFormTest;
import shop.data.Data;
import shop.data.Inventory;
import shop.data.Video;
import shop.data.Record;
import shop.command.Command;
import java.util.Iterator;
import java.util.Comparator;

class Control {
	private static enum Prompts {
		  //Prompts contains all prompts/actions done through the UI
		  DEFAULT (new UIMenuAction() {
	        public void run() {
	          _ui.displayError("doh!");
	        }}),
		  ADDREMOVE (new UIMenuAction() {
	        public void run() {
	          String[] result1 = _ui.processForm(_getVideoForm); // read video info from user
	          Video v = Data.newVideo(result1[0], Integer.parseInt(result1[1]), result1[2]);

	          UIDisplayBuilder f = new UIDisplayBuilder();
	          f.add("Number of copies to add/remove", _numberTest);
	          String[] result2 = _ui.processForm(f.toUIDisplay(""));
	                                             
	          Command c = Data.newAddCmd(_inventory, v, Integer.parseInt(result2[0]));
	          if (! c.run()) {
	            _ui.displayError("Command failed");
	          }
	        }}),
		  CHECKIN (new UIMenuAction() {
	        public void run() {
	        	String result[] = _ui.processForm(_getVideoForm);
	        	Video v = Data.newVideo(result[0], Integer.parseInt(result[1]), result[2]);
	        	
	        	Command c = Data.newInCmd(_inventory, v);
	        	if (! c.run()) {
	                _ui.displayError("Command failed");
	            }
	        }}),
		  CHECKOUT (new UIMenuAction() {
		        public void run() {
		        	String result[] = _ui.processForm(_getVideoForm);
		        	Video v = Data.newVideo(result[0], Integer.parseInt(result[1]), result[2]);
		        	
		        	Command c = Data.newOutCmd(_inventory, v);
		        	if (! c.run()) {
		                _ui.displayError("Command failed");
		            }
		        }}),
		  PRINTINVENTORY (new UIMenuAction() {
	        public void run() {
	          _ui.displayMessage(_inventory.toString());
	        }}),
		  CLEARINVENTORY (new UIMenuAction() {
	        public void run() {
	          if (!Data.newClearCmd(_inventory).run()) {
	            _ui.displayError("Command failed");
	          }
	        }}),
		  UNDO (new UIMenuAction() {
	        public void run() {
	          if (!Data.newUndoCmd(_inventory).run()) {
	            _ui.displayError("Command failed");
	          }
	        }}),
		  REDO (new UIMenuAction() {
		        public void run() {
		            if (!Data.newRedoCmd(_inventory).run()) {
		              _ui.displayError("Command failed");
		            }
		          }}),
		  PRINTTOPTEN (new UIMenuAction() {
	        public void run() {
	        	
	        	Iterator<Record> iter = _inventory.iterator(new Comparator<Record>() {
					public int compare(Record x, Record y) {
						return y.numRentals()-x.numRentals();
					}
	        	});
	        	
	        	StringBuffer buffer = new StringBuffer();
	            buffer.append("Database Top Ten:\n");
	        	int i = 0;
	        	while (iter.hasNext() && i < 10) {
	        		buffer.append("  ");
	        		buffer.append(iter.next().toString());
	        		buffer.append("\n");
	        		i++;
	        	}
	        	_ui.displayMessage(buffer.toString());
	        }}),
		  EXIT (new UIMenuAction() {
			  public void run() {
				  STATE = States.EXIT;
			  }}),
		  INITIALIZEBOGUS (new UIMenuAction() {
		        public void run() {
		        	for (int i = 1; i <= 26; ++i) {
		        		Data.newAddCmd(_inventory, Data.newVideo("" + (char)(96 + i), 2000, "m"), i).run();
		        	}
		          }
		  });
		  
		  private UIAction prompt;
		  Prompts(UIAction prompt) {
			  this.prompt = prompt;
		  }
		  public UIAction getPrompt() {
			  return prompt;
		  }
	  }
	private static enum States {
		EXITED(new State() {
			public UIDisplay displayPrompt() {
				return null;
			}
		}), 
		EXIT(new State() {
			public UIDisplay displayPrompt() {
				 UIDisplayBuilder m = new UIDisplayBuilder();
				    
				    m.add("Default", new UIMenuAction() { public void run() {} });
				    m.add("Yes",
				      new UIMenuAction() {
				        public void run() {
				          STATE = States.EXITED;
				        }
				      });
				    m.add("No",
				      new UIMenuAction() {
				        public void run() {
				          STATE = States.START;
				        }
				      });
				    
				 return m.toUIDisplay("Are you sure you want to exit?");
			}
		}), 
		START(new State() {
			public UIDisplay displayPrompt() {
				UIDisplayBuilder m = new UIDisplayBuilder();
			    
			    m.add("Default", Prompts.DEFAULT.getPrompt());
			    m.add("Add/Remove copies of a video", Prompts.ADDREMOVE.getPrompt());
			    m.add("Check in a video", Prompts.CHECKIN.getPrompt());
			    m.add("Check out a video", Prompts.CHECKOUT.getPrompt());
			    m.add("Print the inventory", Prompts.PRINTINVENTORY.getPrompt());
			    m.add("Clear the inventory", Prompts.CLEARINVENTORY.getPrompt());
			    m.add("Undo", Prompts.UNDO.getPrompt());
			    m.add("Redo", Prompts.REDO.getPrompt());
			    m.add("Print top ten all time rentals in order", Prompts.PRINTTOPTEN.getPrompt());
			    m.add("Exit", Prompts.EXIT.getPrompt());
			    m.add("Initialize with bogus contents", Prompts.INITIALIZEBOGUS.getPrompt());
			    
			    return m.toUIDisplay("Bob's Video");
			}
		});
		private State currState;
		States(State currState) {
			this.currState = currState;
		}
		public State getState() {
			return currState;
		}
	}
	
	private static States STATE;

  private static UIDisplay _getVideoForm;
  private static UIFormTest _numberTest;
  private UIFormTest _stringTest;
    
  private static Inventory _inventory;
  private static UI _ui;
  
  
  
  Control(Inventory inventory, UI ui) {
    _inventory = inventory;
    _ui = ui;
    STATE = States.START;
    
    UIFormTest yearTest = new UIFormTest() {
        public boolean run(String input) {
          try {
            int i = Integer.parseInt(input);
            return i > 1800 && i < 5000;
          } catch (NumberFormatException e) {
            return false;
          }
        }
      };
    _numberTest = new UIFormTest() {
        public boolean run(String input) {
          try {
            Integer.parseInt(input);
            return true;
          } catch (NumberFormatException e) {
            return false;
          }
        }
      };
    _stringTest = new UIFormTest() {
        public boolean run(String input) {
          return ! "".equals(input.trim());
        }
      };

    UIDisplayBuilder f = new UIDisplayBuilder();
    f.add("Title", _stringTest);
    f.add("Year", yearTest);
    f.add("Director", _stringTest);
    _getVideoForm = f.toUIDisplay("Enter Video");
  }
  
  void run() {
    try {
      while (STATE != States.EXITED) {
        _ui.processMenu(runState());
      }
    } catch (UIError e) {
      _ui.displayError("UI closed");
    }
  }
  
  private UIDisplay runState() {
	  return STATE.getState().displayPrompt();
  }
}
