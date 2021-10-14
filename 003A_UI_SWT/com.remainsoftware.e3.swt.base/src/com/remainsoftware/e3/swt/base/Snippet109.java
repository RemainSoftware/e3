package com.remainsoftware.e3.swt.base;

/*
 * SashForm example snippet: create a sash form with three children
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet109 {

public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell(display);
	shell.setText("Snippet 109");
	shell.setLayout (new FillLayout());

	SashForm form = new SashForm(shell,SWT.HORIZONTAL | SWT.BORDER);
	form.setLayout(new FillLayout());

	Composite child1 = new Composite(form,SWT.NONE);
	child1.setLayout(new FillLayout());
	new Label(child1,SWT.NONE).setText("Label in pane 1");

	Composite child2 = new Composite(form,SWT.NONE);
	child2.setLayout(new FillLayout());
	new Button(child2,SWT.PUSH).setText("Button in pane2");

	Composite child3 = new Composite(form,SWT.NONE);
	child3.setLayout(new FillLayout());
	new Label(child3,SWT.PUSH).setText("Label in pane3");

	form.setWeights(new int[] {30,40,30});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
