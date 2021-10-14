package com.remainsoftware.e3.swt.base;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.ViewPart;

public class HelloWorld extends ViewPart {

	public HelloWorld() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {

		FillLayout layout = new FillLayout();
		layout.type = SWT.HORIZONTAL;

		parent.setLayout(layout);

		String name = parent.getLayout().getClass().getName();
		System.out.println(name);

		Button button = new Button(parent, SWT.PUSH);
		button.setText("Push me");
		button.addListener(SWT.Selection, event -> MessageDialog.openConfirm(parent.getShell(), "Pushed", "Pushed"));

		new Button(parent, SWT.PUSH).setText("Push me, also.");

		new Button(parent, SWT.PUSH).setText("Push me, again.");

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
