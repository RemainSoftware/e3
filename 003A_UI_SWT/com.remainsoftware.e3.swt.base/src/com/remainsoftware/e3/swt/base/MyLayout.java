package com.remainsoftware.e3.swt.base;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class MyLayout {
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		FillLayout fillLayout = new FillLayout ();
		fillLayout.type = SWT.VERTICAL;
		fillLayout.marginWidth = 7;
		fillLayout.marginHeight = 8;
		fillLayout.spacing = 10;
		shell.setLayout (fillLayout);

		Button button0 = new Button (shell, SWT.PUSH);
		button0.setText ("button0");

		Button button1 = new Button (shell, SWT.PUSH);
		button1.setText ("button1");

		shell.pack ();
		shell.open ();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}
}