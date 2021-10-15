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

public class AButtonSnippet extends Button {

	/**
	 * Create a automatic button.
	 * 
	 * @param parent
	 * @param style
	 */
	public AButtonSnippet(Composite parent, int style) {
		super(parent, style);
		addPaintListener(event -> draw(event));
	}

	private int fAlpha = 100;
	private int fDelay = 5000;
	private int fBorderSize = 2;
	private boolean fIsDrawing;
	private boolean fIsDamaged;
	private boolean fCancelled;
	private boolean fIsDone;

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

		AButtonSnippet button = new AButtonSnippet(shell, SWT.PUSH);
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

	protected boolean isCancelled() {
		return fCancelled;
	}

	/**
	 * Sets the transparency of the animation.
	 * 
	 * @param alpha the transparency value between 0 and 255l
	 * @return this
	 */
	public AButtonSnippet setAlpha(int alpha) {
		fAlpha = alpha;
		return this;
	}

	/**
	 * @return the transparency value of the animation overlay.
	 */
	public int getAlpha() {
		return fAlpha;
	}

	/**
	 * Cancel the countdown of the widget.
	 * 
	 * @return this
	 */
	public AButtonSnippet cancel() {
		fCancelled = true;
		return this;
	}

	/**
	 * Restart the countdown.
	 * 
	 * @return this
	 */
	public AButtonSnippet reStart() {
		fCancelled = false;
		fIsDone = false;
		redraw();
		return this;
	}

	/**
	 * Sets the delay after which the button will automatically press itself.
	 * 
	 * @param delay the delay in milliseconds
	 * @return this
	 */
	public AButtonSnippet setDelay(int delay) {
		fDelay = delay;
		return this;
	}

	/**
	 * @return the delay to the automatic press in milliseconds.
	 */
	public int getDelay() {
		return fDelay;
	}

	public AButtonSnippet setBorderSize(int borderSize) {
		fBorderSize = borderSize;
		return this;
	}

	public int getBorderSize() {
		return fBorderSize;
	}

	private void draw(PaintEvent event) {
		GC gc = new GC((AButtonSnippet) event.widget);
		AButtonSnippet button = (AButtonSnippet) event.widget;
		Rectangle bounds = button.getBounds();
		Thread drawThread = new Thread(() -> {
			doDraw(gc, button, bounds);
		});
		drawThread.start();
	}

	private void doDraw(GC gc, AButtonSnippet button, Rectangle bounds) {

		gc.setAlpha(button.getAlpha());
		int sleepTime = button.getDelay() / bounds.width;
		int borderWidth = getBorderSize();

		if (fIsDone) {
			drawDamagedArea(gc, bounds, borderWidth, bounds.width);
			return;
		}

		if (fIsDrawing) {
			fIsDamaged = true;
			return;
		}
		try {
			fIsDrawing = true;

			for (int i = borderWidth; i < bounds.width - borderWidth; i++) {
				if (fCancelled) {
					break;
				}
				if (fIsDamaged && i > borderWidth) {
					drawDamagedArea(gc, bounds, borderWidth, i);
					fIsDamaged = false;
				}
				gc.drawLine(i, borderWidth, i, bounds.height - borderWidth);
				sleep(sleepTime);
			}
			fire();
		} finally {
			fIsDrawing = false;
			fIsDone = true;
		}
	}

	private void drawDamagedArea(GC gc, Rectangle bounds, int borderWidth, int width) {
		Color background = gc.getBackground();
		gc.setBackground(gc.getForeground());
		gc.fillRectangle(borderWidth, borderWidth, width - 1, bounds.height - borderWidth - 1);
		gc.setBackground(background);
	}

	private void fire() {
		getDisplay().asyncExec(() -> {
			// setSelection(true);
			notifyListeners(SWT.Selection, new Event());
		});
	}

	private static void sleep(int sleepTimeMillis) {
		try {
			Thread.sleep(sleepTimeMillis);
		} catch (InterruptedException e) {
			// nop
		}
	}

	@Override
	protected void checkSubclass() {
	}
}
