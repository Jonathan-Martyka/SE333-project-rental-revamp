package shop.ui;

import java.util.ArrayList;
import java.util.List;

public class UIDisplayBuilder {
	private final List _toDisplay;
	public UIDisplayBuilder() {
		_toDisplay = new ArrayList();
	}
	public UIDisplay toUIDisplay(String heading) {
		if (null == heading)
			throw new IllegalArgumentException();
	    if (_toDisplay.size() < 1)
	    	throw new IllegalStateException();
	    UIDisplay.Pair[] array = new UIDisplay.Pair[_toDisplay.size()];
	    for (int i = 0; i < _toDisplay.size(); i++)
	      array[i] = (UIDisplay.Pair) (_toDisplay.get(i));
	    return new UIDisplay(heading, array);
	}
	public void add(String prompt, UIAction action) {
		if (null == action)
	      throw new IllegalArgumentException();
	    _toDisplay.add(new UIDisplay.Pair(prompt, action));
	}
}
