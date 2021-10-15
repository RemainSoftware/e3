package org.eclipse.nebula.widgets.abutton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AButtonSnippet {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));

		Label fnLabel = new Label(shell, SWT.NONE);
		fnLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		fnLabel.setText("First name");

		Text fn = new Text(shell, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		fn.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label lnLabel = new Label(shell, SWT.NONE);
		lnLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		lnLabel.setText("Last name");

		Text ln = new Text(shell, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		ln.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		AButton button = new AButton(shell, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		button.setText("Store in Database");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("First name:" + fn.getText());
				System.out.println("Last name:" + ln.getText());
			}
		});

		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		cancelButton.setText("Cancel");
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (button.isCancelled()) {
					button.reStart();
				} else {
					button.cancel();
				}
			}
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
