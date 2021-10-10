package com.remainsoftware.e3.viewmenus;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.part.ViewPart;

public class HelloWorld extends ViewPart {

	public HelloWorld() { //
	}

	@Override
	public void createPartControl(Composite parent) {
		registerContextMenu(parent);
	}

	private void registerContextMenu(Composite parent) {
		MenuManager manager = new MenuManager();
		Menu contextMenu = manager.createContextMenu(parent);
		getSite().registerContextMenu(manager, null);
		parent.setMenu(contextMenu);
	}

	@Override
	public void setFocus() {
	}
}
