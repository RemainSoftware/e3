package com.remainsoftware.e3.swt.base;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class HelloSWT {

	static int x = 30;
	static int y = 30;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setSize(1200, 800);

		Button b = new Button(shell, SWT.PUSH);
		b.setText("Push!");

		new Button(shell, SWT.CHECK).setText("Wow this is nice");

		Composite p = new Composite(shell, SWT.BORDER);

		p.addPaintListener(event -> {
			System.out.println("in paint method");
			GC gc = event.gc;
			gc.setAntialias(SWT.ON);
			gc.drawOval(x++, y++, 30, 30);

			display.timerExec(50, new Runnable() {

				@Override
				public void run() {
					p.redraw();
				}
			});

		});

//		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

	private static void sleep() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
