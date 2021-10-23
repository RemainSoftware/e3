package org.eclipse.nebula.widgets.abutton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class AButtonSnippet {

	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setSize(600, 100);

		int width = display.getBounds().width;
		int height = display.getBounds().height;

		shell.setLocation(width / 2 - (600 / 2), height / 2 - (300 / 2));

		AButton aButton = new AButton(shell, SWT.PUSH);
		aButton.setText("> Next Episode");
		aButton.addListener(SWT.Selection, e -> System.out.println("Triggered..."));
		aButton.setAnimationTime(10000) //
				.setBorderSize(3);

		Button button = new Button(shell, SWT.PUSH);
		button.setText("Activate");
		button.addListener(SWT.Selection, e -> aButton.start());

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
