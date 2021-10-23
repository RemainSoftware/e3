package org.eclipse.nebula.widgets.abutton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

public class AButton extends Button {

	private int fBorderSize = 2;
	private int fAnimationTime = 5000;
	private boolean fIsDrawing;
	private boolean fIsDamaged;
	private boolean fCanRun = false;

	public AButton(Composite parent, int style) {
		super(parent, style);
		addPaintListener(e -> draw(e));
	}

	private void draw(PaintEvent e) {
		if (fIsDrawing) {
			fIsDamaged = true;
			return;
		}
		if (!fCanRun) {
			return;
		}
		Thread thread = new Thread(getAnimationRunner(e));
		thread.setName("Automatic button animation runner");
		thread.start();
	}

	private Runnable getAnimationRunner(PaintEvent e) {
		int bs = getBorderSize();
		int width = getSize().x;
		int height = getSize().y;
		fCanRun = false;
		GC gc = new GC(this);
		gc.setAlpha(100);
		Color fgColor = getForeground();
		gc.setBackground(fgColor);
		return () -> {
			try {
				fIsDrawing = true;
				int lines = width - bs;
				for (int i = bs; i < lines; i++) {
					gc.drawLine(i, bs, i, height - bs - 1);
					sleep(getAnimationTime() / lines);
					if (isDisposed()) {
						break;
					}
					if (fIsDamaged) {
						gc.fillRectangle(bs, bs, i - 2, height - (2 * bs));
						fIsDamaged = false;
					}
				}
				if (!isDisposed()) {
					getDisplay().asyncExec(() -> {
						setSelection(true);
						notifyListeners(SWT.Selection, new Event());
					});
				}
			} finally {
				fIsDrawing = false;
			}
		};
	}

	private void sleep(int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// don't care
		}
	}

	/**
	 * @param animationTime the animation time in milliseconds
	 */
	public AButton setAnimationTime(int animationTime) {
		fAnimationTime = animationTime;
		return this;
	}

	public int getAnimationTime() {
		return fAnimationTime;
	}

	public int getBorderSize() {
		return fBorderSize;
	}

	public AButton setBorderSize(int borderSize) {
		fBorderSize = borderSize;
		return this;
	}

	/**
	 * Starts the animation.
	 */
	public void start() {
		fCanRun = true;
		getDisplay().asyncExec(() -> redraw());
	}

	@Override
	protected void checkSubclass() {
	}
}