package org.eclipse.nebula.widgets.abutton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AButtonSnippet {

	private static Text text;

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, true));

		shell.setSize(600, 600);

		AButton button = new AButton(shell, SWT.PUSH);
		button.cancel();
		button.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		button.setText("Automatic Button");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(text.getText() + " Button fired");
			}
		});

		Button button2 = new Button(shell, SWT.PUSH);
		button2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		button2.setText("Start");
		button2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText("");
				button.start();
			}
		});

		text = new Text(shell, SWT.MULTI | SWT.WRAP | SWT.LEAD | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
